package com.example.dgenlibrary.ui.backgrounds

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.neonOpacity
import com.example.dgenlibrary.ui.theme.pulseOpacity

/**
 * A centered globe background screen, typically used for claim flows or welcome screens.
 * Displays an animated globe wireframe with optional description text.
 *
 * @param modifier Modifier to be applied to the screen
 * @param description Text description to display below the globe
 * @param imageSize Size of the globe animation
 * @param gifEnabledLoader Optional custom ImageLoader for GIF support
 * @param imageModel The image model to load (default: globe wireframe)
 * @param primaryColor Primary color for the globe tint and text
 * @param backgroundColor Background color of the screen
 */
@Composable
fun GlobeBackground(
    modifier: Modifier = Modifier,
    description: String = "Description",
    imageSize: Dp = 275.dp,
    gifEnabledLoader: ImageLoader? = null,
    imageModel: Any? = R.drawable.globe_wireframe,
    primaryColor: Color,
    backgroundColor: Color = dgenBlack
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
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.offset(y = 0.dp)
        ) {
            AsyncImage(
                imageLoader = loader,
                model = imageModel,
                contentDescription = "Decorative globe animation",
                modifier = Modifier.size(imageSize),
                colorFilter = ColorFilter.tint(primaryColor.copy(pulseOpacity))
            )

            Text(
                text = description.uppercase(),
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor.copy(neonOpacity),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-32).dp)
            )
        }
    }
}

/**
 * A variant of GlobeBackground that displays only the globe without text,
 * useful as a pure background layer.
 *
 * @param modifier Modifier to be applied to the background
 * @param imageSize Size of the globe animation
 * @param gifEnabledLoader Optional custom ImageLoader for GIF support
 * @param primaryColor Primary color for the globe tint
 * @param backgroundColor Background color
 * @param content Content to overlay on top of the globe background
 */
@Composable
fun GlobeBackgroundOnly(
    modifier: Modifier = Modifier,
    imageSize: Dp = 400.dp,
    gifEnabledLoader: ImageLoader? = null,
    primaryColor: Color,
    backgroundColor: Color = dgenBlack,
    content: @Composable () -> Unit = {}
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
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            imageLoader = loader,
            model = R.drawable.globe_wireframe,
            contentDescription = null,
            modifier = Modifier.size(imageSize),
            colorFilter = ColorFilter.tint(primaryColor.copy(pulseOpacity))
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun GlobeBackgroundPreview() {
    GlobeBackground(
        description = "Claim your tokens",
        primaryColor = dgenGreen
    )
}

@Preview(showBackground = true)
@Composable
private fun GlobeBackgroundOnlyPreview() {
    GlobeBackgroundOnly(
        primaryColor = Color(0xFF00E4FF)
    ) {
        Text(
            text = "Content on top",
            color = Color.White,
            modifier = Modifier.padding(top = 200.dp)
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun GlobeBackgroundPreviewDDevice() {
    GlobeBackground(
        description = "Claim your tokens",
        primaryColor = dgenGreen
    )
}