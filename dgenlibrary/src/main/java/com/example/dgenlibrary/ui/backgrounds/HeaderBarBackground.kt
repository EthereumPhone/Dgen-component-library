package com.example.dgenlibrary.ui.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.headers.HeaderBar
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean

@Composable
fun DgenHeaderBackground(
    modifier: Modifier = Modifier,
    title: String = "",
    primaryColor: Color = dgenOcean,
    focusManager: FocusManager? = null,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(dgenBlack)
            .statusBarsPadding()
            .padding(start = 12.dp, end = 12.dp, bottom = 24.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager?.clearFocus()
                    }
                )
            },
    ) {
        HeaderBar(
            text = title,
            onClick = onBackClick,
            modifier = modifier.padding(horizontal = 12.dp),
            primaryColor = primaryColor
        )

        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun DgenHeaderBackgroundPreview() {
    DgenHeaderBackground(
        title = "Header",
        onBackClick = {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Content goes here")
            Text(text = "More content below the header")
        }
    }
}