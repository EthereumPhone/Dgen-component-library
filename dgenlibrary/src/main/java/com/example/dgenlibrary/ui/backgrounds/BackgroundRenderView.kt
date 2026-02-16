package com.example.dgenlibrary.ui.backgrounds

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.dgenlibrary.R
import com.example.dgenlibrary.SystemColorManager
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.pulseOpacity

/**
 * A full-screen background composable with an animated wireframe globe.
 * Uses the system primary color for tinting.
 *
 * @param modifier Modifier to be applied
 * @param content Content to display on top of the background
 */
@Composable
fun BackgroundRenderView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    val primaryColor = SystemColorManager.primaryColor
    val gifEnabledLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(dgenBlack)
    ) {
        AsyncImage(
            imageLoader = gifEnabledLoader,
            model = R.drawable.globe_wireframe,
            contentDescription = null,
            modifier = Modifier
                .alpha(pulseOpacity)
                .offset(x = 250.dp, y = 64.dp)
                .scale(1.6f)
                .aspectRatio(1f),
            colorFilter = ColorFilter.tint(primaryColor.copy(pulseOpacity))
        )
        content()
    }
}
