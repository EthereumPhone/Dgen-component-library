package com.example.dgenlibrary.ui.token

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dgenlibrary.R

/**
 * Cached logo source resolution to avoid repeated lookups.
 */
private sealed class LogoSource {
    data class Url(val url: String) : LogoSource()
    data class LocalResource(val resourceId: Int) : LogoSource()
    data object Placeholder : LogoSource()
}

/**
 * Gets a fallback logo resource for known tokens.
 */
private fun getFallbackLogo(symbol: String): Int? {
    return when (symbol.uppercase()) {
        "ETH", "WETH" -> R.drawable.mainnet
        "USDC" -> R.drawable.usdc
        "USDT" -> R.drawable.usdt
        "DAI" -> R.drawable.dai
        else -> null
    }
}

/**
 * A composable that displays a token logo with URL loading and fallback support.
 *
 * Priority order:
 * 1. Remote image URL (if provided and not failed)
 * 2. Known token fallback (ETH, USDC, USDT, DAI)
 * 3. Placeholder with first letter of symbol
 *
 * @param symbol Token symbol (e.g., "ETH", "USDC")
 * @param imageUrl Remote image URL for the token logo, or null
 * @param tokenAddress Unique identifier for cache keying
 * @param primaryColor Primary color for the placeholder
 * @param secondaryColor Secondary color for the placeholder
 * @param size Diameter of the logo
 * @param modifier Modifier to be applied
 */
@Composable
fun TokenLogo(
    symbol: String,
    imageUrl: String? = null,
    tokenAddress: String = "",
    primaryColor: Color,
    secondaryColor: Color,
    size: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var imageLoadError by remember(tokenAddress) { mutableStateOf(false) }

    val logoSource = remember(tokenAddress, imageUrl, imageLoadError) {
        when {
            !imageUrl.isNullOrEmpty() && !imageLoadError -> {
                LogoSource.Url(imageUrl)
            }
            else -> {
                val fallback = getFallbackLogo(symbol)
                if (fallback != null) {
                    LogoSource.LocalResource(fallback)
                } else {
                    LogoSource.Placeholder
                }
            }
        }
    }

    when (logoSource) {
        is LogoSource.Url -> {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(logoSource.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "$symbol logo",
                modifier = modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                onError = {
                    if (!imageLoadError) {
                        Log.w("TokenLogo", "Failed to load image for $symbol")
                    }
                    imageLoadError = true
                }
            )
        }

        is LogoSource.LocalResource -> {
            Image(
                painter = painterResource(logoSource.resourceId),
                contentDescription = "$symbol logo",
                modifier = modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        is LogoSource.Placeholder -> {
            TokenLogoPlaceholder(
                primaryColor = primaryColor,
                secondaryColor = secondaryColor,
                symbol = symbol,
                size = size,
                modifier = modifier
            )
        }
    }
}
