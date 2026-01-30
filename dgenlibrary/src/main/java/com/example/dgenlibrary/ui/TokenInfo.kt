package com.example.dgenlibrary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * A component for displaying token information with a title and description.
 * Commonly used for market cap, price change, volume, and other token metrics.
 *
 * @param modifier Modifier to be applied to the component
 * @param title The label/title text (e.g., "MCAP", "24HΔ")
 * @param description The value text (e.g., "$1.2M", "+15.5%")
 * @param descriptionColor Color for the description text (useful for indicating positive/negative values)
 * @param primaryColor Color for the title text
 * @param size Maximum width of the component
 */
@Composable
fun TokenInfo(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    descriptionColor: Color = dgenWhite,
    primaryColor: Color,
    size: Dp = 110.dp
) {
    Column(
        modifier = modifier.widthIn(max = size)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = SpaceMono,
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 1.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = description,
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = descriptionColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenInfoPreview() {
    TokenInfo(
        title = "MCAP",
        description = "$1.25M",
        primaryColor = dgenGreen
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenInfoPositivePreview() {
    Box(modifier = Modifier.background(dgenBlack).padding(8.dp)) {
        TokenInfo(
            title = "24HΔ",
            description = "+15.5%",
            descriptionColor = dgenGreen,
            primaryColor = dgenGreen
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenInfoNegativePreview() {
    Box(modifier = Modifier.background(dgenBlack).padding(8.dp)) {
        TokenInfo(
            title = "24HΔ",
            description = "-8.3%",
            descriptionColor = dgenRed,
            primaryColor = dgenRed
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenInfoVolumePreview() {
    Box(modifier = Modifier.background(dgenBlack).padding(8.dp)) {
        TokenInfo(
            title = "VOLUME",
            description = "$500K",
            primaryColor = dgenGreen,
            size = 100.dp
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun TokenInfoPreviewDDevice() {
    Box(modifier = Modifier.background(dgenBlack).padding(16.dp)) {
        TokenInfo(
            title = "MCAP",
            description = "$1.25M",
            primaryColor = dgenGreen
        )
    }
}