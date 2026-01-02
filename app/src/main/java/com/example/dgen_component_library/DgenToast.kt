package com.example.dgen_component_library

import android.content.Context
import android.text.Layout
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.dgen_component_library.SweetToastUtil.SetView
import com.example.dgen_component_library.SweetToastUtil.SweetInfo
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenBurgendy
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenOrche
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import kotlinx.coroutines.delay


interface SweetToastProperty {
    fun getBackgroundColor(): Color
    fun getTextColor(): Color
}

class Error : SweetToastProperty {
    override fun getBackgroundColor(): Color = dgenRed
    override fun getTextColor(): Color = dgenWhite
}

class Info : SweetToastProperty {
    override fun getBackgroundColor(): Color = dgenOcean
    override fun getTextColor(): Color = dgenWhite
}

class Success : SweetToastProperty {
    override fun getBackgroundColor(): Color = dgenGreen
    override fun getTextColor(): Color = dgenWhite
}

class Warning : SweetToastProperty {
    override fun getBackgroundColor(): Color = dgenOrche
    override fun getTextColor(): Color = dgenBurgendy
}


object SweetToastUtil {

    @Composable
    fun SweetSuccess(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(top = 16.dp, bottom = 100.dp),
        contentAlignment: Alignment = Alignment.BottomCenter,
        color: Color= dgenGreen
    ) {
        val sweetSuccessToast = CustomSweetToast(LocalContext.current)
        sweetSuccessToast.MakeToast(
            message = message,
            duration = duration,
            type = Success(),
            padding = padding,
            contentAlignment = contentAlignment,
            colorText = color
        )
        sweetSuccessToast.show()
    }

    @Composable
    fun SweetError(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(top = 16.dp, bottom = 100.dp),
        contentAlignment: Alignment = Alignment.BottomCenter
    ) {
        val sweetErrorToast = CustomSweetToast(LocalContext.current)
        sweetErrorToast.MakeToast(
            message = message,
            duration = duration,
            type = Error(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetErrorToast.show()
    }

    @Composable
    fun SweetInfo(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(top = 16.dp, bottom = 100.dp),
        contentAlignment: Alignment = Alignment.BottomCenter
    ) {
        val sweetInfoToast = CustomSweetToast(LocalContext.current)
        sweetInfoToast.MakeToast(
            message = message,
            duration = duration,
            type = Info(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetInfoToast.show()
    }

    @Composable
    fun SweetWarning(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
        padding: PaddingValues = PaddingValues(top = 16.dp, bottom = 100.dp),
        contentAlignment: Alignment = Alignment.BottomCenter
    ) {
        val sweetWarningToast = CustomSweetToast(LocalContext.current)
        sweetWarningToast.MakeToast(
            message = message,
            duration = duration,
            type = Warning(),
            padding = padding,
            contentAlignment = contentAlignment
        )
        sweetWarningToast.show()
    }

    @Composable
    fun SetView(
        messageTxt: String,
        backgroundColor: Color,
        textColor: Color,
        padding: PaddingValues,
        contentAlignment: Alignment
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = contentAlignment
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier//.clip(shape = RoundedCornerShape(8.dp))
                        .defaultMinSize(minHeight = 44.dp)
                        .fillMaxWidth().background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(0.dp)
                        )
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = messageTxt,
                        style = TextStyle(
                            fontFamily = SpaceMono,
                            color = textColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 24.sp,

                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        )
                    )
                }

            }
        }
    }
}

class CustomSweetToast(context: Context) : Toast(context) {
    @Composable
    fun MakeToast(
        message: String,
        duration: Int = LENGTH_LONG,
        type: SweetToastProperty,
        padding: PaddingValues,
        contentAlignment: Alignment,
        colorText: Color =type.getTextColor()
    ) {
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent {

            SetView(
                messageTxt = message,
                backgroundColor = type.getBackgroundColor(),
                textColor =colorText,
                padding = padding,
                contentAlignment = contentAlignment
            )

        }


        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)
        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)



        this.duration = duration
        this.view = views
    }
}


@Composable
fun CustomToast(
    message: String,
    iconRes: Int? = null,
    durationMillis: Long = 3000, // Default duration: 3 seconds
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }

    // Automatically dismiss the toast after the given duration
    LaunchedEffect(Unit) {
        delay(durationMillis)
        isVisible = false
        delay(300) // Extra time for fade-out animation
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentSize(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                iconRes?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "Toast Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = message,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 720,
    heightDp = 720,
)
@Composable
fun MakeToastPreview(){

    var showToast by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showToast) {
            CustomToast(
                message = "This is a custom toast!",
                iconRes = null, // Replace with a drawable resource if needed
                durationMillis = 2500
            ) {
                showToast = false
            }
        }

        // Example Button to trigger Toast
        androidx.compose.material3.Button(
            onClick = { showToast = true },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Show Toast")
        }
    }

//    val sweetWarningToast = CustomSweetToast(LocalContext.current)
//    Box(
//        modifier = Modifier.fillMaxSize().background(dgenBlack)
//    ){
//        SweetInfo(
//            message = "TESTY TEST",
//        )
//        Button(
//            onClick = {
//
//            }
//        ) {
//            Text(
//                text = "Toast button",
//                style = TextStyle(
//                    fontFamily = SpaceMono,
//                    color = dgenWhite,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 24.sp,
//
//                    letterSpacing = 0.sp,
//                    textDecoration = TextDecoration.None
//                )
//            )
//        }
////        SetView(
////            messageTxt = "Test",
////            backgroundColor= dgenGreen,
////            textColor = dgenWhite,
////            padding= PaddingValues(8.dp),
////            contentAlignment = Alignment.BottomCenter
////        )
//    }
}


