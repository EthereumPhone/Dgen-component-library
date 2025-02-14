package com.example.dgen_component_library

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalFoundationApi::class)
@Composable
fun ImagePreviewScreen(
    //images: List<String>,
    onNavigateBack: () -> Unit,
    resId: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {


            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 24.dp, top = 12.dp),contentAlignment = Alignment.Center
            ) {
                BackButton(
                    onClick = onNavigateBack
                )
            }


        with(sharedTransitionScope) {
            Image(
                painter = painterResource(resId),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp) // Setze die gewünschte Größe in der Vorschau

                    .sharedElement(
                        rememberSharedContentState(key = "image/$resId"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { initial, target ->
                            tween(durationMillis = 1000)
                        }
                    )

            )
        }
    }
}
