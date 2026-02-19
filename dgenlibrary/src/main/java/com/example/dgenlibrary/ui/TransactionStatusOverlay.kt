package com.example.dgenlibrary.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.backgrounds.GlobeBackground
import com.example.dgenlibrary.ui.text.TransactionHelpText
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.extraLargeEnterDuration
import com.example.dgenlibrary.ui.theme.extraLargeExitDuration
import com.example.dgenlibrary.ui.theme.mediumEnterDuration
import com.example.dgenlibrary.ui.theme.pulseOpacity
import kotlinx.coroutines.delay

sealed class TransactionStatus {
    object PENDING : TransactionStatus()
    object SUCCESS : TransactionStatus()
    data class FAILURE(val errorMessage: String? = null) : TransactionStatus()
}

/**
 * Full-screen animated overlay that displays transaction status with a pulsating globe gif,
 * configurable status messages, and contextual help text for failures.
 *
 * The gif ImageLoader is created internally — callers only need to provide the status and colors.
 *
 * @param status Current transaction status, or null to hide the overlay
 * @param primaryColor Primary text color
 * @param secondaryColor Reserved for future use / caller theming
 * @param pendingMessage Message shown during the PENDING state
 * @param successMessage Message shown during the SUCCESS state
 * @param failureMessage Default message shown during FAILURE when no errorMessage is provided
 * @param additionalHelpRules Extra keyword→help-text rules appended to the built-in defaults
 * @param onDismiss Called when the overlay auto-dismisses after success or failure
 * @param dismissDelay How long (ms) to show the success/failure state before dismissing
 */
@Composable
fun TransactionStatusOverlay(
    status: TransactionStatus?,
    primaryColor: Color,
    secondaryColor: Color,
    pendingMessage: String = "Transaction Pending...",
    successMessage: String = "Transaction Confirmed!",
    failureMessage: String = "Transaction Failed",
    additionalHelpRules: List<Pair<String, String>> = emptyList(),
    onDismiss: () -> Unit,
    dismissDelay: Long = 5000L
) {
    var displayStatus by remember { mutableStateOf<TransactionStatus?>(null) }

    LaunchedEffect(status) {
        if (status != null) {
            displayStatus = status
        }
    }

    LaunchedEffect(displayStatus) {
        if (displayStatus is TransactionStatus.SUCCESS || displayStatus is TransactionStatus.FAILURE) {
            delay(dismissDelay)
            onDismiss()
            displayStatus = null
        }
    }

    AnimatedVisibility(
        visible = displayStatus != null,
        enter = fadeIn(animationSpec = tween(durationMillis = mediumEnterDuration)),
        exit = fadeOut(animationSpec = tween(durationMillis = mediumEnterDuration))
    ) {
        val currentStatus = displayStatus ?: return@AnimatedVisibility

        val infiniteTransition = rememberInfiniteTransition(label = "statusTransition")

        val pulsatingAlpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = pulseOpacity,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulsatingAlpha"
        )

        val blinkingAlpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "blinkingAlpha"
        )

        val targetColor = when (currentStatus) {
            is TransactionStatus.PENDING -> dgenTurqoise
            is TransactionStatus.SUCCESS -> dgenGreen
            is TransactionStatus.FAILURE -> dgenRed
        }

        val animatedBaseColor by animateColorAsState(
            targetValue = targetColor,
            animationSpec = tween(durationMillis = 2000),
            label = "baseColorAnimation"
        )

        GlobeBackground(
            modifier = Modifier.pointerInput(Unit) { detectTapGestures { } },
            primaryColor = animatedBaseColor,
            globeAlpha = pulsatingAlpha,
            backgroundColor = dgenBlack
        ) {
            AnimatedContent(
                modifier = Modifier.offset(y = (-54).dp),
                targetState = currentStatus,
                transitionSpec = {
                    fadeIn(animationSpec = tween(extraLargeEnterDuration)) togetherWith
                            fadeOut(animationSpec = tween(extraLargeExitDuration))
                },
                label = "textAnimation"
            ) { targetStatus ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val mainText = when (targetStatus) {
                        is TransactionStatus.PENDING -> pendingMessage
                        is TransactionStatus.SUCCESS -> successMessage
                        is TransactionStatus.FAILURE -> failureMessage
                    }

                    Text(
                        text = mainText.uppercase(),
                        style = TextStyle(
                            fontFamily = SpaceMono,
                            color = primaryColor.copy(alpha = blinkingAlpha),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    if (targetStatus is TransactionStatus.FAILURE) {
                        TransactionHelpText(
                            modifier = Modifier,
                            errorMessage = targetStatus.errorMessage,
                            primaryColor = primaryColor,
                            alpha = blinkingAlpha,
                            additionalRules = additionalHelpRules
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, widthDp = 420, heightDp = 420)
@Composable
private fun TransactionStatusOverlayPendingPreview() {
    TransactionStatusOverlay(
        status = TransactionStatus.PENDING,
        primaryColor = dgenTurqoise,
        secondaryColor = dgenBlack,
        onDismiss = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, widthDp = 420, heightDp = 420)
@Composable
private fun TransactionStatusOverlaySuccessPreview() {
    TransactionStatusOverlay(
        status = TransactionStatus.SUCCESS,
        primaryColor = dgenGreen,
        secondaryColor = dgenBlack,
        onDismiss = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, widthDp = 420, heightDp = 420)
@Composable
private fun TransactionStatusOverlayFailurePreview() {
    TransactionStatusOverlay(
        status = TransactionStatus.FAILURE("Insufficient funds"),
        primaryColor = dgenRed,
        secondaryColor = dgenBlack,
        failureMessage = "Swap Failed",
        onDismiss = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, widthDp = 420, heightDp = 420)
@Composable
private fun TransactionStatusOverlayCustomMessagePreview() {
    TransactionStatusOverlay(
        status = TransactionStatus.PENDING,
        primaryColor = dgenTurqoise,
        secondaryColor = dgenBlack,
        pendingMessage = "Swap Pending...",
        successMessage = "Swap Confirmed!",
        failureMessage = "Swap Failed",
        additionalHelpRules = listOf(
            "slippage" to "Price moved too much. Try increasing slippage tolerance",
            "liquidity" to "Not enough liquidity for this swap"
        ),
        onDismiss = {}
    )
}
