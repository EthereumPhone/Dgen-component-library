package com.example.dgenlibrary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.mediumEnterDuration
import com.example.dgenlibrary.ui.theme.mediumExitDuration

/**
 * A reusable search row with animated background, search icon and an animated clear button.
 *
 * @param searchValue Current text field value.
 * @param onValueChange Called when the text changes.
 * @param onClearValue Called when the clear button is tapped.
 * @param modifier Outer modifier applied to the row container.
 * @param primaryColor Accent colour for the search icon, cursor, clear button circle and placeholder.
 * @param secondaryColor Background highlight colour when focused, and clear-icon tint.
 * @param placeholder Composable shown inside the text field when empty and unfocused.
 * @param textStyle Style applied to the typed text.
 * @param onFocusChanged Optional callback when focus state changes.
 */
@Composable
fun DgenSearchRow(
    searchValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClearValue: () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    placeholder: @Composable () -> Unit = {
        Text(
            text = "SEARCH",
            style = TextStyle(
                fontFamily = SpaceMono,
                color = primaryColor.copy(alpha = 0.45f),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
    textStyle: TextStyle = TextStyle(
        fontFamily = PitagonsSans,
        color = dgenWhite,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        textDecoration = TextDecoration.None
    ),
    onFocusChanged: (Boolean) -> Unit = {}
) {
    var isSearchFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val animatedColor by animateColorAsState(
        targetValue = if (isSearchFocused) secondaryColor else Color.Transparent,
        animationSpec = tween(durationMillis = mediumEnterDuration),
        label = "searchRowBgColor"
    )

    LaunchedEffect(isSearchFocused) {
        if (isSearchFocused) {
            focusRequester.requestFocus()
        } else {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
        onFocusChanged(isSearchFocused)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        color = animatedColor,
                    )
                }
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.searchicon),
                    contentDescription = "Search",
                    tint = primaryColor,
                    modifier = Modifier
                        .size(24.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                isSearchFocused = true
                            }
                        }
                )
            }

            DgenCursorSearchTextfield(
                value = searchValue,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true,
                maxFieldHeight = 50.dp,
                cursorColor = primaryColor,
                cursorWidth = 16.dp,
                cursorHeight = 48.dp,
                textfieldFocusManager = focusManager,
                onFocusChanged = { focused ->
                    isSearchFocused = focused
                },
                placeholder = placeholder,
                textStyle = textStyle,
            )

            AnimatedVisibility(
                visible = isSearchFocused,
                enter = fadeIn(animationSpec = tween(mediumEnterDuration)),
                exit = fadeOut(animationSpec = tween(mediumExitDuration))
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ActionButton(
                        modifier = Modifier
                            .size(24.dp)
                            .drawBehind {
                                drawCircle(
                                    color = primaryColor,
                                )
                            },
                        onClick = onClearValue,
                        icon = {
                            Icon(
                                contentDescription = "Clear",
                                imageVector = Icons.Rounded.Clear,
                                tint = secondaryColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun DgenCursorSearchTextfield(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    keyboardtype: KeyboardType =  KeyboardType.Text,
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
    contentAlignment: Alignment = Alignment.CenterStart,
    textStyle: TextStyle = LocalTextStyle.current,
    cursorColor: Color = MaterialTheme.colors.primary,
    cursorWidth: Dp = 18.dp,
    cursorHeight: Dp = 32.dp,
    blinkDuration: Int = 500,
    maxFieldHeight: Dp = 150.dp,
    singleLine: Boolean = false,
    textfieldFocusManager: FocusManager? = null,
    onFocusChanged: (Boolean) -> Unit = {}
) {

    // blink animation, layout & focus state
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

    // scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    // auto‐scroll down whenever new text bumps max scroll
    LaunchedEffect(scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }


    val focusManager = textfieldFocusManager ?: LocalFocusManager.current
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = cursorColor.copy(alpha = 0.2f)
    )

    // Custom text toolbar for selection menu
    val textToolbarState = remember { CustomTextToolbarState(cursorColor) }
    val customTextToolbar = remember(textToolbarState) { CustomTextToolbar(textToolbarState) }


    Box(
        modifier = modifier.heightIn(max = maxFieldHeight)      // fix the height so overflow can happen
            .verticalScroll(scrollState),
        contentAlignment = contentAlignment
    ) {

        if (value.text.isBlank()) {
            if (placeholder != null && !isFocused) {

                placeholder()

            }
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
                        if (isFocused){
                            textLayoutResult
                                ?.takeIf { value.selection.collapsed }
                                ?.let { tlr ->
                                    val rect = tlr.getCursorRect(value.selection.start)

                                    // subtract scroll to bring into view
                                    //val y = rect.top + (rect.height - 32.dp.toPx()) / 2f + 48.dp.toPx()
                                    val y = ((rect.top + rect.bottom) / 2 ) - (cursorHeight.toPx() /2)
                                    drawRect(
                                        color   = cursorColor.copy(alpha = if (isFocused) blinkAlpha else 0f),
                                        topLeft = Offset(rect.left.coerceIn(0f, size.width - cursorWidth.toPx()), y),
                                        size    = Size(cursorWidth.toPx(), cursorHeight.toPx())
                                    )
                                }
                            /*textLayoutResult?.let {
                                val cursorRect = it.getCursorRect(value.selection.start)
                                drawRect(
                                    color = cursorColor,
                                    topLeft = Offset(cursorRect.left, ((cursorRect.top + cursorRect.bottom) / 2 ) - (cursorHeight.toPx() /2)),
                                    size = androidx.compose.ui.geometry.Size(cursorWidth.toPx(), cursorHeight.toPx()),
                                    alpha = blinkAlpha
                                )
                            }*/
                        }

                    }
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        onFocusChanged(focusState.isFocused)
                    }
                ,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = keyboardtype
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        isFocused = true
                    },
                    onDone = {
                        focusManager.clearFocus() // Fokus entfernen, wenn Enter gedrückt wird
                        isFocused = false
                    }
                ),
                textStyle = textStyle,
                visualTransformation = VisualTransformation.None, // Ensure no transformations
                cursorBrush = SolidColor(Color.Unspecified),
                onTextLayout = { textLayoutResult = it },
                singleLine = singleLine
            )
        }
    }

    // Render custom selection menu
    CustomTextSelectionMenuContent(textToolbarState)
}


@Preview(device = "spec:width=720px,height=120px,dpi=240", name = "DgenSearchRow")
@Composable
private fun DgenSearchRowPreview() {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(dgenBlack)
            .padding(12.dp)
    ) {
        DgenSearchRow(
            searchValue = textState,
            onValueChange = { textState = it },
            onClearValue = { textState = TextFieldValue() },
            primaryColor = dgenTurqoise,
            secondaryColor = dgenOcean,
        )
    }
}


