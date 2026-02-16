package com.example.dgenlibrary.ui.token

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans

/**
 * A circular placeholder for token logos, displaying the first letter of the symbol.
 *
 * @param primaryColor Text color
 * @param secondaryColor Background color
 * @param size Diameter of the placeholder
 * @param symbol Token symbol (first letter is displayed)
 * @param modifier Modifier to be applied
 */
@Composable
fun TokenLogoPlaceholder(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    size: Dp = 32.dp,
    symbol: String,
) {
    Surface(
        modifier = modifier.size(size),
        shape = CircleShape,
        color = secondaryColor,
        contentColor = primaryColor,
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol.substring(0, 1).uppercase(),
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 18.sp,
                    textDecoration = TextDecoration.None
                )
            )
        }
    }
}
