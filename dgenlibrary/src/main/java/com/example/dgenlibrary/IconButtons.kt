package com.example.dgenlibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable ()-> Unit,
    title: @Composable (()-> Unit)? = null,
    size: Dp = DgenTheme.dimensions.IconButtonSize,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
){
    Box(
        modifier= modifier
        .size(size)
        .clip(CircleShape)
        .background(color = Color.Transparent)
        .clickable(
            onClick = onClick,
            enabled = enabled,
            role = Role.Button,
            interactionSource = interactionSource,
            indication = rememberRipple(
                bounded = false,
                radius = DgenTheme.dimensions.IconButtonSize / 2
            )
        ),
    contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon()
            if (title != null){
                title()
            }
        }
    }

}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
fun ActionButtonPreviewDDevice() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dgenBlack)
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        ActionButton(
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add",
                    tint = dgenTurqoise
                )
            },
            title = {
                Text(text = "ADD", color = dgenTurqoise)
            }
        )
    }
}