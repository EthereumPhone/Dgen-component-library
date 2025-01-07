package com.example.dgen_component_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dgen_component_library.ui.theme.DgencomponentlibraryTheme
import com.example.dgenlibrary.PrimaryButton
import com.example.dgenlibrary.ui.theme.DgenColors

import com.example.dgenlibrary.ui.theme.DgenTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DgenTheme {
//                Box(contentAlignment = Alignment.TopEnd){
//                    var drawerState by remember {
//                        mutableStateOf(false)
//                    }
//
//                    val translateContent: Dp by animateDpAsState(if (drawerState) 200.dp else 0.dp, label = "translateContent")
//                    val translateList: Dp by animateDpAsState(if (drawerState) 0.dp else 200.dp, label = "translateList")
//
//                    // --- Changed from a Set<Int> to a mutable list for order-based swapping ---
//                    val selectedIndicesList = remember { mutableStateListOf<Int>() }
//                    var activeSlot by remember { mutableStateOf<Int?>(null) }
//
//                    val items by remember {
//                        mutableStateOf(
//                            (1..20).map {
//                                val rnd = Random.nextDouble(0.0,100.0)
//                                ItemData(
//                                    name = "name $it",
//                                    desc = "-name-",
//                                    value = String.format("%.2f",rnd),
//                                    isSelected = false
//                                )
//
//                            }
//                        )
//                    }
//
//                    var dropdownModifer = when(drawerState){
//                        true -> {
//                            Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .background(Color.Blue)
//                                .graphicsLayer {
//                                    //this.translationX = -200.dp.toPx() // open
//                                    this.translationX = -translateContent.toPx() //closed
//                                }
//                                .clickable {
//                                    if (drawerState) {
//                                        drawerState = false
//                                    }
//                                }
//                        }
//                        false -> {
//                            Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight()
//                                .background(Color.Blue)
//                                .graphicsLayer {
//                                    //this.translationX = -200.dp.toPx() // open
//                                    this.translationX = -translateContent.toPx() //closed
//                                }
//
//                        }
//                    }
//                    Column(
//                        modifier = dropdownModifer,
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//
//                        // Show the first and second selections
//                        val firstSelectedIndex = selectedIndicesList.getOrNull(0)
//                        val secondSelectedIndex = selectedIndicesList.getOrNull(1)
//                        val thirdSelectedIndex = selectedIndicesList.getOrNull(2)
//
//                        val selectedItem1 = if (firstSelectedIndex != null) {
//                            items[firstSelectedIndex].name
//                        } else {
//                            "Default1"
//                        }
//
//                        val selectedItem2 = if (secondSelectedIndex != null) {
//                            items[secondSelectedIndex].name
//                        } else {
//                            "Default2"
//                        }
//
//
//
//                        Text(text = "Item 1: $selectedItem1", color = Color.White)
//                        Button(onClick = {
//                            drawerState = !drawerState
//                            activeSlot = 0
//                        }) {
//                            Text("Open / Close Drawer")
//                        }
//                        Text(text = "Item 2: $selectedItem2", color = Color.White)
//                        Button(onClick = {
//                            drawerState = !drawerState
//                            activeSlot = 1
//                        }) {
//                            Text("Open / Close Drawer")
//                        }
//
//
//                    }
//
//
//
//                    List(
//                        modifier = Modifier.graphicsLayer {
//                            this.translationX = translateList.toPx() // closed
//                            //this.translationX = 0.dp.toPx() // -open
//                        },
//                        items = items,
//                        selectedIndicesList = selectedIndicesList, // pass it in
//                        maxSelections = 2,  // two items
//                        activeSlot = activeSlot
//                    )
//                    //SecondScreen()
//                }

                ExampleDropdownSlideList()
            }
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
fun MyTransition() {
//    DgenTheme {
//        val navController = rememberNavController()
//        NavHost(
//            navController = navController,
//            startDestination = "FirstScreen",
//
//            ) {
//            composable("FirstScreen",
//                        enterTransition = {
//                            slideIntoContainer(
//                                animationSpec = tween(300, easing = FastOutSlowInEasing),
//                                towards = AnimatedContentTransitionScope.SlideDirection.Start
//                            )
//                        },
//                        exitTransition = {
//                            slideOutOfContainer(
//                                animationSpec = tween(250, easing = FastOutSlowInEasing),
//                                towards = AnimatedContentTransitionScope.SlideDirection.End
//                            )
//                        }
//            ) {
//                FirstScreen(navigation = navController)
//            }
//            composable("SecondScreen",
//                enterTransition = {
//                    slideIntoContainer(
//                        animationSpec = tween(300, easing = FastOutSlowInEasing),
//                        towards = AnimatedContentTransitionScope.SlideDirection.Start
//                    )
//                },
//                exitTransition = {
//                    slideOutOfContainer(
//                        animationSpec = tween(250, easing = FastOutSlowInEasing),
//                        towards = AnimatedContentTransitionScope.SlideDirection.End
//                    )
//                }
//            ) {
//                SecondScreen(navigation = navController)
//            }
//        }
//    }
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