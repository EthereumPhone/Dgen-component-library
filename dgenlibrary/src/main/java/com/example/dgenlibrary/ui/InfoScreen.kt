package com.example.dgenlibrary.ui

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.neonOpacity
import com.example.dgenlibrary.ui.theme.pulseOpacity

/**
 * An info screen component with animated wireframe torus image and description text.
 * Used for loading states, empty states, or informational displays.
 *
 * @param modifier Modifier to be applied to the screen
 * @param description Text description to display below the image
 * @param imageSize Size of the animated image
 * @param gifEnabledLoader Optional custom ImageLoader for GIF support. If null, a default one is created.
 * @param imageModel The image model to load (default: wireframe torus)
 * @param primaryColor Primary color for the image tint and text
 * @param showBackground Whether to show the black background
 */
@Composable
fun InfoScreen(
    modifier: Modifier = Modifier,
    description: String = "Description",
    imageSize: Dp = 275.dp,
    gifEnabledLoader: ImageLoader? = null,
    imageModel: Any? = R.drawable.wireframe_torus,
    primaryColor: Color,
    showBackground: Boolean = true
) {
    val context = LocalContext.current
    val loader = gifEnabledLoader ?: ImageLoader.Builder(context)
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
            .then(if (showBackground) Modifier.background(dgenBlack) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            AsyncImage(
                imageLoader = loader,
                model = imageModel,
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .offset(y = (-48).dp),
                colorFilter = ColorFilter.tint(primaryColor.copy(pulseOpacity))
            )

            Text(
                text = description,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor.copy(neonOpacity),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(300.dp)
                    .offset(y = (-24).dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoScreenPreview() {
    InfoScreen(
        description = "Loading tokens...",
        primaryColor = dgenGreen
    )
}

@Preview(showBackground = true)
@Composable
private fun InfoScreenEmptyStatePreview() {
    InfoScreen(
        description = "No tokens found. Try adjusting your filters.",
        primaryColor = Color(0xFF00E4FF),
        imageSize = 200.dp
    )
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun InfoScreenPreviewDDevice() {
    InfoScreen(
        description = "Loading tokens...",
        primaryColor = dgenGreen
    )
}