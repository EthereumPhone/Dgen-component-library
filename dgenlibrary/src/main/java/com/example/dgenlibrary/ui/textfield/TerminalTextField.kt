package com.example.dgenlibrary.ui.textfield

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==================== PREVIEWS ====================

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.tooling.preview.Preview
/**
 * A terminal-style text field with an old-school blinking block cursor.
 * Inspired by retro terminal interfaces with phosphor glow effects.
 *
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier to apply
 * @param enabled Whether the text field is enabled
 * @param readOnly Whether the text field is read-only
 * @param prompt The prompt prefix (e.g., "> ")
 * @param textColor Color of the text
 * @param cursorColor Color of the blinking cursor
 * @param glowIntensity Intensity of the text glow effect (0.0 - 1.0)
 * @param glowRadius Blur radius of the glow
 * @param fontSize Font size for the text
 * @param fontFamily Font family (defaults to Monospace)
 * @param cursorBlinkDuration Duration of cursor blink animation in ms
 * @param keyboardType Type of keyboard to show
 * @param imeAction IME action button type
 * @param onImeAction Callback when IME action is triggered
 * @param placeholder Placeholder text when empty
 * @param singleLine Whether to restrict to a single line
 */
@Composable
fun TerminalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    prompt: String = "> ",
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    cursorColor: Color = MaterialTheme.colorScheme.onBackground,
    glowIntensity: Float = 0.6f,
    glowRadius: Float = 16f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    cursorBlinkDuration: Int = 800,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    placeholder: String = "",
    singleLine: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    
    // Pulsing cursor animation
    val infiniteTransition = rememberInfiniteTransition(label = "terminal_cursor")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(cursorBlinkDuration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )
    
    val textStyle = TextStyle(
        color = textColor,
        fontSize = fontSize,
        fontFamily = fontFamily,
        shadow = Shadow(
            color = textColor.copy(alpha = glowIntensity),
            offset = Offset(0f, 0f),
            blurRadius = glowRadius
        )
    )
    
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusRequester.requestFocus()
                    }
                )
            }
    ) {
        // Show placeholder when empty and not focused
        if (value.isEmpty() && !isFocused && placeholder.isNotEmpty()) {
            Text(
                text = "$prompt$placeholder",
                style = textStyle.copy(
                    color = textColor.copy(alpha = 0.5f)
                )
            )
        }
        
        // Display text with prompt and animated cursor
        val displayText = buildAnnotatedString {
            withStyle(SpanStyle(color = textColor)) {
                append("$prompt$value")
            }
            if (isFocused) {
                withStyle(SpanStyle(color = cursorColor.copy(alpha = cursorAlpha))) {
                    append("█")
                }
            }
        }
        
        // Hidden actual text field for input handling
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .matchParentSize()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle.copy(color = Color.Transparent),
            cursorBrush = SolidColor(Color.Transparent),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onImeAction()
                },
                onSend = {
                    onImeAction()
                },
                onGo = {
                    onImeAction()
                }
            )
        )
        
        // Visible styled text
        Text(
            text = displayText,
            style = textStyle,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

/**
 * A terminal-style text field with TextFieldValue for more control.
 * Supports cursor positioning and selection state.
 */
@Composable
fun TerminalTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    prompt: String = "> ",
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    cursorColor: Color = MaterialTheme.colorScheme.onBackground,
    glowIntensity: Float = 0.6f,
    glowRadius: Float = 16f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    cursorBlinkDuration: Int = 800,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    placeholder: String = "",
    singleLine: Boolean = false,
    cursorWidth: Dp = 12.dp,
    cursorHeight: Dp = 20.dp
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    
    // Block cursor blinking animation
    val infiniteTransition = rememberInfiniteTransition(label = "terminal_cursor_block")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )
    
    val textStyle = TextStyle(
        color = textColor,
        fontSize = fontSize,
        fontFamily = fontFamily,
        shadow = Shadow(
            color = textColor.copy(alpha = glowIntensity),
            offset = Offset(0f, 0f),
            blurRadius = glowRadius
        )
    )
    
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusRequester.requestFocus()
                    }
                )
            },
        contentAlignment = Alignment.CenterStart
    ) {
        // Show placeholder when empty and not focused
        if (value.text.isEmpty() && !isFocused && placeholder.isNotEmpty()) {
            Text(
                text = "$prompt$placeholder",
                style = textStyle.copy(
                    color = textColor.copy(alpha = 0.5f)
                )
            )
        }
        
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .drawBehind {
                    if (isFocused) {
                        textLayoutResult?.let { layoutResult ->
                            val cursorRect = layoutResult.getCursorRect(value.selection.start)
                            drawRect(
                                color = cursorColor.copy(alpha = cursorAlpha),
                                topLeft = Offset(
                                    cursorRect.left,
                                    (cursorRect.top + cursorRect.bottom) / 2 - cursorHeight.toPx() / 2
                                ),
                                size = androidx.compose.ui.geometry.Size(
                                    cursorWidth.toPx(),
                                    cursorHeight.toPx()
                                )
                            )
                        }
                    }
                },
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            cursorBrush = SolidColor(Color.Transparent),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onImeAction()
                },
                onSend = {
                    onImeAction()
                },
                onGo = {
                    onImeAction()
                }
            ),
            onTextLayout = { textLayoutResult = it },
            decorationBox = { innerTextField ->
                Box {
                    Text(
                        text = prompt,
                        style = textStyle
                    )
                    Box(modifier = Modifier.padding(start = (prompt.length * fontSize.value * 0.6f).dp)) {
                        innerTextField()
                    }
                }
            }
        )
    }
}

