package com.example.dgenlibrary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.lazerCore
import com.example.dgenlibrary.ui.theme.pulseOpacity

@Composable
fun ChatListInfo(
    header: String = "Header",
    subheader: String = "Subheader",
    unReadMessagesAmount: Int = 1,
    time: String? = null,
    isGroup: Boolean = false,
    readConversation: Boolean = false,
    primaryColor: Color,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 32.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = header,
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = if (readConversation) primaryColor.copy(pulseOpacity) else primaryColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.widthIn(min = 80.dp, max = 180.dp)
            )
            Text(
                text = subheader,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = if (readConversation) dgenWhite.copy(pulseOpacity) else dgenWhite,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .weight(0.25f)
        ) {
            time?.let {
                Text(
                    text = it.uppercase(),
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        color = if (readConversation) primaryColor.copy(pulseOpacity) else primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun PreviewChatListInfo() {
    ChatListInfo(
        header = "Alex Lynn",
        subheader = "The dGEN1 is sick!!!",
        time = "10:00",
        isGroup = false,
        primaryColor = lazerCore,
        onClick = {}
    )
}
