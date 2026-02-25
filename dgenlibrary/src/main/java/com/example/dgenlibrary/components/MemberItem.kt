package com.example.dgenlibrary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    header: String,
    subheader: String = "",
    primaryColor: Color = dgenTurqoise,
    actionButton: @Composable (() -> Unit)? = null
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = header,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                )
            )
            if (subheader.isNotEmpty()) {
                Text(
                    text = subheader,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (actionButton != null) {
            actionButton()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, name = "With Subheader")
@Composable
private fun MemberItemPreview() {
    MemberItem(
        header = "alice.eth",
        subheader = "0x1234...abcd",
        primaryColor = dgenTurqoise,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, name = "Header Only")
@Composable
private fun MemberItemHeaderOnlyPreview() {
    MemberItem(
        header = "0x1234ab...5678ef",
        primaryColor = dgenTurqoise,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, name = "With Action Button")
@Composable
private fun MemberItemWithActionButtonPreview() {
    MemberItem(
        header = "Bob",
        subheader = "bob.base.eth",
        primaryColor = dgenTurqoise,
        modifier = Modifier.padding(horizontal = 16.dp),
        actionButton = {
            IconButton(
                onClick = {},
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PersonRemove,
                    contentDescription = "Remove member",
                    tint = dgenRed.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF050505,
    name = "DDevice",
    device = "spec:width=720px,height=720px,dpi=240"
)
@Composable
private fun MemberItemDDevicePreview() {
    MemberItem(
        header = "charlie.base.eth",
        subheader = "0x9876...5432",
        primaryColor = dgenTurqoise,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
