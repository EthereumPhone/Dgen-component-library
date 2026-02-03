package com.example.dgenlibrary.calculator

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.IconButtonSize
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenTurqoise

@Composable
fun CircularButton(
    modifier: Modifier = Modifier,
    width: Dp = IconButtonSize,
    height: Dp = IconButtonSize,
    squareAspectRatio: Boolean = true,
    text: String,
    textColor: Color = dgenTurqoise,
    borderColor: Color = Color.Transparent,
    fontFamily: FontFamily = PitagonsSans,
    containerColor: Color,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    Button(
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = CircleShape,
        modifier = modifier.size(width = width, height = height)
            .then(
                if (squareAspectRatio) {
                    Modifier.aspectRatio(1F, matchHeightConstraintsFirst = true)
                } else {
                    Modifier
                }
            )
            .border(2.dp, borderColor, CircleShape)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                ),
                color = textColor)
        }

    }
}