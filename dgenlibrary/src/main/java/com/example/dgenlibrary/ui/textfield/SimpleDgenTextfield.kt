package com.example.dgenlibrary.ui.textfield

import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
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
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.label_fontSize
import com.example.dgenlibrary.ui.theme.pulseOpacity


/**
 * A styled text field with label support, custom cursor, and optional secondary action.
 *
 * This component provides a text field with:
 * - Animated background on focus
 * - Custom block cursor
 * - Label content slot
 * - Optional secondary action button (e.g., for QR scanning)
 * - Custom text selection menu
 *
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param keyboardtype Keyboard type to use
 * @param autoCorrectEnabled Whether autocorrect is enabled
 * @param textfieldFocusManager Focus manager for the field
 * @param onEditDone Callback when editing is complete (focus lost)
 * @param onDoubleTap Callback for double tap
 * @param modifier Modifier for the component
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param singleLine Whether the field is single line
 * @param minLines Minimum number of lines
 * @param maxLines Maximum number of lines
 * @param maxLength Maximum character length
 * @param interactionSource Interaction source for the field
 * @param shape Shape of the background
 * @param backgroundColor Background color
 * @param cursorColor Color of the cursor
 * @param cursorWidth Width of the cursor
 * @param cursorHeight Height of the cursor
 * @param activeColor Color when focused
 * @param textStyle Text style for input
 * @param placeholder Placeholder composable
 * @param labelContent Label composable (with optional secondary action)
 * @param secondaryAction Optional secondary action button composable
 */
@Composable
fun SimpleDgenTextfield(
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    keyboardtype: KeyboardType = KeyboardType.Text,
    autoCorrectEnabled: Boolean = true,
    textfieldFocusManager: FocusManager? = null,
    onEditDone: () -> Unit = {},
    onDoubleTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    maxLength: Int = 100,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = dgenOcean,
    cursorColor: Color = dgenWhite,
    cursorWidth: Dp = 18.dp,
    cursorHeight: Dp = 32.dp,
    activeColor: Color = dgenOcean,
    textStyle: TextStyle = TextStyle(
        fontFamily = PitagonsSans,
        color = dgenWhite,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    placeholder: @Composable (() -> Unit)? = null,
    labelContent: @Composable (() -> Unit)? = null,
    secondaryAction: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = textfieldFocusManager ?: LocalFocusManager.current

    // Custom text toolbar for selection menu
    val textToolbarState = remember { SimpleTextToolbarState(activeColor) }
    val customTextToolbar = remember(textToolbarState) { SimpleTextToolbar(textToolbarState) }

    val animatedBackgroundOpacity by animateFloatAsState(
        targetValue = if (isFocused) pulseOpacity else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "backgroundColor"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .drawBehind {
                drawRect(
                    color = activeColor,
                    size = size,
                    topLeft = Offset(0f, 0f),
                    alpha = animatedBackgroundOpacity
                )
            }
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
            .onKeyEvent {
                if (it.nativeKeyEvent.keyCode == Key.Enter.nativeKeyCode) {
                    focusManager.clearFocus()
                    true
                } else {
                    false
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Label row with optional secondary action
            if (labelContent != null || secondaryAction != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (labelContent != null) {
                        labelContent()
                    }
                    if (secondaryAction != null) {
                        secondaryAction()
                    }
                }
            }

            // Custom selection colors - invisible handles but visible selection
            val customTextSelectionColors = TextSelectionColors(
                handleColor = Color.Transparent,
                backgroundColor = cursorColor.copy(alpha = 0.2f)
            )

            // Cursor blinking animation
            val infiniteTransition = rememberInfiniteTransition(label = "cursorBlink")
            val cursorAlpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "cursorBlink"
            )

            var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
            var multiLineOverflow by remember { mutableStateOf(false) }

            CompositionLocalProvider(
                LocalTextSelectionColors provides customTextSelectionColors,
                LocalTextToolbar provides customTextToolbar
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.text.isEmpty() && placeholder != null && !isFocused) {
                        placeholder()
                    }

                    BasicTextField(
                        value = value,
                        onValueChange = { newValue ->
                            if ("\n" in newValue.text) {
                                focusManager.clearFocus()
                                onEditDone()
                            } else if (newValue.text.length <= maxLength) {
                                onValueChange(newValue)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = if (multiLineOverflow) 16.dp else 0.dp)
                            .drawBehind {
                                if (isFocused && value.selection.collapsed) {
                                    textLayoutResult?.let { tlr ->
                                        val rect = tlr.getCursorRect(value.selection.start)
                                        val y = ((rect.top + rect.bottom) / 2) - (cursorHeight.toPx() / 2)
                                        drawRect(
                                            color = cursorColor.copy(alpha = cursorAlpha),
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
                                if (isFocused && !focusState.isFocused) {
                                    onEditDone()
                                }
                                isFocused = focusState.isFocused
                            },
                        textStyle = textStyle,
                        enabled = enabled,
                        readOnly = readOnly,
                        singleLine = singleLine,
                        minLines = minLines,
                        maxLines = if (singleLine) 1 else maxLines,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = if (singleLine) ImeAction.Done else ImeAction.Default,
                            keyboardType = keyboardtype,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                isFocused = false
                            }
                        ),
                        visualTransformation = VisualTransformation.None,
                        cursorBrush = SolidColor(Color.Transparent), // Hide default cursor
                        interactionSource = interactionSource,
                        onTextLayout = {
                            textLayoutResult = it
                            multiLineOverflow = it.lineCount > 1
                        }
                    )
                }
            }
        }
    }

    // Render custom selection menu
    SimpleTextSelectionMenuContent(textToolbarState)
}

