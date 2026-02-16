package com.example.dgenlibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.model.GroupedReaction
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenDarkGray
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * Displays message reactions as compact pills below a message bubble.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReactionsDisplay(
    reactions: List<GroupedReaction>,
    isUserMe: Boolean,
    primaryColor: Color,
    onReactionClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (reactions.isEmpty()) return

    FlowRow(
        modifier = modifier.padding(
            start = if (isUserMe) 0.dp else 8.dp,
            end = if (isUserMe) 8.dp else 0.dp,
            top = 2.dp
        ),
        horizontalArrangement = if (isUserMe) Arrangement.End else Arrangement.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        reactions.forEach { grouped ->
            ReactionPill(
                emoji = grouped.emoji,
                count = grouped.count,
                isFromMe = grouped.isFromMe,
                primaryColor = primaryColor,
                isUserMe = isUserMe,
                onClick = { onReactionClick(grouped.emoji) }
            )
        }
    }
}

/**
 * A single reaction pill showing emoji and count.
 */
@Composable
fun ReactionPill(
    emoji: String,
    count: Int,
    isFromMe: Boolean,
    primaryColor: Color,
    isUserMe: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isFromMe -> primaryColor.copy(alpha = 0.3f)
        else -> dgenDarkGray.copy(alpha = 0.6f)
    }

    val borderColor = when {
        isFromMe -> primaryColor
        else -> Color.Transparent
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .then(
                if (isFromMe) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else Modifier
            )
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = emoji,
                fontSize = 14.sp
            )
            if (count > 1) {
                Text(
                    text = count.toString(),
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isUserMe) dgenWhite else primaryColor
                    )
                )
            }
        }
    }
}

/**
 * Common emoji reactions for the quick picker.
 */
object CommonReactions {
    val quickReactions = listOf("👍", "❤️", "😂", "😮", "😢", "🔥")
    val allReactions = listOf(
        "👍", "👎", "❤️", "💔", "😂", "😮", "😢", "😡",
        "🔥", "🎉", "✅", "❌", "👀", "🙏", "💯", "🚀"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ReactionsDisplayPreview() {
    val sampleReactions = listOf(
        GroupedReaction(emoji = "👍", count = 3, isFromMe = true),
        GroupedReaction(emoji = "❤️", count = 1, isFromMe = false),
    )

    ReactionsDisplay(
        reactions = sampleReactions,
        isUserMe = false,
        primaryColor = Color(0xFF00FF88),
        onReactionClick = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun ReactionPillPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ReactionPill(
            emoji = "👍",
            count = 3,
            isFromMe = true,
            primaryColor = Color(0xFF00FF88),
            isUserMe = false,
            onClick = {}
        )
        ReactionPill(
            emoji = "❤️",
            count = 1,
            isFromMe = false,
            primaryColor = Color(0xFF00FF88),
            isUserMe = false,
            onClick = {}
        )
    }
}
