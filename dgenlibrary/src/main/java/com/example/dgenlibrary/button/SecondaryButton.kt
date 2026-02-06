package com.example.dgenlibrary.button

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.label_fontSize
import com.example.dgenlibrary.ui.theme.oceanCore
import com.example.dgenlibrary.ui.theme.pulseOpacity

@Composable
fun DgenSecondaryButton(
    modifier: Modifier = Modifier,
    text: String = "Button",
    containerColor: Color = oceanCore,
    fontSize: TextUnit = label_fontSize,
    verticalPadding: Dp = 8.dp,
    horizontalPadding: Dp = 24.dp,
    onClick: () -> Unit = {},
    shape: RoundedCornerShape = RoundedCornerShape(size = 3.dp),
    enabled: Boolean = true,
){
    Surface(
        color = Color.Transparent,
        shape = shape,
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures {
                if(enabled){
                    onClick()
                }
            }
        },
    ) {
        Text(
            text = text.uppercase(),
            color = if(enabled) containerColor else containerColor.copy(pulseOpacity),
            style = TextStyle(
                fontFamily = SpaceMono,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = fontSize,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding)
        )
    }
}

@Preview(
    name = "Secondary Button - Default",
    showBackground = true,
    backgroundColor = 0xFF050505
)
@Composable
private fun DgenSecondaryButtonPreview() {
    DgenSecondaryButton(text = "Cancel")
}

@Preview(
    name = "Secondary Button - Disabled",
    showBackground = true,
    backgroundColor = 0xFF050505
)
@Composable
private fun DgenSecondaryButtonDisabledPreview() {
    DgenSecondaryButton(text = "Cancel", enabled = false)
}

@Preview(
    name = "Secondary Button - Long Text",
    showBackground = true,
    backgroundColor = 0xFF050505
)
@Composable
private fun DgenSecondaryButtonLongTextPreview() {
    DgenSecondaryButton(text = "Back to list")
}