/**
 * State holder for custom text toolbar
 */
@Stable
class SimpleTextToolbarState(
    private val primaryColor: Color = dgenTurqoise
) {
    var isShowing by mutableStateOf(false)
        private set
    var menuRect by mutableStateOf(Rect.Zero)
        private set
    var onCopy: (() -> Unit)? by mutableStateOf(null)
        private set
    var onPaste: (() -> Unit)? by mutableStateOf(null)
        private set
    var onCut: (() -> Unit)? by mutableStateOf(null)
        private set
    var onSelectAll: (() -> Unit)? by mutableStateOf(null)
        private set
    var color: Color = primaryColor
        private set

    fun show(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        menuRect = rect
        onCopy = onCopyRequested
        onPaste = onPasteRequested
        onCut = onCutRequested
        onSelectAll = onSelectAllRequested
        isShowing = true
    }

    fun hide() {
        isShowing = false
    }
}

/**
 * Custom Text Toolbar implementation
 */
class SimpleTextToolbar(
    private val state: SimpleTextToolbarState
) : TextToolbar {

    override val status: TextToolbarStatus
        get() = if (state.isShowing) TextToolbarStatus.Shown else TextToolbarStatus.Hidden

    override fun hide() {
        state.hide()
    }

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        if (!state.isShowing) {
            state.show(rect, onCopyRequested, onPasteRequested, onCutRequested, onSelectAllRequested)
        }
    }
}

@Composable
fun SimpleTextSelectionMenuContent(state: SimpleTextToolbarState) {
    if (state.isShowing) {
        DisposableEffect(state.isShowing) {
            onDispose { }
        }

        SimpleSelectionMenu(
            rect = state.menuRect,
            onCopy = state.onCopy,
            onPaste = state.onPaste,
            onCut = state.onCut,
            onSelectAll = state.onSelectAll,
            onDismiss = { state.hide() },
            primaryColor = state.color
        )
    }
}

