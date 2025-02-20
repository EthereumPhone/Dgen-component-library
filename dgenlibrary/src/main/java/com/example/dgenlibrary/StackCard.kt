package com.example.dgenlibrary

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.zIndex
import kotlin.math.abs


@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()

    Layout(
        modifier = modifier.verticalScroll(scrollState),
        content = content
    ) { measurables, constraints ->
        

        var scaleFactor = 0.9f  // Start with full size
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children

            measurable.measure(constraints)
        }


        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x =  (constraints.maxWidth - placeable.width) / 2, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += (placeable.height*0.20f).toInt()
            }
        }
    }
}
@SuppressLint("RestrictedApi")
@Composable
fun MyCardCarousel(
    assets: List<Asset>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = assets.lastIndex)

    // Ensure scrolling starts at the last item
    LaunchedEffect(Unit) {
        listState.scrollToItem(assets.lastIndex)
    }

//    Box (
//        modifier = modifier.zIndex(0f).fillMaxSize().background(Color.Black).padding(horizontal = 16.dp),
//    ){

        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize().zIndex(3f),
            verticalArrangement = Arrangement.spacedBy((-225).dp), // Overlapping effect
            contentPadding = PaddingValues(top = 60.dp, bottom =40.dp) // Ensures enough space for scrolling
        ) {
            itemsIndexed(assets) { index, item ->
                val scrollOffset = listState.firstVisibleItemIndex + listState.firstVisibleItemScrollOffset / 1000f
                val relativeIndex = index - scrollOffset

                // Animated values

                //val scaleFactor = lerp(0.9f, 0.5f, (abs(relativeIndex) / 5).coerceIn(0f, 1f))
                val scaleFactor by animateFloatAsState(
                    targetValue = lerp(0.8f, 0.55f, (abs(relativeIndex) / 4).coerceIn(0f, 1f)),
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )

                val alphaFactor by animateFloatAsState(
                    targetValue = lerp(1f, 0f, (abs(relativeIndex) / 4).coerceIn(0f, 1f)),
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                )

                val frontCardTranslation by animateFloatAsState(
                    targetValue = lerp(0f, 800f, (relativeIndex).coerceIn(0f, 1f)),
                    animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                            alpha = alphaFactor
                            rotationX = -5f
                            translationY = frontCardTranslation
                        },
                    backgroundColor = item.backgroundColor,
                    frontSide = {
                        IdleView(
                            amount = item.amount,
                            tokenName = item.tokenName,
                            fiatAmount = item.fiatAmount,
                            chainList = item.chainList,
                            icon = item.icon
                        )
                    },
                )
            }
//
        }
//    }

}





@Preview(
    showBackground = true,
    widthDp = 447,
    heightDp = 447,
)
@Composable
fun MyCardColumnPreview() {

    val list = listOf(
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFF1E5A9C),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFF9C27B0),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFF8BC34A),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFFE91E63),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFF009688),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFFFFEB3B),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFF00BCD4),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),
        Asset(
            amount = 120.00,
            tokenName = "USDC",
            fiatAmount = 120.00,
            backgroundColor = Color(0xFFF44336),
            chainList = listOf(1,10,8453,42161),
            icon = R.drawable.usdc

        ),

        )


    Column (
        modifier = Modifier.fillMaxSize().background(Color.Black),

        ) {

        Box(
            modifier = Modifier
                .weight(1f) // Allows it to take up remaining space
                .padding(horizontal = 16.dp)
        ) {
            MyCardCarousel(
                modifier = Modifier,
                assets = list
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()

                .height(70.dp) // Adjust height as needed
        ) {
            // Fading border overlay (Green to Transparent)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp) // Adjust thickness of fading border
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent,Color.Black)
                        )
                    )

            )

            // Centered Row of Buttons (Below Fading Border)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                     // Prevents it from being too close to the border
                .background(Color.Black).padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { /* TODO: Action */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                IconButton(onClick = { /* TODO: Action */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Forward",
                        tint = Color.White
                    )
                }
            }
        }
    }

}






//
//@Preview(
//    showBackground = true,
////    widthDp = 447,
////    heightDp = 447,
//)
//@Composable
//fun MyBasicColumnPreview() {
//
//    val list = listOf(
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFF1E5A9C),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFF9C27B0),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFF8BC34A),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFFE91E63),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFF009688),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFFFFEB3B),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFF00BCD4),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFFF44336),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        ),
//
//        )
//
//    MyBasicColumn(
//        modifier = Modifier.padding(horizontal = 16.dp),
//
//
//        content = {
//            val lastIndex = list.lastIndex.coerceAtLeast(1) // Prevent division by zero if list is empty
//
//            list.forEachIndexed { index, item ->
//                val scaleFactor = 0.6f + (index.toFloat() / lastIndex) * (1f - 0.6f)
//                val alphaFactor = 0.6f + (index.toFloat() / lastIndex) * (1f - 0.6f)
//
//                Card(
//                    modifier = Modifier.graphicsLayer{
//                        scaleX = scaleFactor
//                        scaleY = scaleFactor
//                        alpha = alphaFactor
//                        rotationX = -5f
//                    },
//                    backgroundColor = item.backgroundColor,
//                    frontSide = {
//                        IdleView(
//                            amount = item.amount,
//                            tokenName = item.tokenName,
//                            fiatAmount = item.fiatAmount,
//                            chainList = item.chainList,
//                            icon = item.icon
//                        )
//                    },
//                )
//            }
//        }
//    )
//}