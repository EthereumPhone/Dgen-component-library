package com.example.dgen_component_library

import android.content.Context

import android.graphics.Typeface
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.MonomaniacOneFamily
import com.example.dgenlibrary.ui.theme.SourceSansProFamily
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite


@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    context: Context,
    painter: Painter,
    category: String,
    appName: String,
){

    val interactionSource = remember { MutableInteractionSource() }
    var isHovered by remember { mutableStateOf(false) }


    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {


        Image(
            painter = painter,
            contentDescription = appName,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxWidth()
                .aspectRatio(1f) // Makes the card square
                .pointerInput(Unit){
                    detectTapGestures {

                            isHovered = !isHovered


                    }
                }
                .graphicsLayer {

                    this.scaleX = scale
                    this.scaleY = scale

                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    // Draw the original content (the image)
                    drawContent()

                    drawCategoryTag(
                        category = category,
                        textSize = 20f,
                        textColor = android.graphics.Color.WHITE,
                        shapeColor = dgenRed,
                        rotationAngle = -90f,
                        fontResId = R.font.monomaniacone_regular,
                        context = context
                    )

                }
                .border(1.dp, DgenTheme.colors.dgenWhite)
        )
        Text(
            text = appName,
            style = TextStyle(
                fontFamily = SourceSansProFamily,
                color = dgenWhite,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            color = DgenTheme.colors.dgenWhite
        )
    }
}

@Preview
@Composable
fun PreviewAppCard(){
    val context = LocalContext.current
    AppCard(
        context = context,
        painter = painterResource(R.drawable.coinbase2),
        category = "MARKETPLACE",
        appName = "Coinbase",
    )
}

fun DrawScope.drawCategoryTag(
    category: String,
    textSize: Float,
    textColor: Int,
    shapeColor: Color,
    rotationAngle: Float,
    fontResId: Int,
    context: Context
) {
    // Configure the paint for the text
    val paint = android.graphics.Paint().apply {
        color = textColor
        this.textSize = textSize // Text size in pixels
        isAntiAlias = true
        textAlign = android.graphics.Paint.Align.RIGHT
        typeface = ResourcesCompat.getFont(context, fontResId)
    }

    // Calculate the width of the text
    val textWidth = paint.measureText(category)

    // Define the height dynamically based on the text width
    val shapeHeight = textWidth + 20f // Add padding as needed

    // Draw the custom shape
    val path = Path().apply {
        moveTo(size.width / 10 * 1, 0f) // Start point
        lineTo((size.width / 10) * 2.5f, 0f) // Top line
        lineTo((size.width / 10) * 2.5f, shapeHeight) // Use textWidth for height
        lineTo((size.width / 10), shapeHeight) // Adjust bottom
        close() // Connect back to start point
    }

    drawPath(
        path = path,
        color = shapeColor
    )

    // Calculate text position
    val xPosition = (size.width / 10) * 2.1f // Center horizontally
    val yPosition = 10f // Vertically centered within the shape

    // Save the current canvas state
    drawContext.canvas.nativeCanvas.save()

    // Rotate the canvas around the text position
    drawContext.canvas.nativeCanvas.rotate(
        rotationAngle, // Rotation angle in degrees
        xPosition, // Pivot point X (center of the text)
        yPosition  // Pivot point Y (center of the text)
    )

    // Draw the rotated text
    drawContext.canvas.nativeCanvas.drawText(
        category,
        xPosition,
        yPosition,
        paint
    )

    // Restore the canvas to its previous state
    drawContext.canvas.nativeCanvas.restore()
}
