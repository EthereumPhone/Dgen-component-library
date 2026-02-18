package com.example.dgenlibrary.ui.token

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.dgenlibrary.Card
import com.example.dgenlibrary.IdleView
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.largeEnterDuration
import com.example.dgenlibrary.ui.theme.smallDuration
import kotlin.math.abs
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Data class representing a token item in the carousel.
 *
 * @param id Unique identifier for this token (used as the list key)
 * @param symbol Token symbol (e.g. "ETH", "USDC")
 * @param logoUrl URL of the token logo image, or null for placeholder
 * @param balance Token balance amount
 * @param fiatBalance Fiat currency equivalent of the balance
 */
data class TokenCarouselItem(
    val id: String,
    val symbol: String,
    val logoUrl: String? = null,
    val balance: Double = 0.0,
    val fiatBalance: Double = 0.0,
)

/**
 * A vertically stacked card carousel for displaying token assets.
 *
 * Cards are displayed in a perspective stack with scale, alpha, and translation
 * animations driven by scroll position. Supports:
 * - Auto-scroll to the last token on initial load
 * - Scroll position persistence across recompositions
 * - Custom nested scroll with adjustable sensitivity
 * - Top gradient fade overlay
 *
 * @param assets List of [TokenCarouselItem] to display
 * @param onSendClick Callback when the send action is triggered on a token, receives the token id
 * @param primaryColor Primary accent color for the cards
 * @param secondaryColor Secondary color for the cards
 * @param modifier Modifier applied to the root container
 * @param topPadding Padding above the first card
 * @param scrollSensitivity Drag sensitivity multiplier (lower = slower scroll). Default is 0.2f.
 * @param showTopGradient Whether to display the top-edge gradient fade. Default is true.
 * @param cardContent Optional custom card content composable. When provided, it replaces the
 *   default [IdleView]-based rendering. Receives the [TokenCarouselItem], whether the card is
 *   the first visible card, and the primary color.
 */
@SuppressLint("RestrictedApi")
@Composable
fun TokenCardCarousel(
    assets: List<TokenCarouselItem>,
    onSendClick: (id: String) -> Unit,
    primaryColor: Color,
    secondaryColor: Color,
    modifier: Modifier = Modifier,
    topPadding: androidx.compose.ui.unit.Dp = 80.dp,
    scrollSensitivity: Float = 0.2f,
    showTopGradient: Boolean = true,
    cardContent: (@Composable (item: TokenCarouselItem, isFirst: Boolean, color: Color) -> Unit)? = null,
) {
    var savedScrollIndex by rememberSaveable { mutableStateOf(0) }
    var savedScrollOffset by rememberSaveable { mutableStateOf(0) }
    var autoScrollDone by rememberSaveable { mutableStateOf(false) }
    var isUserScrolling by remember { mutableStateOf(false) }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = savedScrollIndex,
        initialFirstVisibleItemScrollOffset = savedScrollOffset
    )

    LaunchedEffect(listState.isScrollInProgress, isUserScrolling) {
        if (!listState.isScrollInProgress && isUserScrolling) {
            savedScrollIndex = listState.firstVisibleItemIndex
            savedScrollOffset = listState.firstVisibleItemScrollOffset
            isUserScrolling = false
        }
    }

    val coroutineScope = rememberCoroutineScope()
    var scrollJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(assets) {
        if (assets.isNotEmpty() && !autoScrollDone) {
            autoScrollDone = true
            scrollJob?.cancel()
            isUserScrolling = false
            val targetIndex = assets.lastIndex
            if (targetIndex >= 0) {
                scrollJob = coroutineScope.launch {
                    listState.scrollToItem(targetIndex)
                    savedScrollIndex = targetIndex
                    savedScrollOffset = 0
                }
            }
        }
    }

    val cardHeight = 400.dp
    val visibleCount = 5
    val overlap = (-cardHeight / visibleCount) * 4
    val clampRange = (visibleCount - 1).toFloat()

    val nestedScrollConnection = remember(scrollSensitivity) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source == NestedScrollSource.Drag) {
                    if (!isUserScrolling) {
                        isUserScrolling = true
                        scrollJob?.cancel()
                    }
                }
                val scaledY = available.y * scrollSensitivity
                if (isUserScrolling) {
                    coroutineScope.launch {
                        listState.scrollBy(scaledY)
                    }
                    return Offset(x = 0f, y = scaledY)
                }
                return Offset.Zero
            }
        }
    }

    val baseBottomPadding = 130.dp
    val itemSpacing = overlap - 32.dp
    val extraScrollMargin = cardHeight * 0.5f

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val containerHeight = this.maxHeight

        val maxTranslation = (containerHeight.value * 1.5f).coerceIn(900f, 1600f)

        val baseContentHeight = (cardHeight * assets.size) +
                (itemSpacing * (assets.size - 1).coerceAtLeast(0)) +
                topPadding

        val bottomPadding = maxOf(
            baseBottomPadding,
            (containerHeight + extraScrollMargin - baseContentHeight)
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .zIndex(3f),
            verticalArrangement = Arrangement.spacedBy(itemSpacing),
            contentPadding = PaddingValues(top = topPadding, bottom = bottomPadding)
        ) {
            itemsIndexed(assets, key = { _, asset -> asset.id }) { index, item ->
                val rotX: Float by animateFloatAsState(-25f, label = "rotX")

                val firstVisibleIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
                val isFirstCard = index == firstVisibleIndex

                val firstVisibleOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
                val scrollOffset = firstVisibleIndex + firstVisibleOffset / 1000f
                val relIdx = (index - scrollOffset).coerceIn(-clampRange, clampRange)

                val scale by animateFloatAsState(
                    targetValue = when {
                        abs(relIdx) <= 0.5f -> 0.8f
                        abs(relIdx) <= clampRange -> lerp(
                            0.8f,
                            0.55f,
                            (abs(relIdx) - 0.5f) / (clampRange - 0.5f)
                        )
                        else -> 0.55f
                    },
                    animationSpec = tween(smallDuration, easing = FastOutSlowInEasing),
                    label = "scaleAnimation"
                )

                val alphaFactor by animateFloatAsState(
                    targetValue = when {
                        abs(relIdx) <= 0.5f -> 1f
                        abs(relIdx) <= clampRange -> lerp(
                            1f,
                            0f,
                            (abs(relIdx) - 0.5f) / (clampRange - 0.5f)
                        )
                        else -> 0f
                    },
                    animationSpec = tween(smallDuration, easing = FastOutSlowInEasing),
                    label = "alphaAnimation"
                )

                val frontCardTranslation by animateFloatAsState(
                    targetValue = lerp(0f, maxTranslation, (relIdx / 2).coerceIn(0f, 1f)),
                    animationSpec = tween(
                        durationMillis = largeEnterDuration,
                        easing = FastOutSlowInEasing
                    ),
                    label = "translationAnimation"
                )

                Card(
                    isFirst = isFirstCard,
                    modifier = Modifier
                        .height(cardHeight)
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            alpha = alphaFactor
                            rotationX = rotX
                            translationY = frontCardTranslation
                            cameraDistance = 32f * density
                        },
                    frontSide = {
                        if (cardContent != null) {
                            cardContent(item, isFirstCard, primaryColor)
                        } else {
                            IdleView(
                                amount = item.balance,
                                tokenName = item.symbol,
                                fiatAmount = item.fiatBalance,
                                icon = item.logoUrl.orEmpty(),
                                navigateToSend = { onSendClick(item.id) },
                                enableSend = item.balance > 0,
                                primaryColor = primaryColor,
                            )
                        }
                    },
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor
                )
            }
        }

        if (showTopGradient) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(dgenBlack, Color.Transparent)
                        )
                    )
            )
        }
    }
}

