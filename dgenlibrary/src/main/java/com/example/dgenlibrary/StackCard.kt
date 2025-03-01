package com.example.dgenlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import kotlin.math.abs



@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("RestrictedApi")
@Composable
fun MyCardCarousel(
    assets: List<Asset>,
    choosenAsset: Asset,
    getFrontCardInfo: (Color, String, Asset) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = assets.lastIndex)
    var frontCardIndex by remember { mutableStateOf(assets.lastIndex) }
    var isCardSelected by remember { mutableStateOf(false) }


    // Ensure scrolling starts at the last item
    LaunchedEffect(Unit) {
        val index = assets.indexOf(choosenAsset)
        listState.scrollToItem(index)
    }

    Box (
        modifier = Modifier.fillMaxWidth().height(16.dp)
    ){  }
        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize().zIndex(3f),
            verticalArrangement = Arrangement.spacedBy((-240).dp), // Overlapping effect
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
                    targetValue = lerp(0f, 1000f, (relativeIndex).coerceIn(0f, 1f)),
                    animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                )


                // Detect the front card
                LaunchedEffect(scaleFactor) {
                    if (scaleFactor >= 0.79f) { // Check if the card is closest to the target scale
                        frontCardIndex = index
                        getFrontCardInfo(item.backgroundColor,"asset_$index", item)

                    }
                }


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = scaleFactor
                                scaleY = scaleFactor
                                alpha = alphaFactor
                                rotationX = -10f
                                translationY = frontCardTranslation
                            }
                            ,
//                        backgroundColor = item.backgroundColor,
                        frontSide = {
                            IdleView(
                                amount = item.amount,
                                tokenName = item.tokenName,
                                fiatAmount = item.fiatAmount,
//                                chainList = item.chainList,
                                icon = item.icon
                            )
                        },
//                        sharedTransitionScope = sharedTransitionScope,
//                        animatedVisibilityScope = animatedVisibilityScope,
//                        cardId = "asset_$index"
                    )


            }
//
        }
//    }

}



@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CardColumn(
    getFrontCardInfo: (Color, String,Asset) -> Unit,
    onShowDetails: () -> Unit,
    assets: List<Asset>,
    choosenAsset: Asset,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {



    val context = LocalContext.current
    Box (
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .background(dgenBlack)
                //.weight(1f) // Allows it to take up remaining space

        ) {
            MyCardCarousel(
                modifier = Modifier.padding(bottom = 24.dp),
                assets = assets,
                choosenAsset = choosenAsset,
                getFrontCardInfo = getFrontCardInfo,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()

        ) {
            // Fading border overlay (Green to Transparent)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp) // Adjust thickness of fading border
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, dgenBlack)
                        )
                    )

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(dgenBlack)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(modifier = Modifier, onClick = onShowDetails) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.width(16.dp),
                            painter = painterResource(R.drawable.baseline_arrow_outward_24),
                            contentDescription = "Back",
                            tint = dgenTurqoise
                        )
                        Text(
                            text= "SEND",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = dgenTurqoise,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                letterSpacing = 0.sp,
                                textDecoration = TextDecoration.None
                            )
                        )
                    }

                }
                Spacer(modifier = Modifier.width(32.dp))
                IconButton(modifier = Modifier, onClick = { /* TODO: Action */ }) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.width(16.dp).graphicsLayer{
                                rotationZ = 90f
                            },
                            painter = painterResource(R.drawable.baseline_swap_horiz_24),
                            contentDescription = "Back",
                            tint = dgenTurqoise
                        )
                        Text(
                            text= "LOG",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = dgenTurqoise,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                letterSpacing = 0.sp,
                                textDecoration = TextDecoration.None
                            )
                        )
                    }

                }
                Spacer(modifier = Modifier.width(32.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = dgenTurqoise,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Gray
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.width(IntrinsicSize.Max),
                    onClick = {
                        copyTextToClipboard(
                            context,
                            "ADD TEXT TO COPY"
                        )
                        Toast.makeText(context, "Copied Address", Toast.LENGTH_SHORT).show() // in Activity

                    }
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.width(16.dp).graphicsLayer{
                                rotationZ = 90f
                            },
                            painter = painterResource(R.drawable.qr_code),
                            contentDescription = "Back",
                            tint = dgenTurqoise
                        )
                        Text(
                            text= "CPY ADD",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = dgenTurqoise,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                lineHeight = 12.sp,
                                letterSpacing = 0.sp,
                                textDecoration = TextDecoration.None
                            )
                        )
                    }

                }

            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StackedVerticalPager(
    assets: List<Asset>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = assets.size -1, // Start at first page
        initialPageOffsetFraction = 0.5f, // No offset initially
        pageCount = { assets.size } // Dynamically set page count
    )

    Box(modifier = modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val pageOffset = (page - pagerState.currentPage).toFloat()

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1.6f)
                    .graphicsLayer {
                        // Scale effect to stack cards
                        val scale = 1f - (0.05f * abs(pageOffset))
                        scaleX = scale
                        scaleY = scale

                        // Vertical translation to create the stack effect
                        translationY = 275f//pageOffset * 50.dp.toPx()

                        // Alpha fade for depth effect
                        alpha = 1f - (0.3f * abs(pageOffset))
                    }
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp),
                color = assets[page].backgroundColor
            ) {
                Text(
                    text = assets[page].tokenName,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(
    showBackground = true,
    widthDp = 447,
    heightDp = 447,
)
@Composable
fun StackedVerticalPagerPreview(){
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
    StackedVerticalPager(
        assets = list
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(
    showBackground = true,
    widthDp = 447,
    heightDp = 447,
)
@Composable
fun WalletPreview(){

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

    var showDetails by remember {
        mutableStateOf(false)
    }

    var testcolor by remember {
        mutableStateOf(Color.Blue)
    }

    var cardId by remember {
        mutableStateOf("asset_${list.size-1}")
    }

    var choosenAsset by remember {
        mutableStateOf(list.last())
    }

    Box(
        modifier = Modifier.fillMaxSize().background(dgenBlack)
    ){
        SharedTransitionLayout {
            AnimatedContent(
                showDetails,
                label = "basic_transition"
            ) { targetState ->
                if (!targetState) {
//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                    ){
//                        Text(
//                            modifier = Modifier.background(testcolor).fillMaxWidth().zIndex(5f).align(Alignment.TopCenter),
//                            text = "TEST - "+cardId,
//                            style = TextStyle(
//                                fontFamily = PitagonsSans,
//                                color = dgenWhite,
//                                fontWeight = FontWeight.SemiBold,
//                                fontSize = 24.sp,
//
//                                letterSpacing = 0.sp,
//                                textDecoration = TextDecoration.None
//                            )
//                        )
                        CardColumn(
                            assets = list,
                            getFrontCardInfo = { color, cardkey, asset ->
                                cardId = cardkey
                                testcolor = color
                                choosenAsset = asset

                            },
                            onShowDetails = {
                                showDetails = true
                            },
                            animatedVisibilityScope = this@AnimatedContent,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            choosenAsset = choosenAsset
                        )
//                    }

                } else {
                    IsolatedSendCardView(
                        cardId = cardId,
                        onNavigateBack = {
                            //choosenAsset = null
                            showDetails = false
                        },
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        asset = choosenAsset
                    )
                }
            }
        }
    }

}

@SuppressLint("ServiceCast")
private fun copyTextToClipboard(context: Context, text: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    clipboardManager.setText(AnnotatedString(text))
}
