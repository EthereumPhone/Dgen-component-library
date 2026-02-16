package com.example.dgenlibrary.ui.textfield

import android.view.View
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.DollarDgenTextfield
import com.example.dgenlibrary.ui.theme.smallDuration

/**
 * A text field with an animated dollar sign prefix that fades in when focused or has content.
 *
 * @param value Current text field value
 * @param onValueChange Callback when the value changes
 * @param modifier Modifier to be applied
 * @param textfieldFocusManager Optional focus manager
 * @param keyboardtype Keyboard type (defaults to Text)
 * @param textStyle Style for the text content
 * @param activeColor Color used for active state
 * @param cursorColor Color of the custom cursor
 * @param cursorWidth Width of the custom cursor
 * @param cursorHeight Height of the custom cursor
 * @param singleLine Whether to restrict to a single line
 * @param maxLines Maximum number of lines
 * @param onEditDone Callback when editing is complete
 * @param placeholder Composable placeholder when field is empty
 * @param onDoubleTap Callback for double-tap gesture
 * @param view Android View for focus handling
 * @param labelContent Optional label content above the field
 */
@Composable
fun DollarSignTextfield(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textfieldFocusManager: FocusManager? = null,
    keyboardtype: KeyboardType = KeyboardType.Text,
    textStyle: TextStyle,
    activeColor: Color,
    cursorColor: Color,
    cursorWidth: Dp = 16.dp,
    cursorHeight: Dp = 32.dp,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onEditDone: () -> Unit = {},
    placeholder: @Composable (() -> Unit)? = null,
    onDoubleTap: () -> Unit = {},
    view: View,
    labelContent: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    val dollarSignOpacity by animateFloatAsState(
        targetValue = if (isFocused || value.text.isNotEmpty()) 1f else 0f,
        animationSpec = tween(durationMillis = smallDuration),
        label = "dollarSignOpacity"
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DollarDgenTextfield(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            value = value,
            onValueChange = onValueChange,
            textfieldFocusManager = textfieldFocusManager,
            keyboardtype = keyboardtype,
            textStyle = textStyle,
            activeColor = activeColor,
            cursorColor = cursorColor,
            cursorWidth = cursorWidth,
            cursorHeight = cursorHeight,
            singleLine = singleLine,
            maxLines = maxLines,
            onEditDone = onEditDone,
            placeholder = placeholder,
            onDoubleTap = onDoubleTap,
            view = view,
            labelContent = labelContent,
            signOpacity = dollarSignOpacity
        )
    }
}