/**
 * Linear interpolation between [start] and [stop] by [fraction].
 */
private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}

// region Previews

@Preview(showBackground = true, backgroundColor = 0xFF050505, widthDp = 400, heightDp = 800)
@Composable
private fun TokenCardCarouselPreview() {
    val sampleAssets = listOf(
        TokenCarouselItem(id = "eth", symbol = "ETH", balance = 1.5, fiatBalance = 4500.0),
        TokenCarouselItem(id = "usdc", symbol = "USDC", balance = 250.0, fiatBalance = 250.0),
        TokenCarouselItem(id = "degen", symbol = "DEGEN", balance = 100000.0, fiatBalance = 125.0),
        TokenCarouselItem(id = "matic", symbol = "MATIC", balance = 500.0, fiatBalance = 350.0),
    )

    TokenCardCarousel(
        assets = sampleAssets,
        onSendClick = {},
        primaryColor = dgenTurqoise,
        secondaryColor = dgenOcean,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505, widthDp = 400, heightDp = 800)
@Composable
private fun TokenCardCarouselEmptyPreview() {
    TokenCardCarousel(
        assets = emptyList(),
        onSendClick = {},
        primaryColor = dgenTurqoise,
        secondaryColor = dgenOcean,
    )
}

@Preview(
    device = "spec:width=720px,height=720px,dpi=240",
    name = "DDevice",
    showBackground = true,
    backgroundColor = 0xFF050505
)
@Composable
private fun TokenCardCarouselDDevicePreview() {
    val sampleAssets = listOf(
        TokenCarouselItem(id = "eth", symbol = "ETH", balance = 1.5, fiatBalance = 4500.0),
        TokenCarouselItem(id = "usdc", symbol = "USDC", balance = 0.0, fiatBalance = 0.0),
        TokenCarouselItem(id = "degen", symbol = "DEGEN", balance = 100000.0, fiatBalance = 125.0),
    )

    TokenCardCarousel(
        assets = sampleAssets,
        onSendClick = {},
        primaryColor = dgenTurqoise,
        secondaryColor = dgenOcean,
    )
}

// endregion
