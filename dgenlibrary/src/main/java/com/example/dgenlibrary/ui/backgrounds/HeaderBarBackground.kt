package com.example.dgenlibrary.ui.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenOcean

@Composable
fun DgenHeaderBackground(
    modifier: Modifier = Modifier,
    title: String = "",
    headerContent: @Composable (() -> Unit)? = null,
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
            .padding(start = 12.dp, end = 12.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager?.clearFocus()
                    }
                )
            },
    ) {
        if(headerContent != null){
            HeaderBar(
                content = headerContent,
                onClick = onBackClick,
                modifier = Modifier.padding(horizontal = 12.dp),
                primaryColor = primaryColor
            )
        } else {
            HeaderBar(
                text = title,
                onClick = onBackClick,
                modifier = Modifier.padding(horizontal = 12.dp),
                primaryColor = primaryColor
            )
        }

        Box(modifier = modifier.weight(1f).fillMaxWidth()) {
            content()
        }
    }
}

@Composable
fun DgenHeaderGlobeBackground(
    modifier: Modifier = Modifier,
    title: String = "",
    headerContent: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    primaryColor: Color = dgenOcean,
    focusManager: FocusManager? = null,
    onBackClick: () -> Unit,
){
    LargeGlobeBackground(
        primaryColor = primaryColor,
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(start = 12.dp, end = 12.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager?.clearFocus()
                        }
                    )
                },
        ) {
            if(headerContent != null){
                HeaderBar(
                    content = headerContent,
                    onClick = onBackClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    primaryColor = primaryColor
                )
            } else {
                HeaderBar(
                    text = title,
                    onClick = onBackClick,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    primaryColor = primaryColor
                )
            }


            Box(modifier = modifier.weight(1f).fillMaxWidth()) {
                content()
            }
        }
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")

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

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenHeaderGlobeBackgroundPreview() {
    DgenHeaderGlobeBackground(
        title = "Header",
        primaryColor = dgenGreen,
        onBackClick = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Content goes here", color = Color.White)
                Text(text = "More content below the header", color = Color.White)
            }
        }
    )
}