/**
 * A display-only command line component showing text with an animated cursor.
 * Use this for displaying the current command line state without input capabilities.
 *
 * @param text The text to display
 * @param modifier Modifier to apply
 * @param prompt The prompt prefix (e.g., "> ")
 * @param textColor Color of the text
 * @param glowIntensity Intensity of the text glow effect
 * @param glowRadius Blur radius of the glow
 * @param fontSize Font size for the text
 * @param fontFamily Font family (defaults to Monospace)
 * @param cursorBlinkDuration Duration of cursor blink animation in ms
 * @param showCursor Whether to show the blinking cursor
 * @param onLongPress Callback for long press events
 */
@Composable
fun CommandLineDisplay(
    text: String,
    modifier: Modifier = Modifier,
    prompt: String = "> ",
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    glowIntensity: Float = 0.6f,
    glowRadius: Float = 16f,
    fontSize: TextUnit = 17.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    cursorBlinkDuration: Int = 800,
    showCursor: Boolean = true,
    onLongPress: (Offset) -> Unit = {}
) {
    // Pulsing cursor animation
    val infiniteTransition = rememberInfiniteTransition(label = "command_cursor")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(cursorBlinkDuration, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursor_alpha"
    )
    
    // Build the text with animated cursor
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = textColor)) {
            append("$prompt$text")
        }
        if (showCursor) {
            withStyle(SpanStyle(color = textColor.copy(alpha = cursorAlpha))) {
                append("█")
            }
        }
    }
    
    Text(
        text = annotatedText,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = fontFamily,
            fontSize = fontSize,
            shadow = Shadow(
                color = textColor.copy(alpha = glowIntensity),
                offset = Offset(0f, 0f),
                blurRadius = glowRadius
            )
        ),
        modifier = modifier
            .padding(vertical = 2.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = onLongPress
                )
            }
    )
}

/**
 * Object containing default configurations for terminal text fields
 */
object TerminalTextFieldDefaults {
    val fontSize = 17.sp
    val glowIntensity = 0.6f
    val glowRadius = 16f
    val cursorBlinkDuration = 800
    val prompt = "> "
}

@Preview(
    name = "Terminal TextField - Empty",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 100
)
@Composable
private fun TerminalTextFieldPreviewEmpty() {
    var text by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        TerminalTextField(
            value = text,
            onValueChange = { text = it },
            textColor = Color(0xFF00FF00),
            cursorColor = Color(0xFF00FF00),
            placeholder = "Type here...",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    name = "Terminal TextField - With Text",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 100
)
@Composable
private fun TerminalTextFieldPreviewWithText() {
    var text by remember { mutableStateOf("Hello, Terminal!") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        TerminalTextField(
            value = text,
            onValueChange = { text = it },
            textColor = Color(0xFF00FF00),
            cursorColor = Color(0xFF00FF00),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    name = "Terminal TextField - Amber Theme",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 100
)
@Composable
private fun TerminalTextFieldPreviewAmber() {
    var text by remember { mutableStateOf("system.connect()") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        TerminalTextField(
            value = text,
            onValueChange = { text = it },
            textColor = Color(0xFFFFB000),
            cursorColor = Color(0xFFFFB000),
            glowIntensity = 0.8f,
            glowRadius = 20f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    name = "Command Line Display",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 80
)
@Composable
private fun CommandLineDisplayPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        CommandLineDisplay(
            text = "ls -la /home/user",
            textColor = Color(0xFF00FF00),
            showCursor = true
        )
    }
}

@Preview(
    name = "Command Line Display - No Cursor",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 320,
    heightDp = 80
)
@Composable
private fun CommandLineDisplayPreviewNoCursor() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        CommandLineDisplay(
            text = "Command executed successfully",
            textColor = Color(0xFF00FFFF),
            showCursor = false,
            prompt = "$ "
        )
    }
}

@Preview(
    name = "Terminal Components - Combined",
    showBackground = true,
    backgroundColor = 0xFF000000,
    widthDp = 360,
    heightDp = 300
)
@Composable
private fun TerminalComponentsCombinedPreview() {
    var inputText by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Previous commands (no cursor)
            CommandLineDisplay(
                text = "whoami",
                textColor = Color(0xFF00FF00),
                showCursor = false
            )
            
            Text(
                text = "root",
                color = Color(0xFF00FF00),
                fontFamily = FontFamily.Monospace,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            
            CommandLineDisplay(
                text = "pwd",
                textColor = Color(0xFF00FF00),
                showCursor = false
            )
            
            Text(
                text = "/root",
                color = Color(0xFF00FF00),
                fontFamily = FontFamily.Monospace,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Current input with cursor
            TerminalTextField(
                value = inputText,
                onValueChange = { inputText = it },
                textColor = Color(0xFF00FF00),
                cursorColor = Color(0xFF00FF00),
                placeholder = "Enter command...",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun TerminalTextFieldPreviewDDevice() {
    var text by remember { mutableStateOf("Hello, Terminal!") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        TerminalTextField(
            value = text,
            onValueChange = { text = it },
            textColor = Color(0xFF00FF00),
            cursorColor = Color(0xFF00FF00),
            modifier = Modifier.fillMaxWidth()
        )
    }
}