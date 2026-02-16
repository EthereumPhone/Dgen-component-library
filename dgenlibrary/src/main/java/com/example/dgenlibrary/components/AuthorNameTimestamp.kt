package com.example.dgenlibrary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

@Composable
fun AuthorNameTimestamp(
    time: String,
    isUserMe: Boolean,
    primaryColor: Color,
    isDelivered: Boolean = false,
    isFailed: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = time,
            fontSize = 12.sp,
            fontFamily = SpaceMono,
            modifier = Modifier
                .alignBy(LastBaseline),
            color = if (isUserMe) dgenWhite else primaryColor,
        )

        if (isUserMe) {
            Spacer(modifier = Modifier.width(4.dp))

            when {
                isFailed -> Icon(
                    imageVector = Icons.Rounded.Error,
                    contentDescription = "Failed",
                    tint = dgenWhite,
                    modifier = Modifier.size(16.dp)
                )

                isDelivered -> Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = "Delivered",
                    tint = dgenWhite,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun AuthorNameTimestampPreview() {
    AuthorNameTimestamp(
        time = "14:30",
        isUserMe = true,
        primaryColor = dgenTurqoise,
        isDelivered = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun AuthorNameTimestampFailedPreview() {
    AuthorNameTimestamp(
        time = "14:30",
        isUserMe = true,
        primaryColor = dgenTurqoise,
        isFailed = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun AuthorNameTimestampOtherPreview() {
    AuthorNameTimestamp(
        time = "14:30",
        isUserMe = false,
        primaryColor = dgenTurqoise
    )
}
