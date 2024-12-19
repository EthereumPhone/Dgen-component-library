package com.example.dgen_component_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dgen_component_library.ui.theme.DgencomponentlibraryTheme
import com.example.dgenlibrary.PrimaryButton

import com.example.dgenlibrary.ui.theme.DgenTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    DgenTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(DgenTheme.colors.dgenBlack)
            .padding(16.dp)
    ) {
        PrimaryButton({}){
                Text(text = "Mint")
            }

        Text(
            text = "Header 0",
            style = DgenTheme.typography.header0
        )

        Text(
            text = "Header 1",
            style = DgenTheme.typography.header1
        )

        Text(
            text = "Header 2",
            style = DgenTheme.typography.header2
        )

        Text(
            text = "Header 3",
            style = DgenTheme.typography.header3
        )
        Text(
            text = "Body 2",
            style = DgenTheme.typography.body2
        )
        Text(
            text = "Body 1",
            style = DgenTheme.typography.body1
        )
        Text(
            text = "Button Example",
            style = DgenTheme.typography.button,
            modifier = Modifier.padding(top = DgenTheme.elevation.default)
        )
        Text(
            text = "Label",
            style = DgenTheme.typography.label
        )
    }
}