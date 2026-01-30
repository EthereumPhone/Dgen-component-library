package com.example.dgenlibrary.alarm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun DoubleCircle(offset: Offset, primaryColor: Color) {
    Canvas(modifier = Modifier.size(48.dp)) {
        val outerRadius = 24.dp.toPx()
        val innerRadius = 13.dp.toPx()
        val outerBorderWidth = 5.dp.toPx()
        val innerBorderWidth = 6.dp.toPx()

        // Draw outer circle
        drawCircle(
            color = primaryColor,
            radius = outerRadius,
            center = center + offset,
            style = Stroke(width = outerBorderWidth)
        )

        // Draw inner circle
        drawCircle(
            color = primaryColor,
            radius = innerRadius,
            center = center + offset,
            style = Stroke(width = innerBorderWidth)
        )
    }
}