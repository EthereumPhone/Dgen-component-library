package com.example.dgenlibrary

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.label_fontSize
import com.example.dgenlibrary.ui.theme.oceanAbyss
import com.example.dgenlibrary.ui.theme.oceanCore
import com.example.dgenlibrary.ui.theme.pulseOpacity

@Composable
fun DgenPrimaryButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    backgroundColor: Color = oceanAbyss,
    containerColor: Color = oceanCore,
    fontSize: TextUnit = label_fontSize,
    verticalPadding: Dp = 8.dp,
    horizontalPadding: Dp = 24.dp,
    onClick: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(size = 3.dp),
    enabled: Boolean = true
) {
    Surface(
        color = if (enabled) backgroundColor else backgroundColor.copy(pulseOpacity),
        shape = shape,
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                if (enabled) {
                    onClick()
                }
            }
        }
    ) {
        Text(
            text = text.uppercase(),
            color = if (enabled) containerColor else containerColor.copy(pulseOpacity),
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = fontSize,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding)
        )
    }
}

@Composable
fun DgenSecondaryButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    containerColor: Color = oceanCore,
    fontSize: TextUnit = label_fontSize,
    verticalPadding: Dp = 8.dp,
    horizontalPadding: Dp = 24.dp,
    onClick: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(size = 3.dp),
    enabled: Boolean = true
) {
    Surface(
        color = Color.Transparent,
        shape = shape,
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                if (enabled) {
                    onClick()
                }
            }
        }
    ) {
        Text(
            text = text.uppercase(),
            color = if (enabled) containerColor else containerColor.copy(pulseOpacity),
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = fontSize,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding)
        )
    }
}

@Composable
fun DgenBorderButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    containerColor: Color = oceanCore,
    fontSize: TextUnit = label_fontSize,
    verticalPadding: Dp = 8.dp,
    horizontalPadding: Dp = 24.dp,
    onClick: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(size = 3.dp),
    enabled: Boolean = true
) {
    Surface(
        color = Color.Transparent,
        shape = shape,
        modifier = modifier
            .border(2.dp, if (enabled) containerColor else containerColor.copy(pulseOpacity), shape)
            .pointerInput(Unit) {
                detectTapGestures {
                    if (enabled) {
                        onClick()
                    }
                }
            }
    ) {
        Text(
            text = text.uppercase(),
            color = if (enabled) containerColor else containerColor.copy(pulseOpacity),
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = fontSize,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding)
        )
    }
}

@Composable
fun DgenInstallButton(
    modifier: Modifier = Modifier,
    text: String = "Install",
    backgroundColor: Color = oceanAbyss,
    containerColor: Color = oceanCore,
    fontSize: TextUnit = 16.sp,
    verticalPadding: Dp = 4.dp,
    horizontalPadding: Dp = 12.dp,
    onClick: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(size = 3.dp),
    enabled: Boolean = true,
    isOpen: Boolean,
    isInstalling: Boolean = false,
    primaryColor: Color = backgroundColor,
    secondaryColor: Color = containerColor
) {
    // Calculate fixed width to accommodate both "INSTALL" and "OPEN" text.
    val buttonWidth = 100.dp

    Box(
        modifier = modifier
            .width(buttonWidth)
            .pointerInput(Unit) {
                detectTapGestures {
                    if (enabled && !isInstalling) {
                        onClick()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isInstalling,
            transitionSpec = {
                (fadeIn(animationSpec = tween(150)) + scaleIn(animationSpec = tween(300))) togetherWith
                    (fadeOut(animationSpec = tween(150)) + scaleOut(animationSpec = tween(300)))
            },
            label = "install button animation"
        ) { targetLoading ->
            if (targetLoading) {
                DgenLoadingMatrix(
                    size = 24.dp,
                    LEDSize = 6.dp,
                    unactiveLEDColor = secondaryColor,
                    activeLEDColor = primaryColor
                )
            } else {
                Surface(
                    color = if (enabled) backgroundColor else backgroundColor.copy(pulseOpacity),
                    shape = shape,
                    modifier = Modifier.width(buttonWidth)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text.uppercase(),
                            color = if (enabled) containerColor else containerColor.copy(pulseOpacity),
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                fontWeight = FontWeight.Bold,
                                fontSize = fontSize,
                                lineHeight = fontSize,
                                letterSpacing = 0.sp,
                                textDecoration = TextDecoration.None,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
