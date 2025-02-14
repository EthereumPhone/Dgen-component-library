package com.example.dgen_component_library

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import kotlin.math.abs
import kotlin.math.min


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val testData = listOf(
                AppMetadata(
                    "Coinbase 1",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("DEFI")
                ),
                AppMetadata(
                    "Coinbase 2",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("MARKETPLACE")
                ),
                AppMetadata(
                    "Coinbase 3",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("BANKING")
                ),AppMetadata(
                    "Coinbase 4",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("DEFI")
                ),
                AppMetadata(
                    "Coinbase 5",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("MARKETPLACE")
                ),
                AppMetadata(
                    "Coinbase 6",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("BANKING")
                ),AppMetadata(
                    "Coinbase 7",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("DEFI")
                ),
                AppMetadata(
                    "Coinbase 8",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("MARKETPLACE")
                ),
                AppMetadata(
                    "Coinbase 9",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("BANKING")
                ),AppMetadata(
                    "Coinbase 10",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("DEFI")
                ),
                AppMetadata(
                    "Coinbase 11",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("MARKETPLACE")
                ),
                AppMetadata(
                    "Coinbase 12",
                    R.drawable.coinbase2,
                    "Buy and sell Bitcoin, Ethereum, and more with trust.",
                    listOf("BANKING")
                ),

                )
            var selectedApp = remember { mutableStateOf<AppMetadata>(AppMetadata()) }


            val scrollState = rememberScrollState()
            var scrollVelocity by remember { mutableStateOf(0f) }
            var searchValue by remember { mutableStateOf(TextFieldValue("")) }
//            val filteredItems = emptyList().filter { pair ->
//                pair.first.name.contains(searchValue.text, ignoreCase = true)
//            }

            val focusManager = LocalFocusManager.current
            var isAnyFieldFocused = remember { mutableStateOf(false) }
            var focusedSearch by remember { mutableStateOf(false) }
            val backgroundColor by animateColorAsState(
                targetValue = if (focusedSearch) dgenOcean else Color.Transparent,
                animationSpec = tween(durationMillis = 500),
                label = "backgroundColor" // Dauer der Animation in Millisekunden
            )

            // Calculate scroll velocity using nestedScroll as shown earlier
            val nestedScrollConnection = remember {
                object : androidx.compose.ui.input.nestedscroll.NestedScrollConnection {
                    var lastScrollTimeMillis = System.currentTimeMillis()

                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        val currentTimeMillis = System.currentTimeMillis()
                        val deltaY = available.y.toFloat()

                        if (currentTimeMillis > lastScrollTimeMillis) {
                            val timeElapsed = currentTimeMillis - lastScrollTimeMillis
                            scrollVelocity = abs(deltaY / timeElapsed)
                            lastScrollTimeMillis = currentTimeMillis

                        }

                        return super.onPreScroll(available, source)
                    }
                }
            }

            val context = LocalContext.current
            val dynamicHeight by derivedStateOf {
                lerp(86.dp, 56.dp, min(1f, scrollState.value / 300f))
            }

            var showPreviewPic by remember {
                mutableStateOf(false)
            }

            DgenTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    Box(Modifier.background(dgenBlack).fillMaxSize()){
                        NavHost(
                            navController = navController,
                            startDestination = "home"
                        ) {
                            addMainDetailScreen(navController, this@SharedTransitionLayout)
                            addAppGridScreen(navController, testData, this@SharedTransitionLayout)
                            addPreviewScreen(navController,this@SharedTransitionLayout)

                        }
                    }
                }



            }
        }
    }
}





@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addMainDetailScreen(
    navController: NavController, sharedTransitionScope: SharedTransitionScope,
) {
    composable(
        route = "detail/{resId}/{name}/{description}/{category}",
        arguments = listOf(
            navArgument("resId") { type = NavType.IntType },
            navArgument("name") { type = NavType.StringType },
            navArgument("description") { type = NavType.StringType },
            navArgument("category") { type = NavType.StringType },
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) }

    ) { backStackEntry ->
        val resId = backStackEntry.arguments?.getInt("resId") ?: return@composable
        val name = backStackEntry.arguments?.getString("name") ?: return@composable
        val description = backStackEntry.arguments?.getString("description") ?: return@composable
        val category = backStackEntry.arguments?.getString("category") ?: return@composable

        val detailApp = AppMetadata(
            logo = resId,
            name = name,
            description = description,
            tags = listOf(category)
        )
        MainDetailScreenRoute(
            app = detailApp,
            onNavigateBack = { navController.popBackStack() },
            onItemClick = { resId ->
                navController.navigate("preview/${resId}")
            },
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addAppGridScreen(navController: NavController, data: List<AppMetadata>, sharedTransitionScope: SharedTransitionScope) {
    composable(
        route = "home",
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) }
    ) {
        AppGridScreenRoute(
            data = data,
            onNavigateToDetail = { item ->
                navController.navigate("detail/${item.logo}/${item.name}/${item.description}/${item.tags[0]}")
            },
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this

        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.addPreviewScreen(
    navController: NavController,sharedTransitionScope: SharedTransitionScope
) {
    composable(
        route = "preview/{resId}",
        arguments = listOf(navArgument("resId") { type = NavType.IntType }),
    ) {  backStackEntry ->
        val resId = backStackEntry.arguments?.getInt("resId") ?: return@composable
        ImagePreviewScreen(
            resId = resId,
            onNavigateBack = {
                navController.popBackStack()
            },
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this
        )
    }
}

data class AppMetadata(
    val name: String = "",
    val logo: Int = 0,
    val description: String ="",
    val tags: List<String> = emptyList(),

    )