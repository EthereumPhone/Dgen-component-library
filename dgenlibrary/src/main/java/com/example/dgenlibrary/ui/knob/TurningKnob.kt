package com.example.dgenlibrary.ui.knob

import android.graphics.Typeface
import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.lazerBurn
import com.example.dgenlibrary.ui.theme.lazerCore
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Configuration data class for the TurningKnob component.
 *
 * @param outerLineColor The color of the outer circle line and protruding step lines
 * @param knobColor The secondary color used for the turning knob fill
 * @param knobBorderColor The primary color used for knob borders and numbers
 * @param stepLabels The labels to display at each step position (should match number of steps)
 */
data class TurningKnobConfig(
    val outerLineColor: Color,
    val knobColor: Color,
    val knobBorderColor: Color,
    val stepLabels: List<String> = listOf("5", "10", "20", "30", "40", "50", "60", "70", "80", "90")
) {
    val numberOfSteps: Int get() = stepLabels.size
}

/**
 * Aircraft-styled turning knob component with step-based rotation and haptic feedback.
 *
 * Features:
 * - Outer circle with protruding lines at each step
 * - Inner turning knob with drop shadow
 * - Triangle pointer indicating current position
 * - Numbers displayed outside the outer circle
 * - Haptic feedback on each step change
 *
 * @param modifier Modifier for the component
 * @param config Configuration for colors and step labels
 * @param size The size of the component
 * @param initialStep The initial step position (0-indexed)
 * @param onStepChanged Callback when the step changes
 * @param labelContent Optional composable content displayed above the knob
 */
@Composable
fun TurningKnob(
    modifier: Modifier = Modifier,
    config: TurningKnobConfig = TurningKnobConfig(
        outerLineColor = lazerCore,
        knobColor = lazerBurn,
        knobBorderColor = lazerCore
    ),
    size: Dp = 280.dp,
    initialStep: Int = 0,
    onStepChanged: (Int, String) -> Unit = { _, _ -> },
    labelContent: @Composable (() -> Unit)? = null,
) {
    val view = LocalView.current
    val context = LocalContext.current
    val numberOfSteps = config.numberOfSteps
    val degreesPerStep = 360f / numberOfSteps

    // Load SpaceMono font for canvas drawing
    val spaceMonoTypeface = remember {
        ResourcesCompat.getFont(context, R.font.spacemono_bold)
    }

    var currentStep by remember { mutableIntStateOf(initialStep.coerceIn(0, numberOfSteps - 1)) }
    var dragAngle by remember { mutableFloatStateOf(currentStep * degreesPerStep) }

    // Animate the rotation smoothly
    val animatedRotation by animateFloatAsState(
        targetValue = currentStep * degreesPerStep,
        animationSpec = tween(durationMillis = 100),
        label = "knobRotation"
    )

    // Trigger haptic feedback when step changes
    LaunchedEffect(currentStep) {
        view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        onStepChanged(currentStep, config.stepLabels.getOrElse(currentStep) { "" })
    }

    Column(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (labelContent != null) {
            labelContent()
        }

        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(size)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, _ ->
                                change.consume()
                                val center = Offset(this.size.width / 2f, this.size.height / 2f)
                                val touchPoint = change.position

                                // Calculate angle from center to touch point
                                val angle = atan2(
                                    touchPoint.y - center.y,
                                    touchPoint.x - center.x
                                ) * (180f / PI.toFloat())

                                // Convert to 0-360 range (starting from top)
                                val normalizedAngle = (angle + 90f + 360f) % 360f

                                dragAngle = normalizedAngle

                                // Calculate which step we're closest to
                                val newStep =
                                    ((normalizedAngle / degreesPerStep).toInt()) % numberOfSteps

                                if (newStep != currentStep) {
                                    currentStep = newStep
                                }
                            }
                        )
                    }
            ) {
                val canvasSize = size.toPx()
                val center = Offset(canvasSize / 2f, canvasSize / 2f)

                val pointerSize = size.toPx() * 0.05f
                // Radii for different elements
                val outerRadius = canvasSize * 0.35f
                val outerLineRadius = canvasSize * 0.40f
                val numberRadius = canvasSize * 0.5f
                val knobOuterRadius = canvasSize * 0.30f
                val knobInnerRadius = canvasSize * 0.18f
                val pointerRadius =
                    ((knobInnerRadius + knobOuterRadius) / 2f) + (pointerSize / 2f)

                // Draw outer circle with step lines
                drawOuterCircle(
                    center = center,
                    radius = outerRadius,
                    lineRadius = outerLineRadius,
                    color = config.outerLineColor,
                    numberOfSteps = numberOfSteps
                )

                // Draw step numbers outside the outer circle
                drawStepNumbers(
                    center = center,
                    radius = numberRadius,
                    color = config.knobBorderColor,
                    stepLabels = config.stepLabels,
                    canvasSize = canvasSize,
                    typeface = spaceMonoTypeface
                )

                // Draw the main knob with drop shadow
                drawKnob(
                    center = center,
                    outerRadius = knobOuterRadius,
                    innerRadius = knobInnerRadius,
                    knobColor = config.knobColor,
                    borderColor = config.knobBorderColor,
                    rotation = animatedRotation,
                    grooveCount = numberOfSteps
                )

                // Draw pointer triangle
                drawPointer(
                    center = center,
                    radius = pointerRadius,
                    color = config.knobBorderColor,
                    rotation = animatedRotation,
                    pointerSize = pointerSize
                )
            }
        }
    }
}

