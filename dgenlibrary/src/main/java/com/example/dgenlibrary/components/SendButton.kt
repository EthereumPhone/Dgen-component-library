package com.example.dgenlibrary.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SendButton(
    background: Color,
    list: List<String>,
    selectedTab: MutableState<Int>,
    onClick: () -> Unit,
) {
    val haptics = LocalHapticFeedback.current
    var showTooltip by remember { mutableStateOf(false) }

    var buttonPosition by remember { mutableStateOf(IntOffset.Zero) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    showTooltip = !showTooltip
                },
                onPress = {
                    showTooltip = !showTooltip
                },
                onTap = {
                    showTooltip = !showTooltip
                },
                onLongPress = {
                    showTooltip = !showTooltip
                }
            )
        }
    ) {
        AnimatedVisibility(
            visible = showTooltip,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }),
        ) {
            Popup(
                offset = IntOffset(
                    x = 0,
                    y = -(buttonSize.height + 100)
                ),
                alignment = Alignment.TopCenter
            ) {
                TooltipContent(
                    selectedTab = selectedTab,
                    list = list,
                    onDismiss = { showTooltip = false }
                )
            }
        }

        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .clip(CircleShape)
                .background(background)
                .size(42.dp)
                .onGloballyPositioned { coordinates ->
                    buttonPosition = coordinates
                        .positionInWindow()
                        .run { IntOffset(x.roundToInt(), y.roundToInt()) }
                    buttonSize = coordinates.size
                        .toSize()
                        .run { IntSize(width.roundToInt(), height.roundToInt()) }
                }
                .combinedClickable(
                    onClick = { onClick() },
                    onLongClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        showTooltip = true
                    },
                )
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowUpward,
                modifier = Modifier
                    .size(32.dp),
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}

@Composable
fun TooltipContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    list: List<String>,
    selectedTab: MutableState<Int>
) {
    Card(
        shape = CircleShape,
        colors = CardDefaults.elevatedCardColors(
            containerColor = dgenBlack,
            contentColor = dgenWhite,
        ),
        elevation = cardElevation(8.dp),
        border = BorderStroke(2.dp, dgenWhite),
        modifier = modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(dgenBlack)
                .padding(8.dp)
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max)
        ) {
            CustomTabSelector(
                selectedTab,
                list,
                {
                    selectedTab.value = it
                    onDismiss()
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun SendButtonPreview() {
    val selectedTab = remember { mutableIntStateOf(0) }
    SendButton(
        background = dgenTurqoise,
        list = listOf("SMS", "XMTP"),
        selectedTab = selectedTab,
        onClick = {}
    )
}
