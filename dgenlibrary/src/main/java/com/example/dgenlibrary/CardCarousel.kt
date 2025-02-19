package com.example.dgenlibrary

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import kotlin.math.abs




@Composable
fun StackedCardList(cards: List<String>) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        itemsIndexed(cards) { index, card ->
            val scrollOffset = listState.firstVisibleItemIndex + listState.firstVisibleItemScrollOffset / 1000f
            val position = index - scrollOffset

            val scale = remember { Animatable(1f) }
            val alpha = remember { Animatable(1f) }

            LaunchedEffect(position) {
                scale.animateTo(1f + 0.2f * (1 - position.coerceIn(0f, 1f)))
                alpha.animateTo(1f - position.coerceIn(0f, 1f))
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp)
                    .graphicsLayer {
                        this.alpha = alpha.value
                        this.scaleX = scale.value
                        this.scaleY = scale.value
                        this.translationY = position * 50.dp.toPx()
                        this.shadowElevation = (5 * (1 - position.coerceIn(0f, 1f))).dp.toPx()
                    },
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = card, fontSize = 24.sp, color = Color.Blue)
                }
            }
        }
    }
}

@Composable
fun CardCarousel(
    //modifier: Modifier = Modifier,
    content: List<Asset>,
) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(content) { index, card ->
            Card(
                modifier = Modifier.graphicsLayer(),
                backgroundColor = card.backgroundColor,
                frontSide = {
                    IdleView(
                        amount = card.amount,
                        tokenName = card.tokenName,
                        fiatAmount = card.fiatAmount,
                        chainList = card.chainList,
                        icon = card.icon
                    )
                },
            )
        }
    }
}

@Composable
fun CardCarousel2(
    modifier: Modifier = Modifier,
    content: List<Asset>,
    //content: @Composable () -> Unit,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(content) { index, card ->
            Card(
                modifier = Modifier.graphicsLayer(),
                backgroundColor = card.backgroundColor,
                frontSide = {
                    IdleView(
                        amount = card.amount,
                        tokenName = card.tokenName,
                        fiatAmount = card.fiatAmount,
                        chainList = card.chainList,
                        icon = card.icon
                    )
                },
            )
        }
    }

    /*val baseOffset = 40.dp
    val factor = 4

    val scrollState = rememberScrollState()

    Layout(
        content,
        modifier.background(Color.Red).verticalScroll(scrollState),
    ) { measurables, constraints ->

        //mesure
        val placeables =
            measurables.map { measurable ->
                measurable.measure(constraints)
            }

        val height = constraints.maxHeight
        val width = constraints.maxWidth


        // Total height: first card + all offsets
        val totalHeight = if (placeables.isNotEmpty()) {
            placeables.first().height + ((placeables.size -1) * (placeables.first().height * 0.6f)).toInt()
        } else {
            0
        }

        layout(width, totalHeight) {



            placeables.forEachIndexed { index, placeable ->
                val yOffset = index * baseOffset.roundToPx()
                //((index * index) / factor) * baseOffset.roundToPx() //index * index * baseOffset.roundToPx()
                //placeable.placeRelative(0, yOffset)
                //val yOffset = (index * placeable.height * 0.6f).toInt() // Reduce spacing between elements

                placeable.placeRelative(0, yOffset)
            }
        }

    }*/
}

//
@Preview(
    showBackground = true,
    widthDp = 447,
    heightDp = 447,
)
@Composable
fun StackedCardCarousel2ZAxisPreview() {

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
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFFF44336),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        )


    )
    /*content = {
            list.forEach{ item ->
                Card(
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
        }*/
    //StackedCardCarousel
    CardCarousel2(
        content = list
    )
}

@Preview(
    showBackground = true,
    widthDp = 447,
    heightDp = 447,
)
@Composable
fun StackedCardCarouselZAxisPreview() {

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
//        Asset(
//            amount = 120.00,
//            tokenName = "USDC",
//            fiatAmount = 120.00,
//            backgroundColor = Color(0xFFF44336),
//            chainList = listOf(1,10,8453,42161),
//            icon = R.drawable.usdc
//
//        )
    )

    CardCarousel(list)
}

//@Preview(
//    showBackground = true,
//    widthDp = 447,
//    heightDp = 447,
//)




