package com.example.dgenlibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.pulseOpacity

@Composable
fun Quote(
    title: String,
    content: String,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier
            .offset(y = 5.dp)
            .height(77.dp)
            .width(8.dp)
            .background(primaryColor.copy(pulseOpacity))
            .padding(end = 16.dp)
        )
        Column {
            Text(
                buildAnnotatedString {
                    append(title)
                },
                fontFamily = SpaceMono,
                color = primaryColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None,
                modifier = Modifier.offset(y = 8.dp)
            )
            Text(
                content,
                fontFamily = PitagonsSans,
                color = dgenWhite,
                fontWeight = FontWeight.SemiBold,
                fontSize = 48.sp,
                lineHeight = 48.sp,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            )
        }
    }
}

@Composable
fun Quote(
    title: String,
    primaryColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier
            .offset(y = 5.dp)
            .height(77.dp)
            .width(8.dp)
            .background(primaryColor.copy(pulseOpacity))
            .padding(end = 16.dp)
        )
        Column {
            Text(
                buildAnnotatedString {
                    append(title)
                },
                fontFamily = SpaceMono,
                color = primaryColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None,
                modifier = Modifier.offset(y = 8.dp)
            )
            content()
        }
    }
}

@Composable
fun Quote(
    primaryColor: Color,
    modifier: Modifier = Modifier,
    title: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier
            .offset(y = 5.dp)
            .height(77.dp)
            .width(8.dp)
            .background(primaryColor.copy(pulseOpacity))
            .padding(end = 16.dp)
        )
        Column {
            title()
            content()
        }
    }
}