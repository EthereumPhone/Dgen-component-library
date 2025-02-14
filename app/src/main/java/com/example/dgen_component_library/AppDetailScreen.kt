package com.example.dgen_component_library

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.Exo2Family
import com.example.dgenlibrary.ui.theme.SourceSansProFamily
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import kotlin.math.max


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainDetailScreenRoute(
    app: AppMetadata,
    onNavigateBack: () -> Unit,
    onItemClick: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){
    MainDetailScreen(app = app, onNavigateBack = onNavigateBack, onItemClick = onItemClick, animatedVisibilityScope = animatedVisibilityScope, sharedTransitionScope = sharedTransitionScope)
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainDetailScreen(
    modifier: Modifier = Modifier,
    app: AppMetadata,
    onItemClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){

    val imagesOpt = listOf(
        painterResource(R.drawable.coinbase_pic1),
        painterResource(R.drawable.coinbase_pic2),
        painterResource(R.drawable.coinbase_pic1),
        painterResource(R.drawable.coinbase_pic2),

        )

    val images = remember {listOf(
        R.drawable.coinbase_pic1, // Replace with your drawable resources
        R.drawable.coinbase_pic2,
        R.drawable.coinbase_pic1, // Replace with your drawable resources
        R.drawable.coinbase_pic2,
    )

    }

    ColumnCollapsibleHeader(
        modifier = Modifier.fillMaxSize(),
        properties = ColumnCollapsibleHeader.Properties(
            min = 72.dp,
            max = 224.dp
        ),
        header = { progress, progressDp, minHeight, maxHeight ->
            DetailHeader(
                minHeight = minHeight,
                maxHeight = maxHeight,
                progress = progress,
                progressDp = progressDp,
                onNavigateBack = onNavigateBack,
                app = app,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionScope = sharedTransitionScope,
            )
        },
        body = {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item(){
                    Spacer(Modifier.height(300.dp).width(50.dp))
                }
                itemsIndexed(imagesOpt) { index, resId ->
                    with(sharedTransitionScope) {
                        Image(
                            painter = resId, //painterResource(resId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(300.dp)
                                .aspectRatio(1f)
                                .clickable {
                                    onItemClick(images[index])
                                }
                                .sharedElement(
                                    rememberSharedContentState(key = "image/${images[index]}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { initial, target ->
                                        tween(durationMillis = 1000)
                                    }
                                )

                        )
                    }

                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "What's new in this version",
                    style = TextStyle(
                        fontFamily = Exo2Family,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    color = DgenTheme.colors.dgenWhite
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
                    style = TextStyle(
                        fontFamily = SourceSansProFamily,
                        color = dgenWhite,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    color = DgenTheme.colors.dgenWhite
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "by Coinbase, Inc.",
                    style = TextStyle(
                        fontFamily = Exo2Family,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    color = DgenTheme.colors.dgenWhite
                )
            }



        }
    )
}
