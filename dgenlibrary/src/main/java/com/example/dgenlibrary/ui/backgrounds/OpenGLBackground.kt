package com.example.dgenlibrary.ui.backgrounds

import android.opengl.GLSurfaceView
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

/**
 * A composable that displays an OpenGL rendered background with content overlaid on top.
 * 
 * This component manages the lifecycle of a GLSurfaceView, pausing and resuming
 * the rendering based on the lifecycle of the composable.
 *
 * @param modifier Modifier to be applied to the background
 * @param glSurfaceView The GLSurfaceView to render as background
 * @param overlayColor Optional color overlay with alpha to darken the background
 * @param overlayAlpha Alpha value for the overlay (0f = transparent, 1f = opaque)
 * @param backgroundSize Size of the OpenGL view (default 300.dp)
 * @param onResume Callback when the view should resume rendering
 * @param onPause Callback when the view should pause rendering
 * @param content The content to display on top of the background
 */
@Composable
fun OpenGLBackground(
    modifier: Modifier = Modifier,
    glSurfaceView: GLSurfaceView,
    overlayColor: Color = dgenBlack,
    overlayAlpha: Float = 0.6f,
    backgroundSize: Dp = 300.dp,
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    // Manage GLSurfaceView lifecycle
    DisposableEffect(lifecycleOwner, glSurfaceView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME -> {
                    onResume()
                    glSurfaceView.onResume()
                }
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> {
                    onPause()
                    glSurfaceView.onPause()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            onPause()
            glSurfaceView.onPause()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // OpenGL rendering in the background
        AndroidView(
            factory = { glSurfaceView },
            modifier = modifier
                .drawWithContent {
                    drawContent()
                    drawRect(color = overlayColor, alpha = overlayAlpha)
                }
                .align(Alignment.Center)
                .size(backgroundSize)
        )

        content()
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

