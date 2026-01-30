package com.example.dgenlibrary.ui.textfield

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenGray
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * A text field with a custom blinking block cursor.
 * 
 * This component provides:
 * - Custom block cursor that blinks
 * - Scrollable content for long text
 * - Custom text selection colors
 * - Custom text selection menu
 *
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier for the component
 * @param primaryColor Primary color used for selection and toolbar
 * @param keyboardtype Keyboard type to use
 * @param placeholder Placeholder composable when empty
 * @param contentAlignment Alignment of content in the box
 * @param textStyle Text style for input
 * @param cursorColor Color of the cursor
 * @param cursorWidth Width of the cursor
 * @param cursorHeight Height of the cursor
 * @param blinkDuration Duration of cursor blink animation in ms
 * @param visualTransformation Visual transformation for the text
 * @param maxFieldHeight Maximum height before scrolling
 * @param cursorVerticalOffset Vertical offset for cursor positioning
 * @param singleLine Whether the field is single line
 * @param textfieldFocusManager Focus manager for the field
 */
@Composable
fun DgenCursorTextfield(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = dgenGray,
    keyboardtype: KeyboardType = KeyboardType.Text,
    placeholder: @Composable () -> Unit = {
        Text(
            text = "Type a message",
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = dgenWhite.copy(alpha = 0.45f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
    },
    contentAlignment: Alignment = Alignment.CenterStart,
    textStyle: TextStyle = TextStyle(
        fontFamily = PitagonsSans,
        color = dgenWhite,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    cursorColor: Color = dgenTurqoise,
    cursorWidth: Dp = 18.dp,
    cursorHeight: Dp = 32.dp,
    blinkDuration: Int = 500,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxFieldHeight: Dp = 150.dp,
    cursorVerticalOffset: Dp = 24.dp,
    singleLine: Boolean = false,
    textfieldFocusManager: FocusManager? = null,
) {
    // Blink animation, layout & focus state
    val infiniteTransition = rememberInfiniteTransition(label = "cursorBlink")
    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(blinkDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursorBlink"
    )
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    // Scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    // Auto-scroll down whenever new text bumps max scroll
    LaunchedEffect(scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    val focusManager = textfieldFocusManager ?: LocalFocusManager.current

    // Custom text toolbar for selection menu
    val textToolbarState = remember { SimpleTextToolbarState(primaryColor) }
    val customTextToolbar = remember(textToolbarState) { SimpleTextToolbar(textToolbarState) }

    Box(
        modifier = Modifier
            .heightIn(max = maxFieldHeight)
            .verticalScroll(scrollState),
        contentAlignment = contentAlignment
    ) {
        if (value.text.isBlank() && !isFocused) {
            placeholder()
        }

        // Custom selection colors with transparent handles
        val customTextSelectionColors = TextSelectionColors(
            handleColor = Color.Transparent,
            backgroundColor = primaryColor.copy(alpha = 0.4f)
        )

        CompositionLocalProvider(
            LocalTextSelectionColors provides customTextSelectionColors,
            LocalTextToolbar provides customTextToolbar
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxWidth()
                    .drawBehind {
                        if (isFocused) {
                            textLayoutResult
                                ?.takeIf { value.selection.collapsed }
                                ?.let { tlr ->
                                    val rect = tlr.getCursorRect(value.selection.start)
                                    val y = ((rect.top + rect.bottom) / 2) - (cursorHeight.toPx() / 2)
                                    drawRect(
                                        color = cursorColor.copy(alpha = if (isFocused) blinkAlpha else 0f),
                                        topLeft = Offset(rect.left, y),
                                        size = Size(cursorWidth.toPx(), cursorHeight.toPx())
                                    )
                                }
                        }
                    }
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = keyboardtype
                ),
                keyboardActions = KeyboardActions(
                    onGo = { isFocused = true },
                    onDone = {
                        focusManager.clearFocus()
                        isFocused = false
                    }
                ),
                textStyle = textStyle,
                visualTransformation = VisualTransformation.None,
                cursorBrush = SolidColor(Color.Unspecified),
                onTextLayout = { textLayoutResult = it },
                singleLine = singleLine
            )
        }
    }

    // Render custom selection menu
    SimpleTextSelectionMenuContent(textToolbarState)
}

/**
 * A search-optimized variant of DgenCursorTextfield.
 * 
 * Similar to DgenCursorTextfield but with additional onFocusChanged callback
 * for search focus tracking.
 *
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier for the component
 * @param keyboardtype Keyboard type to use
 * @param placeholder Placeholder composable when empty
 * @param contentAlignment Alignment of content in the box
 * @param textStyle Text style for input
 * @param cursorColor Color of the cursor
 * @param cursorWidth Width of the cursor
 * @param cursorHeight Height of the cursor
 * @param blinkDuration Duration of cursor blink animation in ms
 * @param maxFieldHeight Maximum height before scrolling
 * @param singleLine Whether the field is single line
 * @param textfieldFocusManager Focus manager for the field
 * @param onFocusChanged Callback when focus state changes
 */
@Composable
fun DgenCursorSearchTextfield(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    keyboardtype: KeyboardType = KeyboardType.Text,
    placeholder: @Composable () -> Unit = {
        Text(
            text = "Search...",
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = dgenTurqoise.copy(alpha = 0.45f),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        )
    },
    contentAlignment: Alignment = Alignment.CenterStart,
    textStyle: TextStyle = TextStyle(
        fontFamily = PitagonsSans,
        color = dgenWhite,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    cursorColor: Color = dgenTurqoise,
    cursorWidth: Dp = 18.dp,
    cursorHeight: Dp = 32.dp,
    blinkDuration: Int = 500,
    maxFieldHeight: Dp = 150.dp,
    singleLine: Boolean = false,
    textfieldFocusManager: FocusManager? = null,
    onFocusChanged: (Boolean) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cursorBlink")
    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(blinkDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursorBlink"
    )
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    val focusManager = textfieldFocusManager ?: LocalFocusManager.current
    
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = cursorColor.copy(alpha = 0.2f)
    )

    val textToolbarState = remember { SimpleTextToolbarState(cursorColor) }
    val customTextToolbar = remember(textToolbarState) { SimpleTextToolbar(textToolbarState) }

    Box(
        modifier = modifier
            .heightIn(max = maxFieldHeight)
            .verticalScroll(scrollState),
        contentAlignment = contentAlignment
    ) {
        if (value.text.isBlank() && !isFocused) {
            placeholder()
        }

        CompositionLocalProvider(
            LocalTextSelectionColors provides customTextSelectionColors,
            LocalTextToolbar provides customTextToolbar
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        if (isFocused) {
                            textLayoutResult
                                ?.takeIf { value.selection.collapsed }
                                ?.let { tlr ->
                                    val rect = tlr.getCursorRect(value.selection.start)
                                    val y = ((rect.top + rect.bottom) / 2) - (cursorHeight.toPx() / 2)
                                    drawRect(
                                        color = cursorColor.copy(alpha = if (isFocused) blinkAlpha else 0f),
                                        topLeft = Offset(
                                            rect.left.coerceIn(0f, size.width - cursorWidth.toPx()),
                                            y
                                        ),
                                        size = Size(cursorWidth.toPx(), cursorHeight.toPx())
                                    )
                                }
                        }
                    }
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        onFocusChanged(focusState.isFocused)
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = keyboardtype
                ),
                keyboardActions = KeyboardActions(
                    onGo = { isFocused = true },
                    onDone = {
                        focusManager.clearFocus()
                        isFocused = false
                    }
                ),
                textStyle = textStyle,
                visualTransformation = VisualTransformation.None,
                cursorBrush = SolidColor(Color.Unspecified),
                onTextLayout = { textLayoutResult = it },
                singleLine = singleLine
            )
        }
    }

    SimpleTextSelectionMenuContent(textToolbarState)
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenCursorTextfieldPreview() {
    var value by remember { mutableStateOf(TextFieldValue("Hello World")) }

    DgenTheme {
        DgenCursorTextfield(
            value = value,
            onValueChange = { value = it }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenCursorTextfieldEmptyPreview() {
    var value by remember { mutableStateOf(TextFieldValue("")) }

    DgenTheme {
        DgenCursorTextfield(
            value = value,
            onValueChange = { value = it }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenCursorSearchTextfieldPreview() {
    var value by remember { mutableStateOf(TextFieldValue("")) }

    DgenTheme {
        DgenCursorSearchTextfield(
            value = value,
            onValueChange = { value = it },
            singleLine = true
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenCursorTextfieldPreviewDDevice() {
    var value by remember { mutableStateOf(TextFieldValue("Hello World")) }
    DgenTheme {
        DgenCursorTextfield(
            value = value,
            onValueChange = { value = it }
        )
    }
}
