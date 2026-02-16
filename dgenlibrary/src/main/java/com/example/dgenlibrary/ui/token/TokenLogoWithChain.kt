package com.example.dgenlibrary.ui.token

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen

/**
 * Token logo composable that can display a chain overlay when needed.
 * This is a genericized version that does not depend on any app-specific model.
 *
 * @param imageUrl Remote image URL for the token logo, or null
 * @param symbol Token symbol (used for placeholder text)
 * @param name Token name (used for content description)
 * @param chainId Chain ID for chain overlay icon selection
 * @param size The size of the main logo
 * @param primaryColor Primary color for text and borders
 * @param secondaryColor Secondary color for backgrounds
 * @param showChainOverlay Whether to show the chain overlay
 * @param isNativeToken Whether this is a native token (e.g., ETH) to show chain icon directly
 * @param modifier Modifier for the composable
 */
@Composable
fun TokenLogoWithChain(
    imageUrl: String? = null,
    symbol: String,
    name: String = symbol,
    chainId: Int = 0,
    size: Dp = 32.dp,
    primaryColor: Color,
    secondaryColor: Color,
    showChainOverlay: Boolean = false,
    isNativeToken: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (isNativeToken) {
            Icon(
                painter = painterResource(id = R.drawable.mainnet),
                contentDescription = "ETH",
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(3.dp)),
                tint = Color.Unspecified
            )
        } else if (!imageUrl.isNullOrEmpty() && imageUrl != "https://example.com/token-image.png") {
            val context = LocalContext.current
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = name,
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(3.dp)),
                contentScale = ContentScale.Crop,
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(secondaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = symbol.take(2).uppercase(),
                            fontSize = (size.value * 0.4).sp,
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                },
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(secondaryColor.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = symbol.take(2).uppercase(),
                            fontSize = (size.value * 0.4).sp,
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor.copy(alpha = 0.5f),
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(3.dp))
                    .background(secondaryColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = symbol.take(2).uppercase(),
                    fontSize = (size.value * 0.4).sp,
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        color = primaryColor,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        if (showChainOverlay) {
            val overlaySize = size * 0.3f
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(overlaySize)
                    .graphicsLayer {
                        translationY = 2f
                        translationX = 2f
                    },
                contentAlignment = Alignment.Center
            ) {
                when (chainId) {
                    8453, 84532 -> {
                        Image(
                            painter = painterResource(id = R.drawable.base_square),
                            modifier = Modifier
                                .border(1.dp, secondaryColor, RoundedCornerShape(3.dp))
                                .clip(RoundedCornerShape(3.dp)),
                            contentDescription = "Base Chain",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    10 -> {
                        Image(
                            painter = painterResource(id = R.drawable.optimism),
                            modifier = Modifier
                                .border(1.dp, secondaryColor, RoundedCornerShape(3.dp))
                                .clip(RoundedCornerShape(3.dp)),
                            contentDescription = "Optimism Chain",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    1 -> {
                        Image(
                            painter = painterResource(id = R.drawable.mainnet),
                            modifier = Modifier
                                .border(1.dp, secondaryColor, RoundedCornerShape(3.dp))
                                .clip(RoundedCornerShape(3.dp)),
                            contentDescription = "Mainnet",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    42161 -> {
                        Image(
                            painter = painterResource(id = R.drawable.arbitrum),
                            contentDescription = "Arbitrum Chain",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    137 -> {
                        Image(
                            painter = painterResource(id = R.drawable.polygon),
                            modifier = Modifier
                                .border(1.dp, secondaryColor, RoundedCornerShape(3.dp))
                                .clip(RoundedCornerShape(3.dp)),
                            contentDescription = "Polygon",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    7777777 -> {
                        Image(
                            painter = painterResource(id = R.drawable.zorb),
                            modifier = Modifier
                                .border(1.dp, secondaryColor, CircleShape)
                                .clip(CircleShape),
                            contentDescription = "Zora",
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(secondaryColor, RoundedCornerShape(3.dp))
                                .border(1.dp, primaryColor, RoundedCornerShape(3.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "?",
                                fontSize = 8.sp,
                                style = TextStyle(
                                    fontFamily = SpaceMono,
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenLogoWithChainPreview() {
    TokenLogoWithChain(
        symbol = "DEGEN",
        chainId = 8453,
        size = 48.dp,
        primaryColor = dgenGreen,
        secondaryColor = dgenBlack,
        showChainOverlay = true
    )
}
