package com.example.dgenlibrary.ui.headers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

// Animation durations
private const val MEDIUM_ENTER_DURATION = 300
private const val MEDIUM_EXIT_DURATION = 200
private const val PULSE_OPACITY = 0.45f

/**
 * A search header component with animated search field and action button.
 * 
 * When focused, displays a back button and clear button.
 * When unfocused, displays a search icon and action button (e.g., Add).
 *
 * @param searchValue The current search text value
 * @param onValueChange Callback when search text changes
 * @param onClearValue Callback to clear the search text
 * @param focusManager Focus manager for controlling focus
 * @param backgroundColor Background color for the header
 * @param onAction Callback for the action button (e.g., Add contact)
 * @param isSearchFocused Mutable state tracking if search is focused
 * @param focusRequester Focus requester for the search field
 * @param primaryColor Primary theme color
 * @param secondaryColor Secondary theme color
 * @param keyboardController Keyboard controller for showing/hiding keyboard
 * @param onDismissSearch Callback when search is dismissed
 * @param searchPlaceholder Placeholder text for search field
 * @param actionIcon Icon for the action button
 * @param actionContentDescription Content description for action button
 * @param backIcon Icon for the back button (when searching)
 * @param searchIcon Icon for the search button
 * @param onHapticFeedback Callback for haptic feedback
 * @param textfield Custom composable for the search text field
 */
@Composable
fun SearchHeader(
    searchValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClearValue: () -> Unit,
    focusManager: FocusManager,
    backgroundColor: Color = dgenBlack,
    onAction: () -> Unit,
    isSearchFocused: MutableState<Boolean>,
    focusRequester: FocusRequester,
    primaryColor: Color = dgenTurqoise,
    secondaryColor: Color = dgenOcean,
    keyboardController: SoftwareKeyboardController? = null,
    onDismissSearch: () -> Unit = {},
    searchPlaceholder: String = "SEARCH",
    actionIcon: ImageVector = Icons.Default.Add,
    actionContentDescription: String = "Action",
    backIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Rounded.Clear,
            contentDescription = "Back",
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
    },
    searchIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Rounded.Clear, // Replace with search icon
            contentDescription = "Search",
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
    },
    onHapticFeedback: () -> Unit = {},
    textfield: @Composable (
        value: TextFieldValue,
        onValueChange: (TextFieldValue) -> Unit,
        modifier: Modifier,
        placeholder: @Composable () -> Unit
    ) -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSearchFocused.value) secondaryColor else Color.Transparent,
        animationSpec = tween(durationMillis = MEDIUM_ENTER_DURATION),
        label = "color"
    )

    val animatedPlaceholderColor by animateColorAsState(
        targetValue = if (isSearchFocused.value) primaryColor.copy(PULSE_OPACITY) else primaryColor,
        animationSpec = tween(durationMillis = MEDIUM_ENTER_DURATION),
        label = "placeholderColor"
    )

    LaunchedEffect(isSearchFocused.value) {
        if (isSearchFocused.value) {
            focusRequester.requestFocus()
        } else {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 8.dp)
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
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Left icon (Back or Search)
                Box(
                    modifier = Modifier.size(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = Modifier,
                        targetState = isSearchFocused.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(MEDIUM_ENTER_DURATION)) togetherWith
                                    fadeOut(animationSpec = tween(MEDIUM_EXIT_DURATION))
                        },
                        label = "leftIcon"
                    ) { state ->
                        if (state) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .pointerInput(true) {
                                        detectTapGestures {
                                            onHapticFeedback()
                                            onDismissSearch()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                backIcon()
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            onHapticFeedback()
                                            isSearchFocused.value = true
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                searchIcon()
                            }
                        }
                    }
                }

                // Search text field
                textfield(
                    searchValue,
                    onValueChange,
                    Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    {
                        Text(
                            text = searchPlaceholder,
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = animatedPlaceholderColor,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                letterSpacing = 0.sp,
                                textDecoration = TextDecoration.None
                            )
                        )
                    }
                )

                // Right icon (Clear or Action)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = Modifier,
                        targetState = isSearchFocused.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(MEDIUM_ENTER_DURATION)) togetherWith
                                    fadeOut(animationSpec = tween(MEDIUM_EXIT_DURATION))
                        },
                        label = "rightIcon"
                    ) { state ->
                        if (state) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .pointerInput(searchValue.text) {
                                        detectTapGestures {
                                            onHapticFeedback()
                                            if (searchValue.text.isEmpty()) {
                                                onDismissSearch()
                                            } else {
                                                onClearValue()
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .drawBehind {
                                            drawCircle(color = primaryColor)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        contentDescription = "Clear",
                                        imageVector = Icons.Rounded.Clear,
                                        tint = secondaryColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .pointerInput(true) {
                                        detectTapGestures {
                                            onHapticFeedback()
                                            onAction()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = actionIcon,
                                    contentDescription = actionContentDescription,
                                    tint = primaryColor,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Brush.verticalGradient(listOf(dgenBlack, Color.Transparent)))
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun SearchHeaderPreview() {
    val isSearchFocused = remember { mutableStateOf(false) }
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    
    SearchHeader(
        searchValue = searchValue.value,
        onValueChange = { searchValue.value = it },
        onClearValue = { searchValue.value = TextFieldValue("") },
        focusManager = androidx.compose.ui.platform.LocalFocusManager.current,
        onAction = {},
        isSearchFocused = isSearchFocused,
        focusRequester = remember { FocusRequester() },
        textfield = { value, onChange, modifier, placeholder ->
            Box(modifier = modifier) {
                if (value.text.isEmpty()) {
                    placeholder()
                }
                Text(
                    text = value.text,
                    color = dgenWhite
                )
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun SearchHeaderFocusedPreview() {
    val isSearchFocused = remember { mutableStateOf(true) }
    val searchValue = remember { mutableStateOf(TextFieldValue("John")) }
    
    SearchHeader(
        searchValue = searchValue.value,
        onValueChange = { searchValue.value = it },
        onClearValue = { searchValue.value = TextFieldValue("") },
        focusManager = androidx.compose.ui.platform.LocalFocusManager.current,
        onAction = {},
        isSearchFocused = isSearchFocused,
        focusRequester = remember { FocusRequester() },
        textfield = { value, onChange, modifier, placeholder ->
            Box(modifier = modifier) {
                Text(
                    text = value.text,
                    color = dgenWhite
                )
            }
        }
    )
}