/**
 * Draws the outer circle with protruding lines at each step position.
 */
private fun DrawScope.drawOuterCircle(
    center: Offset,
    radius: Float,
    lineRadius: Float,
    color: Color,
    numberOfSteps: Int
) {
    // Draw the main outer circle
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = 4f)
    )

    // Draw protruding lines at each step
    val degreesPerStep = 360f / numberOfSteps
    for (i in 0 until numberOfSteps) {
        // Offset by -90 degrees so 0 is at top
        val angleDegrees = i * degreesPerStep - 90f
        val angleRadians = angleDegrees * (PI / 180f).toFloat()

        val innerPoint = Offset(
            center.x + radius * cos(angleRadians),
            center.y + radius * sin(angleRadians)
        )
        val outerPoint = Offset(
            center.x + lineRadius * cos(angleRadians),
            center.y + lineRadius * sin(angleRadians)
        )

        drawLine(
            color = color,
            start = innerPoint,
            end = outerPoint,
            strokeWidth = 4f
        )
    }
}

/**
 * Draws the step labels outside the outer circle.
 */
private fun DrawScope.drawStepNumbers(
    center: Offset,
    radius: Float,
    color: Color,
    stepLabels: List<String>,
    canvasSize: Float,
    typeface: Typeface?
) {
    val numberOfSteps = stepLabels.size
    val degreesPerStep = 360f / numberOfSteps
    val textSize = canvasSize * 0.08f

    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            this.color = color.toArgb()
            this.textSize = textSize
            this.textAlign = android.graphics.Paint.Align.CENTER
            this.typeface = typeface ?: Typeface.DEFAULT_BOLD
            this.isAntiAlias = true
        }

        for (i in stepLabels.indices) {
            // Offset by -90 degrees so index 0 is at top
            val angleDegrees = i * degreesPerStep - 90f
            val angleRadians = angleDegrees * (PI / 180f).toFloat()

            val x = center.x + radius * cos(angleRadians)
            val y = center.y + radius * sin(angleRadians) + textSize / 3f

            canvas.nativeCanvas.drawText(stepLabels[i], x, y, paint)
        }
    }
}

/**
 * Draws the main turning knob with outer and inner circles including drop shadow.
 */
private fun DrawScope.drawKnob(
    center: Offset,
    outerRadius: Float,
    innerRadius: Float,
    knobColor: Color,
    borderColor: Color,
    rotation: Float,
    grooveCount: Int
) {
    // Draw outer knob fill
    drawCircle(
        color = knobColor,
        radius = outerRadius,
        center = center
    )

    // Draw outer knob border
    drawCircle(
        color = borderColor,
        radius = outerRadius,
        center = center,
        style = Stroke(width = 2f)
    )

    // Draw decorative grooves on the outer knob (aircraft style)
    // Skip the groove at position 0 where the pointer is
    val grooveDegreesStep = 360f / grooveCount
    for (i in 1 until grooveCount) {
        val angleDegrees = i * grooveDegreesStep + rotation - 90f
        val angleRadians = angleDegrees * (PI / 180f).toFloat()

        val grooveInnerRadius = outerRadius * 0.75f
        val grooveOuterRadius = outerRadius * 0.95f

        val innerPoint = Offset(
            center.x + grooveInnerRadius * cos(angleRadians),
            center.y + grooveInnerRadius * sin(angleRadians)
        )
        val outerPoint = Offset(
            center.x + grooveOuterRadius * cos(angleRadians),
            center.y + grooveOuterRadius * sin(angleRadians)
        )

        drawLine(
            color = borderColor.copy(alpha = 0.3f),
            start = innerPoint,
            end = outerPoint,
            strokeWidth = 1.5f
        )
    }

    // Draw drop shadow for inner knob
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.Black.copy(alpha = 0.5f),
                Color.Transparent
            ),
            center = center.copy(y = center.y + 4f),
            radius = innerRadius + 8f
        ),
        radius = innerRadius + 8f,
        center = center.copy(y = center.y + 4f)
    )

    // Draw inner knob fill
    drawCircle(
        color = knobColor,
        radius = innerRadius,
        center = center
    )

    // Draw inner knob border
    drawCircle(
        color = borderColor,
        radius = innerRadius,
        center = center,
        style = Stroke(width = 2f)
    )
}

