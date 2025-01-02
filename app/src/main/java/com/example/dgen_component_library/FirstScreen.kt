package com.example.dgen_component_library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun FirstScreen(
    modifier: Modifier
){//navigation: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight().background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("First screen")
        Button(onClick = {
            //navigation.navigate("SecondScreen")
        }) {
            Text("Go to second screen")
        }
    }
}