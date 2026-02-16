package com.example.dgenlibrary.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.lazerCore

@Composable
fun ClickableMessage(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    styledMessage: AnnotatedString,
    style: TextStyle,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (Int) -> Unit = {},
    onLongClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
) {
    val maxHeight = 500.dp

    var expanded by remember { mutableStateOf(false) }
    var textHeight by remember { mutableStateOf(0.dp) }

    val animatedHeight by animateDpAsState(targetValue = if (expanded) textHeight else 300.dp)

    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }

    val fadeModifier = when (textHeight > maxHeight) {
        true -> {
            Modifier.drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, backgroundColor),
                        startY = size.height - 80f,
                        endY = size.height
                    )
                )
            }
        }

        false -> {
            Modifier
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        BasicText(
            text = styledMessage,
            style = style,
            modifier = modifier
                .heightIn(max = animatedHeight)
                .onGloballyPositioned { coordinates ->
                    textHeight = coordinates.size.height.dp
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongClick()
                        },
                        onTap = { pos ->
                            layoutResult.value?.let { layoutResult ->
                                onClick(layoutResult.getOffsetForPosition(pos))
                            }
                        },
                        onDoubleTap = {
                            onDoubleClick()
                        }
                    )
                }
                .then(
                    if (expanded) {
                        Modifier
                    } else {
                        fadeModifier
                    }
                ),
            onTextLayout = {
                layoutResult.value = it
                onTextLayout(it)
            }
        )

        if (textHeight > maxHeight) {
            Text(
                if (expanded) "Show less" else "Read more...",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = dgenWhite,
                fontFamily = PitagonsSans,
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            expanded = !expanded
                        },
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun ClickableMessagePreview() {
    ClickableMessage(
        backgroundColor = lazerCore,
        styledMessage = AnnotatedString(
            "This is a sample message that demonstrates the ClickableMessage composable. " +
                    "It supports long text with read more functionality."
        ),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = dgenWhite,
            fontFamily = PitagonsSans
        )
    )
}