@Composable
fun SimpleSelectionMenu(
    rect: Rect,
    onCopy: (() -> Unit)?,
    onPaste: (() -> Unit)?,
    onCut: (() -> Unit)?,
    onSelectAll: (() -> Unit)?,
    onDismiss: () -> Unit,
    primaryColor: Color = dgenTurqoise,
    secondaryColor: Color = dgenBlack
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val density = LocalDensity.current

    val buttonCount = listOfNotNull(onCut, onCopy, onPaste, onSelectAll).size
    val menuWidth = (buttonCount * 50 + 20).dp
    val menuHeight = 44.dp
    val menuWidthPx = with(density) { menuWidth.toPx() }
    val menuHeightPx = with(density) { menuHeight.toPx() }
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val screenHeightPx = with(density) { screenHeight.toPx() }

    val preferredSpacing = with(density) { 80.dp.toPx() }
    val minimalSpacing = with(density) { 60.dp.toPx() }
    val edgePadding = with(density) { 20.dp.toPx() }

    var xOffset = if (rect.width > 0) {
        (rect.left + rect.width / 2 - menuWidthPx / 2).toInt()
    } else {
        (rect.left - menuWidthPx / 2).toInt()
    }

    if (xOffset < edgePadding) {
        xOffset = edgePadding.toInt()
    } else if (xOffset + menuWidthPx > screenWidthPx - edgePadding) {
        xOffset = (screenWidthPx - menuWidthPx - edgePadding).toInt()
    }

    val spaceAbove = rect.top
    val spaceBelow = screenHeightPx - rect.bottom
    val textHeight = rect.height

    val yOffset: Int = when {
        spaceAbove >= menuHeightPx + preferredSpacing -> {
            (rect.top - menuHeightPx - preferredSpacing).toInt()
        }
        spaceBelow >= menuHeightPx + preferredSpacing && textHeight < with(density) { 100.dp.toPx() } -> {
            (rect.bottom + preferredSpacing).toInt()
        }
        spaceAbove > menuHeightPx + minimalSpacing -> {
            (rect.top - menuHeightPx - minimalSpacing).toInt()
        }
        spaceBelow > menuHeightPx + minimalSpacing -> {
            (rect.bottom + minimalSpacing).toInt()
        }
        else -> {
            val rectCenterY = rect.top + rect.height / 2
            if (rectCenterY < screenHeightPx / 2) {
                (screenHeightPx - menuHeightPx - edgePadding).toInt()
            } else {
                edgePadding.toInt()
            }
        }
    }

    Popup(
        alignment = Alignment.TopStart,
        offset = androidx.compose.ui.unit.IntOffset(xOffset, yOffset),
        onDismissRequest = onDismiss,
        properties = PopupProperties(
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .background(secondaryColor, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = secondaryColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                onCut?.let {
                    SimpleTextSelectionMenuButton(
                        text = "CUT",
                        onClick = { it(); onDismiss() },
                        primaryColor = primaryColor
                    )
                }
                onCopy?.let {
                    SimpleTextSelectionMenuButton(
                        text = "COPY",
                        onClick = { it(); onDismiss() },
                        primaryColor = primaryColor
                    )
                }
                onPaste?.let {
                    SimpleTextSelectionMenuButton(
                        text = "PASTE",
                        onClick = { it(); onDismiss() },
                        primaryColor = primaryColor
                    )
                }
                onSelectAll?.let {
                    SimpleTextSelectionMenuButton(
                        text = "ALL",
                        onClick = { it(); onDismiss() },
                        primaryColor = primaryColor
                    )
                }
            }
        }
    }
}

@Composable
private fun SimpleTextSelectionMenuButton(
    text: String,
    onClick: () -> Unit,
    primaryColor: Color
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        colors = ButtonDefaults.textButtonColors(contentColor = primaryColor)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = SpaceMono,
                fontSize = label_fontSize,
                fontWeight = FontWeight.SemiBold
            ),
            color = primaryColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun SimpleDgenTextfieldPreview() {
    var value by remember { mutableStateOf(TextFieldValue("Hello World")) }

    DgenTheme {
        SimpleDgenTextfield(
            value = value,
            onValueChange = { value = it },
            labelContent = {
                Text(
                    text = "LABEL",
                    color = dgenTurqoise,
                    style = TextStyle(fontFamily = SpaceMono, fontWeight = FontWeight.Normal)
                )
            },
            placeholder = {
                Text(
                    text = "Enter text...",
                    color = dgenWhite.copy(alpha = 0.45f)
                )
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun SimpleDgenTextfieldWithSecondaryActionPreview() {
    var value by remember { mutableStateOf(TextFieldValue("")) }

    DgenTheme {
        SimpleDgenTextfield(
            value = value,
            onValueChange = { value = it },
            labelContent = {
                Text(
                    text = "ETH ADDRESS",
                    color = dgenTurqoise,
                    style = TextStyle(fontFamily = SpaceMono, fontWeight = FontWeight.Normal)
                )
            },
            secondaryAction = {
                Text(
                    text = "SCAN",
                    color = dgenTurqoise,
                    style = TextStyle(fontFamily = SpaceMono, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(end = 8.dp)
                )
            },
            placeholder = {
                Text(
                    text = "Enter ENS or ETH address",
                    color = dgenWhite.copy(alpha = 0.45f)
                )
            }
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun SimpleDgenTextfieldPreviewDDevice() {
    var value by remember { mutableStateOf(TextFieldValue("Hello World")) }
    DgenTheme {
        SimpleDgenTextfield(
            value = value,
            onValueChange = { value = it },
            labelContent = {
                Text(
                    text = "LABEL",
                    color = dgenTurqoise,
                    style = TextStyle(fontFamily = SpaceMono, fontWeight = FontWeight.Normal)
                )
            },
            placeholder = {
                Text(
                    text = "Enter text...",
                    color = dgenWhite.copy(alpha = 0.45f)
                )
            }
        )
    }
}
