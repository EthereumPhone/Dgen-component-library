package com.example.dgenlibrary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalFocusManager
import com.example.dgenlibrary.SystemColorManager.primaryColor
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.body1_fontSize
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.label_fontSize
import com.example.dgenlibrary.ui.theme.pulseOpacity
import kotlinx.coroutines.delay

@Composable
fun BoxScope.SearchBar(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    focusedSearch: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    textColor: Color,
    backgroundColor: Color,
    componentBackgroundColor: Color = dgenBlack.copy(alpha = 0.9f),
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    onClear: () -> Unit,
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var internalTfv by remember {
        mutableStateOf(TextFieldValue(text = searchValue, selection = TextRange(searchValue.length)))
    }
    val isAnyFieldFocused = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Synchronize internalTfv with searchValue from parent if it changes externally.
    LaunchedEffect(searchValue) {
        if (internalTfv.text != searchValue) {
            internalTfv = TextFieldValue(text = searchValue, selection = TextRange(searchValue.length))
        }
    }

    LaunchedEffect(Unit) {
        // Small delay can help to ensure focus happens after composition.
        delay(100)
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(
                    color = componentBackgroundColor,
                    size = size
                )
            }
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .align(Alignment.TopCenter)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .drawBehind {
                    drawRoundRect(
                        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx()),
                        color = backgroundColor,
                        alpha = 0.7f
                    )
                }
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier
                    .size(32.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onNavigateBack()
                        }
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "Search",
                    tint = primaryColor,
                    modifier = modifier.size(24.dp)
                )
            }

            DgenBasicTextfield(
                value = internalTfv,
                onValueChange = { newTextFieldValueState ->
                    val oldText = internalTfv.text
                    internalTfv = newTextFieldValueState

                    if (oldText != newTextFieldValueState.text) {
                        onSearchValueChange(newTextFieldValueState.text)
                    }
                },
                minLines = 1,
                maxLines = 1,
                cursorWidth = 14.dp,
                cursorHeight = 24.dp,
                cursorColor = primaryColor,
                textStyle = TextStyle(
                    textAlign = TextAlign.Start,
                    fontFamily = SpaceMono,
                    color = dgenWhite,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = body1_fontSize,
                    lineHeight = body1_fontSize,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None,
                ),
                modifier = modifier
                    .padding(end = 14.dp)
                    .weight(1f)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                keyboardtype = KeyboardType.Text,
                isAnyFieldFocused = isAnyFieldFocused,
                textfieldFocusManager = focusManager,
                placeholder = {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = "Search".uppercase(),
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontFamily = SpaceMono,
                            color = textColor.copy(pulseOpacity),
                            fontSize = label_fontSize,
                            lineHeight = label_fontSize,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        )
                    )
                }
            )

            AnimatedVisibility(
                modifier = modifier.padding(start = 0.dp, end = 0.dp),
                visible = focusedSearch,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearEasing
                    )
                )
            ) {
                ActionButton(
                    modifier = modifier
                        .size(24.dp)
                        .drawBehind {
                            drawCircle(
                                color = textColor
                            )
                        },
                    onClick = onClear,
                    icon = {
                        Icon(
                            contentDescription = "Clear",
                            imageVector = Icons.Rounded.Clear,
                            tint = backgroundColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                )
            }
        }
    }
}
