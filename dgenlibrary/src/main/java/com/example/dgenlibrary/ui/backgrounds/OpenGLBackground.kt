package com.example.dgenlibrary.ui.backgrounds

import android.opengl.GLSurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import com.example.dgenlibrary.ui.theme.dgenBlack


import kotlin.math.roundToInt
import androidx.compose.runtime.LaunchedEffect
import org.ethosmobile.contacts.opengl.views.OpenGLGlobeView


@Composable
fun OpenGLBackground(
    modifier: Modifier = Modifier,
    globeRenderer: OpenGLGlobeView,
    globeColor: Color,
    lineWidth: Float = 2f,
    content: @Composable () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    // Manage GLSurfaceView lifecycle and throttle rendering when not animating
    DisposableEffect(lifecycleOwner, globeRenderer) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME -> {
                    globeRenderer.setAnimating(true)
                    globeRenderer.onResumeView()
                }
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
                    globeRenderer.setAnimating(false)
                    globeRenderer.onPauseView()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            globeRenderer.setAnimating(false)
            globeRenderer.onPauseView()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // OpenGL rendering in the background

        AndroidView(
            factory = { globeRenderer.apply {
                // renderMode controlled by setAnimating
            }
            },
            modifier = modifier
                .drawWithContent {
                    drawContent()
                    drawRect(color = dgenBlack, alpha = 0.6f)
                }
                .align(Alignment.Center).size(300.dp)//.fillMaxSize() // Match the size of the parent container
        )

        content()

    }

    // Update OpenGL renderer color when globeColor changes
    LaunchedEffect(globeColor, lineWidth) {
        val r = (globeColor.red * 255).roundToInt()
        val g = (globeColor.green * 255).roundToInt()
        val b = (globeColor.blue * 255).roundToInt()
        val hexColor = String.format("#%02X%02X%02X", r, g, b)
        globeRenderer.setColor(hexColor)
        globeRenderer.renderer.linewidth = lineWidth
    }
}



@Preview
@Composable
private fun OpenGLBackgroundPreview() {
    // Preview is limited for OpenGL content
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = "OpenGLBackground Preview\n(Requires actual GLSurfaceView)",
            color = Color.White
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun OpenGLBackgroundPreviewDDevice() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dgenBlack),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = "OpenGLBackground\n(Requires GLSurfaceView)",
            color = Color.White
        )
    }
}