package com.example.dgenlibrary

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import com.example.dgenlibrary.ui.textfield.DgenCursorSearchTextfield
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.body1_fontSize
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenDarkRed
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenOrche
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.mediumEnterDuration
import com.example.dgenlibrary.ui.theme.mediumExitDuration
import com.example.dgenlibrary.ui.theme.oceanAbyss
import com.example.dgenlibrary.ui.theme.pulseOpacity
import com.example.dgenlibrary.util.Haptics

@Composable
fun SearchHeader(
    searchValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClearValue: () -> Unit,
    focusManager: FocusManager,
    backgroundColor: Color,
    onAddContact: () -> Unit,
    isSearchFocused: MutableState<Boolean>,
    focusRequester: FocusRequester,
    primaryColor: Color,
    secondaryColor: Color,
    keyboardController: SoftwareKeyboardController?,
    onDismissSearch: () -> Unit = {}
){
    val view = LocalView.current
    val context = LocalContext.current

    // Get vibrator for haptic feedback
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    val animatedColor by animateColorAsState(
        targetValue = if (isSearchFocused.value) secondaryColor else Color.Transparent,
        animationSpec = tween(durationMillis = mediumEnterDuration),
        label = "color"
    )

    val animatedPlaceholderColor by animateColorAsState(
        targetValue = if (isSearchFocused.value) primaryColor.copy(pulseOpacity) else primaryColor,
        animationSpec = tween(durationMillis = mediumEnterDuration),
        label = "color"
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
                .padding(start = 12.dp, end = 12.dp, top = 16.dp , bottom = 8.dp)
        ){
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
                Box(
                    modifier = Modifier.size(32.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    AnimatedContent(
                        modifier = Modifier,
                        targetState = isSearchFocused.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(mediumEnterDuration)) togetherWith
                                    fadeOut(animationSpec = tween(mediumExitDuration))
                        }
                    ) { state ->
                        if (state){
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .pointerInput(true) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            onDismissSearch()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back_icon),
                                    contentDescription = "Back",
                                    tint = primaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        else {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            isSearchFocused.value = true
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.searchicon),
                                    contentDescription = "Search",
                                    tint = primaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                DgenCursorSearchTextfield(
                    value = searchValue,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = body1_fontSize,
                        lineHeight = 36.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    onFocusChanged = { focused ->
                        if (isSearchFocused.value != focused) {
                            isSearchFocused.value = focused
                        }
                    },
                    singleLine = true,
                    cursorColor = primaryColor,
                    cursorWidth = 16.dp,
                    textfieldFocusManager = focusManager,
                    placeholder = {
                        Text(
                            text = "SEARCH",
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

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(end = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = Modifier,
                        targetState = isSearchFocused.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(mediumEnterDuration)) togetherWith
                                    fadeOut(animationSpec = tween(mediumExitDuration))
                        }
                    ) { state ->
                        if (state){
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .pointerInput(searchValue.text) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            // If search field is empty, dismiss the search mode
                                            if (searchValue.text.isEmpty()) {
                                                onDismissSearch()
                                            } else {
                                                // If search field has text, just clear it and keep focus
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
                                            drawCircle(
                                                color = primaryColor,
                                            )
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
                        }
                        else {
                            Box(
                                modifier = Modifier
                                    .height(56.dp)
                                    .pointerInput(true) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            onAddContact()
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Contact",
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

@Composable
fun SearchHeader(
    searchValue: TextFieldValue,
    focusManager: FocusManager,
    backgroundColor: Color,
    isSearchFocused: MutableState<Boolean>,
    focusRequester: FocusRequester,
    primaryColor: Color,
    secondaryColor: Color,
    keyboardController: SoftwareKeyboardController?,
    onValueChange: (TextFieldValue) -> Unit,
    onClearValue: () -> Unit,
    onDismissSearch: () -> Unit = {},
    icon: @Composable () -> Unit,
){
    val view = LocalView.current
    val context = LocalContext.current

    // Get vibrator for haptic feedback
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    val animatedColor by animateColorAsState(
        targetValue = if (isSearchFocused.value) secondaryColor else Color.Transparent,
        animationSpec = tween(durationMillis = mediumEnterDuration),
        label = "color"
    )

    val animatedPlaceholderColor by animateColorAsState(
        targetValue = if (isSearchFocused.value) primaryColor.copy(pulseOpacity) else primaryColor,
        animationSpec = tween(durationMillis = mediumEnterDuration),
        label = "color"
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
                .padding(start = 12.dp, end = 12.dp, top = 16.dp , bottom = 8.dp)
        ){
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
                Box(
                    modifier = Modifier.size(32.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    AnimatedContent(
                        modifier = Modifier,
                        targetState = isSearchFocused.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(mediumEnterDuration)) togetherWith
                                    fadeOut(animationSpec = tween(mediumExitDuration))
                        }
                    ) { state ->
                        if (state){
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .pointerInput(true) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            onDismissSearch()
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.back_icon),
                                    contentDescription = "Back",
                                    tint = primaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        else {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            isSearchFocused.value = true
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.searchicon),
                                    contentDescription = "Search",
                                    tint = primaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                DgenCursorSearchTextfield(
                    value = searchValue,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontFamily = PitagonsSans,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = body1_fontSize,
                        lineHeight = 36.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    onFocusChanged = { focused ->
                        if (isSearchFocused.value != focused) {
                            isSearchFocused.value = focused
                        }
                    },
                    singleLine = true,
                    cursorColor = primaryColor,
                    cursorWidth = 16.dp,
                    textfieldFocusManager = focusManager,
                    placeholder = {
                        Text(
                            text = "SEARCH",
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

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(end = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = Modifier,
                        targetState = isSearchFocused.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(mediumEnterDuration)) togetherWith
                                    fadeOut(animationSpec = tween(mediumExitDuration))
                        }
                    ) { state ->
                        if (state){
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .pointerInput(searchValue.text) {
                                        detectTapGestures {
                                            view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
                                            // If search field is empty, dismiss the search mode
                                            if (searchValue.text.isEmpty()) {
                                                onDismissSearch()
                                            } else {
                                                // If search field has text, just clear it and keep focus
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
                                            drawCircle(
                                                color = primaryColor,
                                            )
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
                        }
                        else {
                            Box(
                                modifier = Modifier.height(56.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                icon()
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

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SearchHeaderPreview() {
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    val isSearchFocused = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchHeader(
        searchValue = searchValue.value,
        onValueChange = { searchValue.value = it },
        onClearValue = { searchValue.value = TextFieldValue("") },
        focusManager = focusManager,
        backgroundColor = dgenBlack,
        onAddContact = {},
        isSearchFocused = isSearchFocused,
        focusRequester = focusRequester,
        primaryColor = dgenWhite,
        secondaryColor = dgenBlack,
        keyboardController = keyboardController,
        onDismissSearch = { isSearchFocused.value = false }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SearchHeaderFocusedPreview() {
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    val isSearchFocused = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchHeader(
        searchValue = searchValue.value,
        onValueChange = { searchValue.value = it },
        onClearValue = { searchValue.value = TextFieldValue("") },
        focusManager = focusManager,
        backgroundColor = dgenBlack,
        onAddContact = {},
        isSearchFocused = isSearchFocused,
        focusRequester = focusRequester,
        primaryColor = dgenWhite,
        secondaryColor = oceanAbyss,
        keyboardController = keyboardController,
        onDismissSearch = { isSearchFocused.value = false }
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SearchHeaderFocusedWithIconPreview() {
    val searchValue = remember { mutableStateOf(TextFieldValue("")) }
    val isSearchFocused = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchHeader(
        searchValue = searchValue.value,
        onValueChange = { searchValue.value = it },
        onClearValue = { searchValue.value = TextFieldValue("") },
        focusManager = focusManager,
        backgroundColor = dgenBlack,
        icon = {

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Contact",
                    tint = dgenGreen,
                    modifier = Modifier.size(32.dp)
                )

        },
        isSearchFocused = isSearchFocused,
        focusRequester = focusRequester,
        primaryColor = dgenWhite,
        secondaryColor = dgenBlack,
        keyboardController = keyboardController,
        onDismissSearch = { isSearchFocused.value = false }
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun SearchHeaderWithTextPreview() {
    val searchValue = remember { mutableStateOf(TextFieldValue("John Doe")) }
    val isSearchFocused = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchHeader(
        searchValue = searchValue.value,
        onValueChange = { searchValue.value = it },
        onClearValue = { searchValue.value = TextFieldValue("") },
        focusManager = focusManager,
        backgroundColor = dgenBlack,
        onAddContact = {},
        isSearchFocused = isSearchFocused,
        focusRequester = focusRequester,
        primaryColor = dgenWhite,
        secondaryColor = oceanAbyss,
        keyboardController = keyboardController,
        onDismissSearch = { isSearchFocused.value = false }
    )
}