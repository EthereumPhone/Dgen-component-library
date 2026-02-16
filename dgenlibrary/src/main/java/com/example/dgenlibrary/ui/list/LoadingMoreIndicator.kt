package com.example.dgenlibrary.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.DgenLoadingMatrix
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.neonOpacity

/**
 * Loading indicator for pagination / loading-more states.
 *
 * @param primaryColor Color for the loading matrix LEDs
 * @param message Text to display alongside the loading indicator
 * @param isError Whether this is showing an error state (dims the LED color)
 */
@Composable
fun LoadingMoreIndicator(
    primaryColor: Color,
    message: String,
    isError: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DgenLoadingMatrix(
                size = 24.dp,
                LEDSize = 6.dp,
                activeLEDColor = if (isError) primaryColor.copy(alpha = 0.5f) else primaryColor,
                unactiveLEDColor = primaryColor.copy(alpha = 0.25f)
            )
            Text(
                text = message,
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = dgenWhite.copy(alpha = neonOpacity),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        }
    }
}
