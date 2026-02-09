package com.example.dgenlibrary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono

@Composable
fun AmountButton(
    amount: Int,
    isSelected: Boolean,
    primaryColor: Color,
    secondaryColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(64.dp)
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) primaryColor else Color.Transparent)
            .border(BorderStroke(1.dp, primaryColor), RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = PitagonsSans,
                        color = if (isSelected) secondaryColor else primaryColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    )
                ) {
                    append("\$")
                }
                append("$amount")
            },
            style = TextStyle(
                fontFamily = SpaceMono,
                color = if (isSelected) secondaryColor else primaryColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        )
    }
}