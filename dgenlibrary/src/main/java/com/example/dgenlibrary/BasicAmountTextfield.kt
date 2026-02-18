package com.example.dgenlibrary

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.pulseOpacity
import com.example.dgenlibrary.ui.theme.smallDuration

/**
 * A styled amount input field with crypto/fiat toggle support, MAX button, and optional
 * header/secondary content slots.
 *
 * @param currentAmount Current crypto amount string
 * @param currentFiatAmount Current fiat amount string
 * @param formattedMaxAmount Formatted max crypto amount for display
 * @param formattedMaxFiatAmount Formatted max fiat amount for display
 * @param useMaxAmount Whether the MAX amount is currently active
 * @param title Title text displayed in the header row (used when headerContent is null)
 * @param headerContent Optional composable that replaces the default title. Receives the current
 *   toggleFiat state and a callback to toggle it, enabling custom header UIs like TextToggle.
 * @param secondaryContent Optional composable displayed next to the text field. Receives a Boolean
 *   indicating whether it should be selectable/interactive.
 * @param onAmountChange Callback when the amount changes: (amount: String, isFiat: Boolean)
 * @param onMaxClick Callback when the MAX button is clicked
 * @param readOnly Whether the text field is read-only
 * @param showMaxAmount Whether to show the MAX label with the amount
 * @param maxClickable Whether the MAX amount is clickable
 * @param secondarySelectable Whether the secondary content is selectable
 * @param maxAmount Maximum crypto amount for validation (text turns red when exceeded)
 * @param maxFiatAmount Maximum fiat amount for validation (text turns red when exceeded)
 * @param invalidColor Color used when the entered amount exceeds the maximum
 */
@Composable
fun AmountTextFieldBasic(
    currentAmount: String,
    currentFiatAmount: String,
    formattedMaxAmount: String,
    formattedMaxFiatAmount: String,
    useMaxAmount: Boolean,
    title: String = "",
    headerContent: @Composable ((toggleFiat: Boolean, onToggleFiat: () -> Unit) -> Unit)? = null,
    secondaryContent: (@Composable (Boolean) -> Unit)? = null,
    onAmountChange: (String, Boolean) -> Unit,
    onMaxClick: () -> Unit,
    readOnly: Boolean = false,
    showMaxAmount: Boolean = true,
    maxClickable: Boolean = !readOnly,
    secondarySelectable: Boolean = !readOnly,
    maxAmount: Double = Double.MAX_VALUE,
    maxFiatAmount: Double = Double.MAX_VALUE,
    invalidColor: Color = dgenRed,
) {
    val primaryColor = SystemColorManager.primaryColor
    var toggleFiat by remember { mutableStateOf(false) }

    val maxAlpha by animateFloatAsState(
        targetValue = if (useMaxAmount && maxClickable) 1f else pulseOpacity,
        animationSpec = tween(smallDuration, easing = FastOutLinearInEasing),
    )

    val amount = if (toggleFiat) {
        currentFiatAmount
    } else {
        currentAmount
    }

    val isValid = if (useMaxAmount) true
    else {
        val currentVal = amount.toDoubleOrNull()
        val maxVal = if (toggleFiat) maxFiatAmount else maxAmount
        currentVal == null || amount.isEmpty() || currentVal <= maxVal
    }

    var textFieldValue by remember { mutableStateOf(TextFieldValue(amount)) }
    LaunchedEffect(amount) {
        if (textFieldValue.text != amount) {
            textFieldValue = TextFieldValue(
                text = amount,
                selection = TextRange(amount.length)
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(
            Modifier
                .offset(y = 3.dp)
                .height(77.dp)
                .width(8.dp)
                .background(primaryColor.copy(pulseOpacity))
                .padding(end = 8.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (headerContent != null) {
                    headerContent(toggleFiat) {
                        toggleFiat = !toggleFiat
                        onAmountChange("", toggleFiat)
                    }
                } else {
                    Text(
                        text = title.uppercase(),
                        fontFamily = SpaceMono,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None,
                        color = primaryColor,
                        modifier = Modifier
                            .offset(y = 2.dp)
                            .then(
                                if (maxClickable && showMaxAmount) Modifier.clickable {
                                    val target =
                                        if (toggleFiat) formattedMaxFiatAmount else formattedMaxAmount
                                    textFieldValue = TextFieldValue(
                                        text = target,
                                        selection = TextRange(target.length)
                                    )
                                    onAmountChange(target, toggleFiat)
                                    onMaxClick()
                                } else Modifier
                            )
                    )
                }

                val displayMaxAmount = if (toggleFiat) {
                    "$$formattedMaxFiatAmount"
                } else {
                    formattedMaxAmount
                }

                Text(
                    text = if (showMaxAmount) "MAX  $displayMaxAmount" else "$displayMaxAmount",
                    fontFamily = PitagonsSans,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 18.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None,
                    color = primaryColor.copy(maxAlpha),
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .then(
                            if (maxClickable && showMaxAmount) Modifier.clickable {
                                val target =
                                    if (toggleFiat) formattedMaxFiatAmount else formattedMaxAmount
                                textFieldValue = TextFieldValue(
                                    text = target,
                                    selection = TextRange(target.length)
                                )
                                onAmountChange(target, toggleFiat)
                                onMaxClick()
                            } else Modifier
                        )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DgenBasicTextfield(
                    modifier = Modifier.weight(1f),
                    value = textFieldValue,
                    onValueChange = { new ->
                        if (readOnly) {
                            textFieldValue = new
                        } else {
                            if (new.text == textFieldValue.text) {
                                textFieldValue = new
                            } else if (new.text.matches("^\\d*\\.?\\d*$".toRegex())) {
                                textFieldValue = new
                                onAmountChange(new.text, toggleFiat)
                            }
                        }
                    },
                    maxLines = 1,
                    maxLength = 42,
                    cursorColor = primaryColor,
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = "0.0",
                            style = TextStyle(
                                fontFamily = PitagonsSans,
                                color = dgenWhite.copy(alpha = pulseOpacity),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 42.sp,
                                textAlign = TextAlign.Start,
                            )
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = PitagonsSans,
                        color = if (isValid) dgenWhite else invalidColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 42.sp,
                        textAlign = TextAlign.Start
                    ),
                    keyboardtype = KeyboardType.Number,
                    cursorWidth = 24.dp,
                    cursorHeight = 42.dp,
                    enabled = !readOnly,
                    readOnly = readOnly,
                )
                if (secondaryContent != null) {
                    Spacer(modifier = Modifier.width(16.dp))
                    secondaryContent(secondarySelectable)
                }
            }

        }
    }
}