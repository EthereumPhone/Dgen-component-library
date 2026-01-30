package com.example.dgenlibrary.ui.token

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.TokenInfo
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.neonOpacity

/**
 * Data class representing token display information.
 * Use this to pass token data to the TokenCard component.
 */
data class TokenDisplayData(
    val name: String,
    val symbol: String,
    val marketCap: String? = null,
    val priceChange: String? = null,
    val priceChangeColor: Color = dgenWhite,
    val isTrending: Boolean = false
)

/**
 * A card component for displaying token information.
 * Features token name, symbol, market cap, price change, and optional action button.
 *
 * @param tokenData Token display data
 * @param primaryColor Primary color for borders and accents
 * @param secondaryColor Secondary color (used for action button)
 * @param onClick Callback when the card is clicked
 * @param onLongPress Callback when the card is long-pressed
 * @param leadingContent Optional composable for leading content (e.g., token logo)
 * @param trailingContent Optional composable for trailing content (e.g., action button)
 * @param modifier Modifier to be applied to the card
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TokenCard(
    tokenData: TokenDisplayData,
    primaryColor: Color,
    secondaryColor: Color = dgenBlack,
    onClick: () -> Unit,
    onLongPress: () -> Unit = {},
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val haptics = LocalHapticFeedback.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongPress()
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = primaryColor.copy(alpha = 0.05f)
        ),
        border = BorderStroke(1.dp, primaryColor.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(3.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Leading content (e.g., token logo)
                    leadingContent?.invoke()

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Token Name
                            Text(
                                text = tokenData.name,
                                style = TextStyle(
                                    fontFamily = PitagonsSans,
                                    color = dgenWhite,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    letterSpacing = 1.sp
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )

                            // Trending badge
                            if (tokenData.isTrending) {
                                Text(
                                    text = "HOT",
                                    style = TextStyle(
                                        fontFamily = SpaceMono,
                                        color = dgenWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        letterSpacing = 1.sp
                                    ),
                                    modifier = Modifier
                                        .background(
                                            dgenRed,
                                            RoundedCornerShape(0.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 0.dp)
                                )
                            }
                        }

                        // Symbol
                        Text(
                            text = "$" + tokenData.symbol,
                            style = TextStyle(
                                fontFamily = PitagonsSans,
                                color = dgenWhite.copy(alpha = neonOpacity),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }

                // Token info row (market cap, price change)
                Row(
                    Modifier.padding(end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    tokenData.marketCap?.let { marketCap ->
                        TokenInfo(
                            title = "MCAP",
                            description = marketCap,
                            primaryColor = primaryColor,
                            size = 100.dp
                        )
                    }

                    tokenData.priceChange?.let { priceChange ->
                        TokenInfo(
                            title = "24HΔ",
                            description = priceChange,
                            primaryColor = primaryColor,
                            descriptionColor = tokenData.priceChangeColor
                        )
                    }
                }
            }

            // Trailing content (e.g., action button)
            trailingContent?.let { content ->
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}

/**
 * Placeholder card displayed while token data is loading.
 *
 * @param primaryColor Primary color for the placeholder background
 */
@Composable
fun TokenCardPlaceholder(
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = primaryColor.copy(alpha = 0.03f),
                shape = RoundedCornerShape(3.dp)
            )
    )
}

@Preview(widthDp = 480, showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenCardPreview() {
    TokenCard(
        tokenData = TokenDisplayData(
            name = "Degen",
            symbol = "DEGEN",
            marketCap = "$1.25M",
            priceChange = "+15.5%",
            priceChangeColor = dgenGreen
        ),
        primaryColor = dgenGreen,
        onClick = {},
        onLongPress = {}
    )
}

@Preview(widthDp = 480, showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenCardNegativePreview() {
    TokenCard(
        tokenData = TokenDisplayData(
            name = "Bear Market",
            symbol = "BEAR",
            marketCap = "$850K",
            priceChange = "-8.3%",
            priceChangeColor = dgenRed
        ),
        primaryColor = dgenRed,
        onClick = {},
        onLongPress = {}
    )
}

@Preview(widthDp = 480, showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenCardTrendingPreview() {
    TokenCard(
        tokenData = TokenDisplayData(
            name = "Hot Token",
            symbol = "HOT",
            marketCap = "$5.5M",
            priceChange = "+125.8%",
            priceChangeColor = dgenGreen,
            isTrending = true
        ),
        primaryColor = dgenGreen,
        onClick = {},
        onLongPress = {}
    )
}

@Preview(widthDp = 480, showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenCardPlaceholderPreview() {
    TokenCardPlaceholder(primaryColor = dgenGreen)
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun TokenCardPreviewDDevice() {
    TokenCard(
        tokenData = TokenDisplayData(
            name = "Degen",
            symbol = "DEGEN",
            marketCap = "$1.25M",
            priceChange = "+15.5%",
            priceChangeColor = dgenGreen
        ),
        primaryColor = dgenGreen,
        onClick = {},
        onLongPress = {}
    )
}