package com.example.dgenlibrary.calculator

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.R
import com.example.dgenlibrary.SystemColorManager.primaryColor
import com.example.dgenlibrary.SystemColorManager.secondaryColor
import com.example.dgenlibrary.ui.theme.IconButtonSize
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
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
    fontFamily: FontFamily = FontFamily(Font(R.font.pitagonsanstext_semibold)),
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
        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.Center
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = fontFamily,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                ),
                color = textColor)
        }

    }
}

@Composable
fun CircularIconButton(
    modifier: Modifier = Modifier,
    size: Dp = IconButtonSize,
    icon: @Composable () -> Unit,
    borderColor: Color = Color.Transparent,
    containerColor: Color,
    onClick: () -> Unit,
) {

    val hapticFeedback = LocalHapticFeedback.current

    IconButton(
        onClick = {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor
        ),

        modifier = modifier.size(size = size).clip(CircleShape).border(2.dp, borderColor, CircleShape)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            Alignment.Center
        ) {
            icon()
        }

    }
}

@Preview
@Composable
fun CircularButtonPreview(){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ){
            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.sign),
                        contentDescription = "QR-Code",
                        tint = primaryColor
                    )
                },
                containerColor = secondaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )
            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.percent),
                        contentDescription = "QR-Code",
                        tint = primaryColor
                    )
                },
                containerColor = secondaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Absolute.spacedBy(16.dp),
        ){
            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.multiply),
                        contentDescription = "QR-Code",
                        tint = primaryColor
                    )
                },
                containerColor = secondaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )

            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.divide),
                        contentDescription = "QR-Code",
                        tint = primaryColor
                    )
                },
                containerColor = secondaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Absolute.spacedBy(16.dp),
        ){
            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.add),
                        contentDescription = "QR-Code",
                        tint = primaryColor
                    )
                },
                containerColor = secondaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )

            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "QR-Code",
                        tint = primaryColor
                    )
                },
                containerColor = secondaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Absolute.spacedBy(16.dp),
        ){
            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(R.drawable.clear),
                        contentDescription = "QR-Code",
                        tint = secondaryColor
                    )
                },
                containerColor = primaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )
            CircularIconButton(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(R.drawable.equal),
                        contentDescription = "QR-Code",
                        tint = secondaryColor
                    )
                },
                containerColor = primaryColor,
                borderColor = primaryColor,
                onClick = {  }
            )
        }
    }
}