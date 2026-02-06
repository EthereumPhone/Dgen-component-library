package com.example.dgenlibrary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    imageSize: Dp = 275.dp,
    primaryColor: Color
) {
    InfoScreen(
        modifier = modifier,
        description = "Connect to the internet to see apps.",
        imageSize = imageSize,
        primaryColor = primaryColor
    )
}
