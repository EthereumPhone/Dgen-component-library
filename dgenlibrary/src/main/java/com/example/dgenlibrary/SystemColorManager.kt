package com.example.dgenlibrary

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import com.example.dgenlibrary.ui.theme.gunMetalCore
import com.example.dgenlibrary.ui.theme.gunMetalForge
import com.example.dgenlibrary.ui.theme.lazerBurn
import com.example.dgenlibrary.ui.theme.lazerCore
import com.example.dgenlibrary.ui.theme.oceanAbyss
import com.example.dgenlibrary.ui.theme.oceanCore
import com.example.dgenlibrary.ui.theme.orcheAsh
import com.example.dgenlibrary.ui.theme.orcheCore
import com.example.dgenlibrary.ui.theme.terminalCore
import com.example.dgenlibrary.ui.theme.terminalHack

/**
 * Manages two dynamic colors (Primary & Secondary),
 * which can be read from the system accent colors when the app starts.
 * The colors are held as `mutableStateOf`, so that any change
 * automatically triggers recomposition in @Composable callers.
 */
object SystemColorManager {

    private val DEFAULT_PRIMARY = lazerCore
    private val DEFAULT_SECONDARY = lazerBurn
    private val DEFAULT_TERITARY = Color(0xFF820303)

    /** Current primary color */
    var primaryColor by mutableStateOf(DEFAULT_PRIMARY)
        private set

    /** Current secondary color */
    var secondaryColor by mutableStateOf(DEFAULT_SECONDARY)
        private set

    /** Current openGL color */
    var openGLColor by mutableStateOf(DEFAULT_TERITARY)
        private set

    private var accentObserver: ContentObserver? = null

    /**
     * Reads the current system accent color and updates the fields.
     * Should be executed, for example, in the `LaunchedEffect` of a screen.
     */
    fun refresh(context: Context) {
        // Android stores the accent color in Secure Settings from Android 12 onwards.
        // The fallback is an intense red if no entry is present.
        val accentInt = Settings.Secure.getInt(
            context.contentResolver,
            "systemui_accent_color",
            DEFAULT_PRIMARY.toArgb()
        )

        val accentColor = Color(accentInt)
        Log.d("SystemColorManager","accentInt: $accentInt, accentColor: $accentColor")

        //Decide the colorway
        when(accentInt){
            //TERMINAL
            -13510400 -> {
                primaryColor = terminalCore
                secondaryColor = terminalHack
                openGLColor = Color(0xFF186103)
            }
            //LAZER
            -131072 -> {
                primaryColor = lazerCore
                secondaryColor = lazerBurn
                openGLColor = Color(0xFF820303)
            }
            //OCEAN
            -16718593 -> {
                primaryColor = oceanCore
                secondaryColor = oceanAbyss
                openGLColor = Color(0xFF037582)
            }
            //ORCHE
            -1012183 -> {
                primaryColor = orcheCore
                secondaryColor = orcheAsh
                openGLColor = Color(0xFF7B4A17)
            }

            //GUNMETAL
            -3618616 -> {
                primaryColor = gunMetalCore
                secondaryColor = gunMetalForge
                openGLColor = Color(0xFF676767)
            }

            else -> {
                primaryColor = lazerCore
                secondaryColor = lazerBurn
                openGLColor = Color(0xFF820303)
            }
        }

        //-13510400 - Green
        // -131072 - Red
        // -16718593 - blue
        // -1012183 - orange
        // -3618616 - Gray
    }

    /**
     * Start listening for system accent color changes. Should be called once (e.g. in Application/Activity).
     */
    fun start(context: Context) {
        if (accentObserver != null) return // already started

        accentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                refresh(context)
            }
        }

        context.contentResolver.registerContentObserver(
            Settings.Secure.getUriFor("systemui_accent_color"),
            false,
            accentObserver as ContentObserver
        )

        // Load current accent immediately
        refresh(context)
    }

    /**
     * Stop listening for system accent color changes. Call from Activity.onDestroy or similar.
     */
    fun stop(context: Context) {
        accentObserver?.let {
            context.contentResolver.unregisterContentObserver(it)
            accentObserver = null
        }
    }

    /**
     * Allows to manually override the colors (e.g. for testing).
     */
    fun setColors(primary: Color, secondary: Color) {
        primaryColor = primary
        secondaryColor = secondary
    }

    /**
     * Returns the appropriate placeholder token drawable resource based on the current primary color.
     * Each theme has its own styled placeholder icon.
     */
    fun getPlaceholderTokenDrawable(): Int {
        return getPlaceholderTokenDrawableForColor(primaryColor)
    }

    /**
     * Returns the appropriate placeholder token drawable resource based on the given color.
     * - Lazer (red): placeholder_token_lazer
     * - Ocean (cyan): placeholder_token_ocean
     * - Orche (orange): placeholder_token_orche
     * - Gunmetal (gray): placeholder_token_gunmetal
     * - Terminal (green): placeholder_token_terminal
     */
    fun getPlaceholderTokenDrawableForColor(color: Color): Int {
        return when (color) {
            lazerCore -> R.drawable.placeholder_token_lazer
            oceanCore -> R.drawable.placeholder_token_ocean
            orcheCore -> R.drawable.placeholder_token_orche
            gunMetalCore -> R.drawable.placeholder_token_gunmetal
            terminalCore -> R.drawable.placeholder_token_terminal
            else -> R.drawable.placeholder_token_lazer
        }
    }
} 