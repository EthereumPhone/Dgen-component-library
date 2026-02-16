package com.example.dgenlibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.lazerCore

val ChatBubbleShape = RoundedCornerShape(32.dp, 32.dp, 32.dp, 32.dp)
val UserChatBubbleShape = RoundedCornerShape(32.dp, 32.dp, 32.dp, 32.dp)

val LastChatBubbleShape = RoundedCornerShape(20.dp, 32.dp, 32.dp, 4.dp)
val LastUserChatBubbleShape = RoundedCornerShape(32.dp, 20.dp, 4.dp, 32.dp)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MessageBubble(
    messageText: String,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean = false,
    time: String = "",
    isDelivered: Boolean = false,
    isFailed: Boolean = false,
    primaryColor: Color,
    secondaryColor: Color,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
    mediaContent: (@Composable () -> Unit)? = null,
) {
    val bubbleShape = if (isUserMe) {
        if (isFirstMessageByAuthor) {
            LastUserChatBubbleShape
        } else {
            UserChatBubbleShape
        }
    } else {
        if (isFirstMessageByAuthor) {
            LastChatBubbleShape
        } else {
            ChatBubbleShape
        }
    }

    val messageBrush = when (isUserMe) {
        true -> primaryColor
        false -> secondaryColor
    }

    Column(
        horizontalAlignment = if (isUserMe) Alignment.End else Alignment.Start,
        modifier = modifier
            .clip(bubbleShape)
            .background(messageBrush)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongClick() },
                    onDoubleTap = { onDoubleClick() }
                )
            }
    ) {
        if (mediaContent != null) {
            Box(
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 0.dp)
                    .sizeIn(maxHeight = 256.dp, maxWidth = 256.dp)
            ) {
                mediaContent()
            }
        }

        FlowRow(
            modifier = Modifier
                .padding(end = 20.dp, start = 16.dp, top = 12.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (messageText.isNotBlank()) {
                Text(
                    text = messageText,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = dgenWhite,
                        fontFamily = PitagonsSans
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            AuthorNameTimestamp(
                time = time,
                isUserMe = isUserMe,
                primaryColor = primaryColor,
                isDelivered = isDelivered,
                isFailed = isFailed
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun MessageBubbleMePreview() {
    MessageBubble(
        messageText = "Hey, have you tried the dGEN1?",
        isUserMe = true,
        time = "14:30",
        isDelivered = true,
        primaryColor = lazerCore,
        secondaryColor = dgenBlack,
        isFirstMessageByAuthor = false
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun MessageBubbleTheirPreview() {
    MessageBubble(
        messageText = "Yes! It's amazing \uD83D\uDD25",
        isUserMe = false,
        time = "14:31",
        primaryColor = dgenTurqoise,
        secondaryColor = dgenTurqoise.copy(alpha = 0.2f),
        isFirstMessageByAuthor = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun MessageBubbleFailedPreview() {
    MessageBubble(
        messageText = "This message failed to send",
        isUserMe = true,
        time = "14:32",
        isFailed = true,
        primaryColor = lazerCore,
        secondaryColor = dgenBlack
    )
}
