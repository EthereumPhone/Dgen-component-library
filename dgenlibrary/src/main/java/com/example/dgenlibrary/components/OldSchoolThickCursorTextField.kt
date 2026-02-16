package com.example.dgenlibrary.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenTurqoise

@Composable
fun OldSchoolThickCursorTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable () -> Unit = {
        Text(
            text = "Type a message",
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = dgenTurqoise.copy(alpha = 0.45f),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        )
    },
    textStyle: TextStyle = LocalTextStyle.current,
    cursorColor: Color = MaterialTheme.colors.primary,
    cursorWidth: Float = 16f,
    cursorHeight: Float = 32f,
    blinkDuration: Int = 500,
    hasMultipleLines: MutableState<Boolean> = mutableStateOf(false),
    expand: MutableState<Boolean> = mutableStateOf(false),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxFieldHeight: Dp = 150.dp,
    cursorVerticalOffset: Dp = 18.dp,
    singleLine: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition()
    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(blinkDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    var lineCount by remember { mutableStateOf(1) }

    Box(
        modifier = modifier
            .heightIn(max = maxFieldHeight)
            .verticalScroll(scrollState),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.text.isBlank()) {
            placeholder()
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            modifier = modifier
                .onFocusChanged { isFocused = it.isFocused },
            cursorBrush = SolidColor(Color.Transparent),
            onTextLayout = { layoutResult ->
                lineCount = layoutResult.lineCount
                hasMultipleLines.value = (lineCount > 1)
                textLayoutResult = layoutResult

                if (hasMultipleLines.value && lineCount > 3) {
                    expand.value = true
                }
            },
            singleLine = singleLine
        ) { innerTextField ->
            Box(
                Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        textLayoutResult
                            ?.takeIf { value.selection.collapsed }
                            ?.let { tlr ->
                                val rect = tlr.getCursorRect(value.selection.start)

                                val y = rect.top -
                                        scrollState.value +
                                        (rect.height - cursorHeight) / 2f +
                                        cursorVerticalOffset.toPx()

                                drawRect(
                                    color = cursorColor.copy(alpha = if (isFocused) blinkAlpha else 0f),
                                    topLeft = Offset(rect.left, y),
                                    size = Size(cursorWidth, cursorHeight)
                                )
                            }
                    }
            ) {
                innerTextField()
            }
        }
    }
}

@Composable
fun OldSchoolThickCursorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    cursorColor: Color = MaterialTheme.colors.primary,
    cursorWidth: Float = 8f,
    cursorHeight: Float = 24f,
    blinkDuration: Int = 500,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(value))
    }

    OldSchoolThickCursorTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            if (it.text != value) {
                onValueChange(it.text)
            }
        },
        modifier = modifier,
        textStyle = textStyle,
        cursorColor = cursorColor,
        cursorWidth = cursorWidth,
        cursorHeight = cursorHeight,
        blinkDuration = blinkDuration,
        visualTransformation = visualTransformation
    )
}

@Composable
private fun TerminalPreviewSection(
    title: String,
    textColor: Color,
    initialText: String,
    backgroundColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(
            text = title,
            color = textColor,
            fontFamily = FontFamily.Monospace,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        var textFieldValue by remember {
            mutableStateOf(TextFieldValue(initialText))
        }

        OldSchoolThickCursorTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            textStyle = TextStyle(
                color = textColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp
            ),
            cursorColor = textColor,
            cursorWidth = 10f,
            cursorHeight = 20f,
            blinkDuration = 600,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 400,
    heightDp = 400,
    name = "Old School Terminal Preview with TextFieldValue"
)
@Composable
fun OldSchoolThickCursorPreview() {
    MaterialTheme {
        Surface(
            color = Color(0xFF121212)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Old School Terminal",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TerminalPreviewSection(
                    title = "C:\\> DIR",
                    textColor = Color(0xFF00FF00),
                    initialText = "AUTOEXEC.BAT CONFIG.SYS",
                    backgroundColor = Color(0xFF001100)
                )

                TerminalPreviewSection(
                    title = "C:\\> _",
                    textColor = Color(0xFFFFB700),
                    initialText = "",
                    backgroundColor = Color(0xFF332200)
                )

                TerminalPreviewSection(
                    title = "READY.",
                    textColor = Color(0xFF00AAFF),
                    initialText = "10 PRINT \"HELLO WORLD\"",
                    backgroundColor = Color(0xFF001133)
                )

                TerminalPreviewSection(
                    title = "login:",
                    textColor = Color.White,
                    initialText = "admin",
                    backgroundColor = Color(0xFF333333)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 300,
    heightDp = 300,
    name = "Interactive Terminal"
)
@Composable
fun OldSchoolTerminalInteractivePreview() {
    var commandHistory by remember {
        mutableStateOf(listOf<String>())
    }

    var currentCommand by remember {
        mutableStateOf(TextFieldValue(""))
    }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(8.dp)
        ) {
            Column {
                commandHistory.forEach { command ->
                    Row {
                        Text(
                            text = "> ",
                            color = Color.Green,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = command,
                            color = Color.Green,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "> ",
                        color = Color.Green,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 18.sp
                    )

                    OldSchoolThickCursorTextField(
                        value = currentCommand,
                        onValueChange = { value ->
                            currentCommand = value
                            if (value.text.contains("\n")) {
                                val newCommand = value.text.replace("\n", "")
                                commandHistory = commandHistory + newCommand
                                currentCommand = TextFieldValue("")
                            }
                        },
                        textStyle = TextStyle(
                            color = Color.Green,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp
                        ),
                        cursorColor = Color.Green,
                        cursorWidth = 12f,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
