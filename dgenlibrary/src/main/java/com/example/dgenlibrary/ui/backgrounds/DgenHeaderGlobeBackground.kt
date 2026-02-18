package com.example.dgenlibrary.ui.backgrounds

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.pulseOpacity

/**
 * A header-style background with an animated globe wireframe positioned in the top-right area.
 * Includes statusBarsPadding for proper system bar inset handling.
 * Typically used as the root layout for screens like Send, Receive, etc.
 *
 * @param modifier Modifier applied to the root Box (e.g., clickable for focus dismiss)
 * @param primaryColor The color to tint the globe animation
 * @param backgroundColor The background color behind the globe
 * @param globeOffsetX Horizontal offset for the globe position
 * @param globeOffsetY Vertical offset for the globe position
 * @param globeScale Scale factor for the globe size
 * @param globeAlpha Opacity of the globe animation
 * @param content Content to display on top of the background
 */
@Composable
fun DgenHeaderGlobeBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    backgroundColor: Color = dgenBlack,
    globeOffsetX: Dp = 250.dp,
    globeOffsetY: Dp = 20.dp,
    globeScale: Float = 1.3f,
    globeAlpha: Float = pulseOpacity,
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
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
            .background(backgroundColor)
            .statusBarsPadding()
    ) {
        AsyncImage(
            imageLoader = gifEnabledLoader,
            model = R.drawable.globe_wireframe,
            contentDescription = null,
            modifier = Modifier
                .alpha(globeAlpha)
                .offset(x = globeOffsetX, y = globeOffsetY)
                .scale(globeScale)
                .aspectRatio(1f),
            colorFilter = ColorFilter.tint(primaryColor)
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun DgenHeaderGlobeBackgroundPreview() {
    DgenHeaderGlobeBackground(primaryColor = dgenGreen)
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenHeaderGlobeBackgroundPreviewDDevice() {
    DgenHeaderGlobeBackground(primaryColor = dgenGreen)
}
