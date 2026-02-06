package com.example.dgenlibrary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.ui.tooling.preview.Preview
import com.example.dgenlibrary.ui.theme.dgenWhite

@Composable
fun ActionBarBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    boxContent: @Composable () -> Unit,
    actionContent: @Composable () -> Unit,
    actionButtonSpace: Dp = 56.dp
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Row(modifier = modifier.fillMaxSize()){
            Box(modifier = modifier.weight(1f).fillMaxHeight()) {
                boxContent()
                Box(
                    modifier = Modifier.padding(16.dp)
                        .width(IntrinsicSize.Min) // Größe des Buttons
                        .height(IntrinsicSize.Min)
                        .align(Alignment.CenterEnd) // Position: Unten rechts

                ) {
                    Column(
                        modifier = modifier.zIndex(1f), // Stellt sicher, dass es über anderen Elementen liegt,
                        verticalArrangement = Arrangement.spacedBy(actionButtonSpace),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        actionContent()
                    }

                }
            }
        }
    }
}


@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
fun ActionBarBoxPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ActionBarBox(
            boxContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Main Content",
                        color = dgenWhite
                    )
                }
            },
            actionContent = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add",
                    tint = dgenWhite
                )
            }
        )
    }
}