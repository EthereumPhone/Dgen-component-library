package com.example.dgenlibrary.ui.backgrounds

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.pulseOpacity

@Composable
fun DgenBackBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = dgenBlack,
    imageModel: Any = R.drawable.wireframe_torus,
    imageSize: Dp = 280.dp,
    imageOffsetX: Dp = 120.dp,
    imageOffsetY: Dp = (-24).dp,
    imageAlpha: Float = pulseOpacity,
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    val loader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AsyncImage(
            imageLoader = loader,
            model = imageModel,
            contentDescription = null,
            modifier = Modifier
                .alpha(imageAlpha)
                .offset(x = imageOffsetX, y = imageOffsetY)
                .size(imageSize),
            colorFilter = ColorFilter.tint(primaryColor.copy(pulseOpacity))
        )
        content()
    }
}
