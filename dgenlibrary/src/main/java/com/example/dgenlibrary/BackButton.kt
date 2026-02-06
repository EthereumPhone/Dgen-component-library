package com.example.dgenlibrary

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    color: Color = DgenTheme.colors.dgenWhite,
    size: Dp = 24.dp
) {
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .size(56.dp)
            .aspectRatio(1f),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(R.drawable.back_icon),
            contentDescription = "BackButton",
            modifier = modifier.size(size).graphicsLayer { translationX = -20f },
            tint = color
        )
    }
}
