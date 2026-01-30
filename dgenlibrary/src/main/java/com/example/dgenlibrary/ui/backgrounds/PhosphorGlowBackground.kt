package com.example.dgenlibrary.ui.backgrounds

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random
// ==================== PREVIEWS ====================

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
/**
 * A retro-styled phosphor glow background with CRT scanlines effect,
 * matrix rain, and optional grid overlay.
 *
 * @param modifier Modifier to apply to the background container
 * @param primaryColor The primary glow color (defaults to theme primary)
 * @param backgroundColor The base background color (defaults to Black)
 * @param enableScanlines Enable CRT scanline effect
 * @param scanlineAlpha Alpha value for scanlines (0.0 - 1.0)
 * @param enableMatrixRain Enable matrix rain background effect
 * @param matrixDensity Density of matrix rain dots (0.0 - 1.0)
 * @param matrixDotRadius Radius of matrix rain dots
 * @param enableGrid Enable grid overlay
 * @param gridSpacing Spacing between grid lines in dp
 * @param gridAlpha Alpha value for grid lines
 * @param content The content to display on top of the background
 */
@Composable
fun PhosphorGlowBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = Color.Black,
    enableScanlines: Boolean = true,
    scanlineAlpha: Float = 0.08f,
    enableMatrixRain: Boolean = true,
    matrixDensity: Float = 0.08f,
    matrixDotRadius: Float = 2f,
    enableGrid: Boolean = true,
    gridSpacing: Int = 50,
    gridAlpha: Float = 0.15f,
    content: @Composable BoxScope.() -> Unit
) {
    // Animation states for matrix characters
    var matrixChars by remember { mutableStateOf(List(15) { generateMatrixChar() }) }
    
    // Continuous animations
    val infiniteTransition = rememberInfiniteTransition(label = "phosphor_animations")
    
    val scanlineAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanlines"
    )
    
    val phosphorGlow by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "phosphor_glow"
    )
    
    val matrixAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "matrix_alpha"
    )
    
    // Matrix character updates
    LaunchedEffect(Unit) {
        while (true) {
            delay(Random.nextLong(200, 800))
            val index = Random.nextInt(matrixChars.size)
            matrixChars = matrixChars.toMutableList().also { list ->
                list[index] = generateMatrixChar()
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .drawWithContent {
                drawContent()
                
                // CRT Scanlines effect
                if (enableScanlines) {
                    drawScanlines(scanlineAnimation, size.height, primaryColor, alpha = scanlineAlpha)
                }
                
                // Phosphor glow overlay
                drawRect(
                    color = primaryColor.copy(alpha = 0.06f * phosphorGlow),
                    size = size
                )
            }
    ) {
        // Matrix rain background
        if (enableMatrixRain) {
            MatrixRainBackground(
                chars = matrixChars,
                alpha = matrixAlpha,
                modifier = Modifier.fillMaxSize(),
                primaryColor = primaryColor,
                density = matrixDensity,
                radius = matrixDotRadius
            )
        }
        
        // Grid background
        if (enableGrid) {
            val gridLineColor = primaryColor.copy(alpha = gridAlpha)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val gridSize = gridSpacing.dp.toPx()
                val horizontalLines = (size.height / gridSize).toInt()
                val verticalLines = (size.width / gridSize).toInt()
                
                // Draw horizontal lines
                for (i in 0..horizontalLines) {
                    drawLine(
                        color = gridLineColor,
                        start = Offset(0f, i * gridSize),
                        end = Offset(size.width, i * gridSize),
                        strokeWidth = 1f
                    )
                }
                
                // Draw vertical lines
                for (i in 0..verticalLines) {
                    drawLine(
                        color = gridLineColor,
                        start = Offset(i * gridSize, 0f),
                        end = Offset(i * gridSize, size.height),
                        strokeWidth = 1f
                    )
                }
            }
        }
        
        content()
    }
}

/**
 * Matrix rain background effect composable
 */
@Composable
fun MatrixRainBackground(
    chars: List<String>,
    alpha: Float,
    modifier: Modifier = Modifier,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    density: Float = 0.1f,
    radius: Float = 2f
) {
    Canvas(modifier = modifier) {
        val charSize = 20.dp.toPx()
        val columns = (size.width / charSize).toInt()
        val rows = (size.height / charSize).toInt()
        
        for (col in 0 until columns) {
            for (row in 0 until rows) {
                if (Random.nextFloat() < density) {
                    val x = col * charSize
                    val y = row * charSize
                    
                    drawCircle(
                        color = primaryColor.copy(alpha = alpha * 0.4f),
                        radius = radius,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

/**
 * Draw CRT scanlines effect
 */
fun DrawScope.drawScanlines(
    offset: Float,
    height: Float,
    primaryColor: Color,
    alpha: Float = 0.05f
) {
    val lineSpacing = 4.dp.toPx()
    val numLines = (height / lineSpacing).toInt()
    
    for (i in 0..numLines) {
        val y = (i * lineSpacing + offset * height) % height
        drawLine(
            color = primaryColor.copy(alpha = alpha),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1f
        )
    }
}

/**
 * Generate a random matrix character (hexadecimal)
 */
fun generateMatrixChar(): String {
    val chars = "0123456789ABCDEF"
    return chars.random().toString()
}



@Preview(
    name = "Phosphor Glow Background - Green",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
@Composable
private fun PhosphorGlowBackgroundPreviewGreen() {
    MaterialTheme {
        PhosphorGlowBackground(
            primaryColor = Color(0xFF00FF00),
            enableScanlines = true,
            enableMatrixRain = true,
            enableGrid = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "TERMINAL READY",
                    color = Color(0xFF00FF00),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(
    name = "Phosphor Glow Background - Amber",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
@Composable
private fun PhosphorGlowBackgroundPreviewAmber() {
    MaterialTheme {
        PhosphorGlowBackground(
            primaryColor = Color(0xFFFFB000),
            enableScanlines = true,
            enableMatrixRain = true,
            enableGrid = true,
            gridSpacing = 40
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SYSTEM ONLINE",
                    color = Color(0xFFFFB000),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(
    name = "Phosphor Glow Background - Minimal",
    showBackground = true,
    widthDp = 360,
    heightDp = 400
)
@Composable
private fun PhosphorGlowBackgroundPreviewMinimal() {
    MaterialTheme {
        PhosphorGlowBackground(
            primaryColor = Color(0xFF00FFFF),
            enableScanlines = true,
            enableMatrixRain = false,
            enableGrid = false
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SCANLINES ONLY",
                    color = Color(0xFF00FFFF),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview(
    name = "Matrix Rain Background",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 200,
    heightDp = 200
)
@Composable
private fun MatrixRainBackgroundPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        MatrixRainBackground(
            chars = listOf("0", "1", "A", "F", "C", "D"),
            alpha = 0.4f,
            primaryColor = Color(0xFF00FF00),
            density = 0.15f,
            radius = 3f
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun PhosphorGlowBackgroundPreviewDDevice() {
    MaterialTheme {
        PhosphorGlowBackground(
            primaryColor = Color(0xFF00FF00),
            enableScanlines = true,
            enableMatrixRain = true,
            enableGrid = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "TERMINAL READY",
                    color = Color(0xFF00FF00),
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }
}