/**
 * Draws the triangular pointer indicating the current position.
 */
private fun DrawScope.drawPointer(
    center: Offset,
    radius: Float,
    color: Color,
    rotation: Float,
    pointerSize: Float,
) {
    // Calculate the rotation angle (offset by -90 so 0 degrees is at top)
    val angleRadians = (rotation - 90f) * (PI / 180f).toFloat()

    // Calculate pointer tip position
    val tipX = center.x + radius * cos(angleRadians)
    val tipY = center.y + radius * sin(angleRadians)

    // Calculate the base positions (perpendicular to the direction)
    val perpAngle = angleRadians + (PI / 2f).toFloat()
    val baseDistance = radius - pointerSize

    val baseCenterX = center.x + baseDistance * cos(angleRadians)
    val baseCenterY = center.y + baseDistance * sin(angleRadians)

    val baseLeft = Offset(
        baseCenterX + (pointerSize / 2f) * cos(perpAngle),
        baseCenterY + (pointerSize / 2f) * sin(perpAngle)
    )
    val baseRight = Offset(
        baseCenterX - (pointerSize / 2f) * cos(perpAngle),
        baseCenterY - (pointerSize / 2f) * sin(perpAngle)
    )

    val path = Path().apply {
        moveTo(tipX, tipY)
        lineTo(baseLeft.x, baseLeft.y)
        lineTo(baseRight.x, baseRight.y)
        close()
    }

    // Draw pointer shadow
    val shadowPath = Path().apply {
        moveTo(tipX + 2f, tipY + 2f)
        lineTo(baseLeft.x + 2f, baseLeft.y + 2f)
        lineTo(baseRight.x + 2f, baseRight.y + 2f)
        close()
    }

    drawPath(
        path = shadowPath,
        color = Color.Black.copy(alpha = 0.3f)
    )

    // Draw pointer
    drawPath(
        path = path,
        color = color
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TurningKnobPreviewLazer() {
    Box(
        modifier = Modifier
            .size(320.dp)
            .background(dgenBlack),
        contentAlignment = Alignment.Center
    ) {
        TurningKnob(
            config = TurningKnobConfig(
                outerLineColor = lazerCore,
                knobColor = lazerBurn,
                knobBorderColor = lazerCore
            ),
            size = 280.dp,
            initialStep = 0
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TurningKnobPreviewOcean() {
    Box(
        modifier = Modifier
            .size(320.dp)
            .background(dgenBlack),
        contentAlignment = Alignment.Center
    ) {
        TurningKnob(
            config = TurningKnobConfig(
                outerLineColor = Color(0xFF00E4FF),
                knobColor = Color(0xFF051C1F),
                knobBorderColor = Color(0xFF00E4FF)
            ),
            size = 280.dp,
            initialStep = 5
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TurningKnobPreviewCustomLabels() {
    Box(
        modifier = Modifier
            .size(320.dp)
            .background(dgenBlack),
        contentAlignment = Alignment.Center
    ) {
        TurningKnob(
            config = TurningKnobConfig(
                outerLineColor = Color(0xFFF08E29),
                knobColor = Color(0xFF231404),
                knobBorderColor = Color(0xFFF08E29),
                stepLabels = listOf("OFF", "1", "2", "3", "4", "5", "6", "7", "8", "MAX")
            ),
            size = 280.dp,
            initialStep = 0
        )
    }
}
