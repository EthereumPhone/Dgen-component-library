package com.example.dgenlibrary

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme


@SuppressLint("SuspiciousIndentation")
@Composable
fun DgenBasicTextfield(
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    maxLines: Int = 100,
    cursorColor: Color = DgenTheme.colors.dgenWhite,
    cursorWidth: Dp = 12.dp,
    cursorHeight: Dp = 32.dp,
    textStyle: TextStyle = DgenTheme.typography.body2,

    ) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val cursorAlpha by rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .drawBehind {
                    drawCustomCursor(
                        textFieldValue = value,
                        textLayoutResult = textLayoutResult,
                        cursorColor = if(isFocused) cursorColor else Color.Transparent, // Blinken
                        cursorWidth = cursorWidth.toPx(),
                        cursorHeight = cursorHeight.toPx(),
                        cursorAlpha = cursorAlpha
                    )
                }
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
            ,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Fokus entfernen, wenn Enter gedrückt wird
                }
            ),
            textStyle = textStyle,
            enabled = enabled,
            readOnly = readOnly,
            cursorBrush = SolidColor(Color.Unspecified),
            minLines = minLines,
            maxLines = maxLines,
            interactionSource = interactionSource,
            onTextLayout = { textLayoutResult = it }
        )
}


@Composable
fun DgenTextfield(
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = 100,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(0.dp),
    backgroundColor: Color = DgenTheme.colors.dgenWhite,
    cursorColor: Color = DgenTheme.colors.dgenWhite,
    cursorWidth: Dp = 12.dp,
    cursorHeight: Dp = 32.dp,
    textStyle: TextStyle = DgenTheme.typography.body2,
    labelContent: @Composable() (() -> Unit)? = null,
    ) {

    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current // Zugriff auf den FocusManager


    val backgroundColor by animateColorAsState(
        targetValue = if (isFocused) backgroundColor else Color.Transparent,
        animationSpec = tween(durationMillis = 500),
        label = "backgroundColor" // Dauer der Animation in Millisekunden
    )



    Column (
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .drawBehind {
                drawRect(
                    color = backgroundColor, // Farbe des Rechtecks
                    size = size, // Füllt den gesamten Platz aus
                    topLeft = Offset(0f, 0f), // Startposition
                    alpha = 0.2f
                )
            }
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
            .onKeyEvent { // Ermöglicht zusätzliche Tastatureingaben
                if (it.nativeKeyEvent.keyCode == Key.Enter.nativeKeyCode) {
                    focusManager.clearFocus() // Fokus entfernen bei Enter
                    true
                } else {
                    false
                }
            }
        ,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){

        if (labelContent != null) {
            labelContent()
        }

        DgenBasicTextfield(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
            ,
            textStyle = textStyle,
            enabled = enabled,
            readOnly = readOnly,
            minLines = minLines,
            maxLines = maxLines,
            interactionSource = interactionSource,
            cursorColor =  cursorColor,
            cursorWidth = cursorWidth,
            cursorHeight = cursorHeight
        )
   }

}


fun DrawScope.drawCustomCursor(
    textFieldValue: TextFieldValue,
    textLayoutResult: TextLayoutResult?,
    cursorColor: Color,
    cursorWidth: Float,
    cursorHeight: Float,
    cursorAlpha: Float
) {

    textLayoutResult?.let {
        val cursorRect = it.getCursorRect(textFieldValue.selection.start)
        drawRect(
            color = cursorColor,
            topLeft = Offset(cursorRect.left, (cursorRect.top + cursorRect.bottom) / 2 - cursorHeight / 2 - cursorHeight / 8),
            size = androidx.compose.ui.geometry.Size(cursorWidth, cursorHeight),
            alpha = cursorAlpha
        )
    }
}