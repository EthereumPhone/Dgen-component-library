package com.example.dgenlibrary.ui.backgrounds

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.headers.NavigationHeaderBar
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.pulseOpacity

@Composable
fun DgenNavigationBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = dgenBlack,
    imageModel: Any = R.drawable.wireframe_torus,
    imageSize: Dp = 280.dp,
    imageOffsetX: Dp = 120.dp,
    imageOffsetY: Dp = (-24).dp,
    imageAlpha: Float = pulseOpacity,
    title: String = "",
    headerContent: @Composable (() -> Unit)? = null,
    focusManager: FocusManager? = null,
    onBackClick: () -> Unit = {},
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 12.dp, end = 12.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager?.clearFocus() }
                    )
                }
        ) {
            if (headerContent != null) {
                NavigationHeaderBar(
                    content = headerContent,
                    onClick = onBackClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    primaryColor = primaryColor
                )
            } else if (title.isNotEmpty()) {
                NavigationHeaderBar(
                    text = title,
                    onClick = onBackClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    primaryColor = primaryColor
                )
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                content()
            }
        }
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenNavigationBackgroundTitlePreview() {
    DgenNavigationBackground(
        title = "Settings",
        primaryColor = dgenGreen,
        onBackClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Content goes here", color = Color.White)
            Text(text = "More content below the header", color = Color.White)
        }
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenNavigationBackgroundContentPreview() {
    DgenNavigationBackground(
        primaryColor = dgenGreen,
        onBackClick = {},
        headerContent = {
            Text(
                text = "CUSTOM HEADER",
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = dgenGreen,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Content goes here", color = Color.White)
            Text(text = "More content below the header", color = Color.White)
        }
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenNavigationBackgroundNoHeaderPreview() {
    DgenNavigationBackground(
        primaryColor = dgenTurqoise
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "No header, just background", color = Color.White)
            Text(text = "Content only mode", color = Color.White)
        }
    }
}
