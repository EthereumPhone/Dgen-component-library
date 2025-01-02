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
                Box(contentAlignment = Alignment.TopEnd){
                    var drawerState by remember {
                        mutableStateOf(false)
                    }
                    var selectedItem by remember { mutableStateOf<ItemData?>(null) }

                    val translateContent: Dp by animateDpAsState(if (drawerState) 200.dp else 0.dp, label = "translateContent")
                    val translateList: Dp by animateDpAsState(if (drawerState) 0.dp else 200.dp, label = "translateList")

                    val items by remember {
                        mutableStateOf(
                            (1..20).map {
                                val rnd = Random.nextDouble(0.0,100.0)
                                ItemData(
                                    name = "name $it",
                                    desc = "-name-",
                                    value = String.format("%.2f",rnd),
                                    isSelected = false
                                )

                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(Color.Blue)
                            .graphicsLayer {
                                //this.translationX = -200.dp.toPx() // open
                                this.translationX = -translateContent.toPx() //closed
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Selected Option: ${selectedItem?.name ?: "None"}")
                        Button(onClick = {
                            //navigation.navigate("SecondScreen")
                            drawerState = !drawerState
                        }) {
                            Text("Go to second screen")
                        }
                    }
                    List(
                        modifier = Modifier.graphicsLayer {
                            this.translationX = translateList.toPx() // closed
                            //this.translationX = 0.dp.toPx() // -open
                        },
                        items = items,
                        onItemSelected = { item ->
                            selectedItem = item  // Keep track of which item was clicked last
                        }
                    )
                    //SecondScreen()
                }
            }
        }
    }
}



@Composable
fun List(
    modifier: Modifier = Modifier,
    items: List<ItemData>,
    onItemSelected: (ItemData?) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }

    LazyColumn(
        modifier = modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(Color.Red)
    ) {
        itemsIndexed(items) { index, item ->
            val isSelected = (index == selectedIndex)
            CustomListItem(
                item = item.copy(isSelected = isSelected),
                onClick = {
                    // If the same item is clicked, unselect it; otherwise select it
                    selectedIndex = if (isSelected) -1 else index
                    onItemSelected(if (selectedIndex == -1) null else item)
                }
            )
        }
    }
}

@Composable
fun CustomListItem(item: ItemData, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(if (item.isSelected) DgenTheme.colors.dgenWhite else DgenTheme.colors.dgenBlack)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
        ){
            Text(
                text = item.name,
                style = DgenTheme.typography.body1,
                color = if (item.isSelected) DgenTheme.colors.dgenBlack else DgenTheme.colors.dgenWhite
            )
            Text(
               text = item.desc,
                style = DgenTheme.typography.label,
                color = if (item.isSelected) DgenTheme.colors.dgenBlack else DgenTheme.colors.dgenWhite
            )
        }
        Text(

            text = item.value,
            style = DgenTheme.typography.button,
            color = if (item.isSelected) DgenTheme.colors.dgenBlack else DgenTheme.colors.dgenWhite
        )
    }
}

data class ItemData(
    var name: String = "",
    var desc: String = "",
    var value: String = "",
    var isSelected: Boolean = false
)

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