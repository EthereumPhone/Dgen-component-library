package com.example.dgenlibrary.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.example.dgenlibrary.ui.theme.dgenBlack

@Composable
fun SelectionOverlay(
    visible: Boolean,
    primaryColor: Color,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
    headerContent: (@Composable RowScope.() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    overlayAlpha: Float = 0.9f,
    dismissOnBackgroundClick: Boolean = true,
    onBackgroundClick: (() -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(dgenBlack.copy(alpha = overlayAlpha))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (onBackgroundClick != null) {
                                onBackgroundClick()
                            } else if (dismissOnBackgroundClick) {
                                onCancelClick()
                            }
                        }
                    )
                }
        ) {
            if (content != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }

            SelectionBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = visible,
                primaryColor = primaryColor,
                onCancelClick = onCancelClick,
                showHeader = false,
                headerContent = headerContent,
                showCancelButton = false,
                actions = actions
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSelectionOverlay() {
    SelectionOverlay(
        visible = true,
        primaryColor = Color.White,
        onCancelClick = {},
        content = {
            androidx.compose.material3.Text(
                text = "Selected message preview shown here",
                color = Color.White
            )
        },
        actions = {
            SelectionBarColumn(
                imageVector = Icons.Outlined.Visibility,
                title = "Hide",
                primaryColor = Color.White,
                onClick = {}
            )
            SelectionBarColumn(
                imageVector = Icons.Outlined.VisibilityOff,
                title = "Unhide",
                primaryColor = Color.White,
                onClick = {}
            )
        }
    )
}
