package com.example.dgen_component_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ExampleDropdownSlideList
import com.example.dgenlibrary.PrimaryButton

import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.SourceSansProFamily
import com.example.dgenlibrary.ui.theme.dgenWhite


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DgenTheme {

                var textFieldValue by remember { mutableStateOf(TextFieldValue("Text")) }

                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    DgenBasicTextfield(
                        textFieldValue,
                        {new -> textFieldValue = new},
                    )
                    DgenTextfield(
                        value = textFieldValue,
                        onValueChange = {new -> textFieldValue = new},
                        backgroundColor = DgenTheme.colors.dgenAqua,
                        cursorColor = DgenTheme.colors.dgenAqua,
                        cursorWidth = 21.dp,
                        cursorHeight = 42.dp,
                        textStyle = TextStyle(
                            fontFamily = SourceSansProFamily,
                            color = dgenWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 42.sp,
                            lineHeight = 42.sp,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        )
                    ){
                        Text(
                            text = "Text",
                            style = DgenTheme.typography.label,
                            color = DgenTheme.colors.dgenAqua
                        )
                    }
                }
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
        PrimaryButton(){
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