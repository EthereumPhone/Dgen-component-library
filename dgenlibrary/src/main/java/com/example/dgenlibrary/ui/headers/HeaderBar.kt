package com.example.dgenlibrary.ui.headers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.mediumEnterDuration
import com.example.dgenlibrary.ui.theme.mediumExitDuration

/**
 * A reusable header bar component with optional title text, custom content, and close button.
 *
 * @param modifier Modifier to be applied to the header bar
 * @param text Optional title text. If empty, content slot will be used instead
 * @param enableCancel Whether to show the close button
 * @param content Custom content to display when text is empty
 * @param primaryColor The primary color for text and icons
 * @param onClick Callback when the close button is clicked
 */
@Composable
fun HeaderBar(
    modifier: Modifier = Modifier,
    text: String = "",
    enableCancel: Boolean = true,
    content: @Composable () -> Unit = {},
    primaryColor: Color,
    onClick: () -> Unit = {}
) {
    // Debouncing state to prevent multiple rapid clicks
    var lastClickTime by remember { mutableLongStateOf(0L) }
    val debounceDelay = 500L

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (text == "") {
            Box(modifier = Modifier.weight(1f, fill = false)) {
                content()
            }
        } else {
            Text(
                text = text.uppercase(),
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
        }

        AnimatedVisibility(
            modifier = Modifier.size(56.dp),
            visible = enableCancel,
            enter = fadeIn(tween(mediumEnterDuration)),
            exit = fadeOut(tween(mediumExitDuration))
        ) {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime >= debounceDelay) {
                        lastClickTime = currentTime
                        onClick()
                    }
                }
            ) {
                Box(modifier = Modifier.size(56.dp)) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Close",
                        tint = primaryColor
                    )
                }
            }
        }
    }
}

/**
 * Header bar variant with an epoch/timestamp text alongside the title.
 *
 * @param modifier Modifier to be applied to the header bar
 * @param text Title text
 * @param epochText Secondary text (e.g., timestamp or epoch)
 * @param enableCancel Whether to show the close button
 * @param primaryColor The primary color for text and icons
 * @param onClick Callback when the close button is clicked
 */
@Composable
fun HeaderBarWithEpoch(
    modifier: Modifier = Modifier,
    text: String = "",
    epochText: String = "",
    enableCancel: Boolean = true,
    primaryColor: Color,
    onClick: () -> Unit = {}
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    val debounceDelay = 500L

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f, fill = false),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = text.uppercase(),
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )

            Text(
                text = epochText,
                modifier = Modifier,
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        AnimatedVisibility(
            modifier = Modifier.size(56.dp),
            visible = enableCancel,
            enter = fadeIn(tween(mediumEnterDuration)),
            exit = fadeOut(tween(mediumExitDuration))
        ) {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickTime >= debounceDelay) {
                        lastClickTime = currentTime
                        onClick()
                    }
                }
            ) {
                Box(modifier = Modifier.size(56.dp)) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterEnd),
                        painter = painterResource(R.drawable.close),
                        contentDescription = "Close",
                        tint = primaryColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun HeaderBarPreview() {
    HeaderBar(
        text = "Filter & Sort",
        primaryColor = dgenGreen,
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun HeaderBarWithEpochPreview() {
    HeaderBarWithEpoch(
        text = "Token Details",
        epochText = "12:34:56",
        primaryColor = dgenGreen,
        onClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun HeaderBarNoCancelPreview() {
    HeaderBar(
        text = "Settings",
        enableCancel = false,
        primaryColor = dgenGreen,
        onClick = {}
    )
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun HeaderBarPreviewDDevice() {
    HeaderBar(
        text = "Filter & Sort",
        primaryColor = dgenGreen,
        onClick = {}
    )
}