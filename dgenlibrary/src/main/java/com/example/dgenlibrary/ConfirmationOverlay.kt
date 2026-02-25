package com.example.dgenlibrary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.dgenlibrary.button.DgenPrimaryButton
import com.example.dgenlibrary.button.DgenSecondaryButton
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.neonOpacity
import com.example.dgenlibrary.ui.theme.oceanAbyss
import com.example.dgenlibrary.ui.theme.oceanCore

@Composable
fun ConfirmationOverlay(
    visible: Boolean = true,
    description: String,
    extraDescription: String? = null,
    primaryColor: Color,
    secondaryColor: Color,
    cancelButtonText: String = "CANCEL",
    confirmButtonText: String = "CONFIRM",
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        // Layer 1: full-screen clickable background. Tapping it will cancel.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(dgenBlack)
                .pointerInput(Unit) {
                    detectTapGestures {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        onCancel()
                    }
                }
        )

        // Layer 2: centred content column that intercepts taps (so taps don't reach background)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { /* Intercept to prevent dismissal */ },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                // Message Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = description,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = PitagonsSans,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = primaryColor,
                            lineHeight = 32.sp,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    extraDescription?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = PitagonsSans,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = primaryColor.copy(neonOpacity),
                                lineHeight = 24.sp,
                                letterSpacing = 0.sp,
                                textDecoration = TextDecoration.None
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Buttons Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DgenPrimaryButton(
                        text = cancelButtonText,
                        backgroundColor = primaryColor,
                        containerColor = secondaryColor,
                        onClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onCancel()
                        }
                    )
                    
                    Spacer(modifier = Modifier.width(24.dp))

                    DgenSecondaryButton(
                        text = confirmButtonText,
                        containerColor = primaryColor,
                        onClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onConfirm()
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Confirmation Overlay - Default",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360
)
@Composable
private fun ConfirmationOverlayPreview() {
    ConfirmationOverlay(
        visible = true,
        description = "Delete this contact?",
        extraDescription = "This action cannot be undone.",
        primaryColor = oceanAbyss,
        secondaryColor = oceanCore,
        cancelButtonText = "CANCEL",
        confirmButtonText = "DELETE",
        onCancel = {},
        onConfirm = {}
    )
}

@Preview(
    name = "Confirmation Overlay - No Extra Description",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360
)
@Composable
private fun ConfirmationOverlayNoExtraPreview() {
    ConfirmationOverlay(
        visible = true,
        description = "Are you sure you want to continue?",
        extraDescription = null,
        primaryColor = oceanAbyss,
        secondaryColor = oceanCore,
        onCancel = {},
        onConfirm = {}
    )
}