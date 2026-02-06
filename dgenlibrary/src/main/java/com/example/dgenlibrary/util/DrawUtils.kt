package com.example.dgenlibrary.util

import android.content.Context
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas

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
    val paint = Paint().apply {
        color = textColor
        this.textSize = textSize // Text size in pixels
        isAntiAlias = true
        textAlign = Paint.Align.RIGHT
        typeface = ResourcesCompat.getFont(context, fontResId)
    }

    // Calculate the width of the text
    val textWidth = paint.measureText(category)

    // Define the height dynamically based on the text width
    val shapeHeight = textWidth + 20f // Add padding as needed

    // Draw the custom shape
    val path = Path().apply {
        moveTo(size.width / 16 * 1, 0f) // Start point
        lineTo((size.width / 16) * 3.5f, 0f) // Top line
        lineTo((size.width / 16) * 3.5f, shapeHeight) // Use textWidth for height
        lineTo((size.width / 16), shapeHeight) // Adjust bottom
        close() // Connect back to start point
    }

    drawPath(
        path = path,
        color = shapeColor
    )

    // Calculate text position
    val xPosition = (size.width / 16) * 3f // Center horizontally
    val yPosition = 8f // Vertically centered within the shape

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

fun DrawScope.drawCheckCircle(
    center: Offset,
    radius: Float,
    circleColor: Color,
    checkColor: Color,
    strokeWidth: Float
) {
    // Draw the outer circle
    drawCircle(
        color = circleColor,
        radius = radius,
        center = center
    )

    // Calculate points for the check mark relative to the circle's center and radius.
    val start = Offset(center.x - radius * 0.4f, center.y)
    val mid = Offset(center.x - radius * 0.1f, center.y + radius * 0.4f)
    val end = Offset(center.x + radius * 0.5f, center.y - radius * 0.3f)

    // Create a path for the check mark.
    val checkPath = Path().apply {
        moveTo(start.x, start.y)
        lineTo(mid.x, mid.y)
        lineTo(end.x, end.y)
    }

    // Draw the check mark using a stroke style.
    drawPath(
        path = checkPath,
        color = checkColor,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
}
