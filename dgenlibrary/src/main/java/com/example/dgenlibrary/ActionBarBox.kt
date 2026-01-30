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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

//@Composable
//fun ExampleActionBarBox(){
//    var textFieldValue by remember { mutableStateOf(TextFieldValue("Text")) }
//
//    var activeButton1 by remember { mutableStateOf(false) }
//    var activeButton2 by remember { mutableStateOf(false) }
//    var activeButton3 by remember { mutableStateOf(false) }
//
//    var pointerOffset by remember {
//        mutableStateOf(Offset(0f, 0f))
//    }
//
//    var blurEnabled by remember { mutableStateOf(false) }
//
//    val blurRadius by animateFloatAsState(
//        targetValue = if (blurEnabled) 20f else 0f,
//        animationSpec = tween(500)
//    )
//    var isAnyFieldFocused = remember { mutableStateOf(false) }
//
//    ActionBarBox(
//        Modifier
//            .fillMaxSize()
//        ,
//        boxContent = {
//
//            Box(modifier = Modifier.fillMaxSize()){
//                Column(
//                    Modifier
//                        .fillMaxSize()
//                        .background(Color.Black)
//                        .pointerInput("dragging") {
//                            detectDragGestures { change, dragAmount ->
//                                pointerOffset += dragAmount
//                            }
//                        }
//                        .onSizeChanged {
//                            pointerOffset = Offset(it.width / 2f, it.height / 2f)
//                        }
//                        .drawWithContent {
//                            drawContent()
//                            // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
//                            if (activeButton1 || activeButton2 || activeButton3){
//                                blurEnabled = true
//                            }else{
//                                blurEnabled = false
//                            }
//
//                        }
//                        .blur(blurRadius.dp)
//                ) {
//                    DgenBasicTextfield(
//                        textFieldValue,
//                        {new -> textFieldValue = new},
//                    )
//                    DgenTextfield(
//                        value = textFieldValue,
//                        onValueChange = {new -> textFieldValue = new},
//                        backgroundColor = DgenTheme.colors.dgenAqua,
//                        cursorColor = DgenTheme.colors.dgenAqua,
//                        cursorWidth = 21.dp,
//                        cursorHeight = 42.dp,
//                        textStyle = TextStyle(
//                            fontFamily = SourceSansProFamily,
//                            color = dgenWhite,
//                            fontWeight = FontWeight.SemiBold,
//                            fontSize = 42.sp,
//                            lineHeight = 42.sp,
//                            letterSpacing = 0.sp,
//                            textDecoration = TextDecoration.None
//                        )
//                    ){
//                        Text(
//                            text = "Text",
//                            style = DgenTheme.typography.label,
//                            color = DgenTheme.colors.dgenAqua
//                        )
//                    }
//
//                    Text(
//                        text= "1: $activeButton1, 2: $activeButton2, 3: $activeButton3, ",
//                        color = Color.White
//                    )
//                }
//                AnimatedVisibility(
//                    visible = blurEnabled,
//                    enter = fadeIn(
//                        animationSpec = tween(
//                            durationMillis = 300,
//                            delayMillis = 100,
//                        )
//                    ),
//                    exit = fadeOut(
//                        animationSpec = tween(
//                            durationMillis = 250,
//                            delayMillis = 100,
//                        )
//                    ),
//                ) {
//                    Box(modifier = Modifier.fillMaxSize()){
//                        LazyColumn(
//                            modifier = Modifier.padding(start = 24.dp, end = 72.dp),
//                            verticalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            items(15){
//                                Text(text="Test",color = Color.White, fontSize = 24.sp)
//                            }
//                        }
//                    }
//                }
//
//
//            }
//
//        },
//        actionContent = {
//            ActionButton(
//                onClick = {
//                    if(activeButton1){
//                        activeButton1 = false
//                        activeButton2 = false
//                        activeButton3 = false
//                    }else{
//                        activeButton1 = true
//                        activeButton2 = false
//                        activeButton3 = false
//                    }
//
//                },
//                icon = {
//                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "",
//                        tint = if(activeButton1) {
//                            DgenTheme.colors.dgenRed
//                        } else {
//                            DgenTheme.colors.dgenWhite
//                        }
//                    )
//                }
//            )
//            ActionButton(
//                onClick = {
//                    if(activeButton2){
//                        activeButton1 = false
//                        activeButton2 = false
//                        activeButton3 = false
//                    }else{
//                        activeButton1 = false
//                        activeButton2 = true
//                        activeButton3 = false
//                    }
//
//                },
//                icon = {
//                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "",
//                        tint = if(activeButton2) {
//                            DgenTheme.colors.dgenRed
//                        } else {
//                            DgenTheme.colors.dgenWhite
//                        }
//                    )
//                }
//            )
//            ActionButton(
//                onClick = {
//                    if(activeButton3){
//                        activeButton1 = false
//                        activeButton2 = false
//                        activeButton3 = false
//                    }else{
//                        activeButton1 = false
//                        activeButton2 = false
//                        activeButton3 = true
//                    }
//
//                },
//                icon = {
//                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "",
//                        tint = if(activeButton3) {
//                            DgenTheme.colors.dgenRed
//                        } else {
//                            DgenTheme.colors.dgenWhite
//                        }
//                    )
//                }
//            )
//        }
//    )
//}

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