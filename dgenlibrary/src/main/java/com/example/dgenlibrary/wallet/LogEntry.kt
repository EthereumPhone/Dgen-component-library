package com.example.dgenlibrary.wallet

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.util.abbreviateNumber
import com.example.dgenlibrary.util.formatAddress
import com.example.dgenlibrary.util.formatWithSuffix
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.util.TokenLogoFallback
import com.example.dgenlibrary.ui.theme.dgenBlack
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Maps chain ID to the appropriate block explorer URL
 */
private fun getBlockExplorerUrl(chainId: Int, txHash: String): String {
    return "https://blockscan.com/tx/$txHash"
}

/**
 * Transaction log entry composable.
 * Displays a single transaction with token logo, amount, direction (TO/FROM), and address.
 *
 * @param logoUrl Optional URL for the token logo
 * @param chainId The chain ID of the transaction
 * @param from The sender address
 * @param to The receiver address
 * @param asset The token symbol (e.g. "ETH", "USDC")
 * @param value The transaction value as a string
 * @param userSent Whether the current user sent this transaction
 * @param txHash The transaction hash
 * @param primaryColor The accent color for directional text
 * @param onNavigateToDetail Callback when the entry is tapped, receives the txHash
 */
@Composable
fun LogEntry(
    logoUrl: String = "",
    chainId: Int,
    from: String,
    to: String,
    asset: String,
    value: String,
    userSent: Boolean,
    txHash: String,
    primaryColor: Color,
    onNavigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val decimalFormat = DecimalFormat("0.00").apply {
        decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    }

    //formatting
    val fromValue = if(from.takeLast(4) == ".eth"){
        from
    } else {
        formatAddress(from)
    }

    val toValue = if(to.takeLast(4) == ".eth"){
        to
    } else {
        formatAddress(to)
    }

    // Check if we should use a network logo
    val networkLogoResource = getNetworkLogoResource(chainId, asset)

    // Check for fallback logo for consistent display across screens
    val fallbackLogo = TokenLogoFallback.getFallbackLogo(asset)

    // Determine the effective logo URL: prefer fallback for consistency
    val effectiveLogoUrl = when {
        // If we have a fallback URL, use it for consistency
        fallbackLogo is TokenLogoFallback.LogoSource.Url -> fallbackLogo.url
        // Otherwise use the provided logo URL
        logoUrl.isNotEmpty() -> logoUrl
        else -> ""
    }

    Box(
        Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.clickable {
                onNavigateToDetail(txHash)
            }
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            )
            {

                when {
                    // First priority: Use network logo for native tokens
                    networkLogoResource != null -> {
                        // Don't clip base_square to a circle, it should maintain its square shape with rounded corners
                        val modifier = if (networkLogoResource == R.drawable.base_square) {
                            Modifier.size(24.dp)
                        } else {
                            Modifier.size(24.dp).clip(CircleShape)
                        }
                        Image(
                            modifier = modifier,
                            painter = painterResource(networkLogoResource),
                            contentDescription = "$asset on chain $chainId"
                        )
                    }
                    // Second priority: Use provided logo URL or fallback URL
                    effectiveLogoUrl.isNotEmpty() -> {
                        AsyncImage(
                            modifier = Modifier.size(24.dp).clip(CircleShape),
                            model = effectiveLogoUrl,
                            contentDescription = "Token logo",
                            placeholder = painterResource(R.drawable.placeholer_icon_5),
                            error = painterResource(R.drawable.placeholer_icon_5)
                        )
                    }
                    // Third priority: Check for local resource fallback
                    fallbackLogo is TokenLogoFallback.LogoSource.LocalResource -> {
                        Image(
                            modifier = Modifier.size(24.dp).clip(CircleShape),
                            painter = painterResource(fallbackLogo.resourceId),
                            contentDescription = "Token logo"
                        )
                    }
                    // Fallback: Use placeholder
                    else -> {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.placeholer_icon_5),
                            contentDescription = "Placeholder"
                        )
                    }
                }

                //if the tx was sending something
                if (userSent) {

                    Text(
                        buildAnnotatedString {
                            append("${abbreviateNumber(value.toDouble())} $asset")

                            withStyle(
                                style = SpanStyle(
                                    fontFamily = SpaceMono,
                                    color = primaryColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.None
                                )
                            ) {
                                append(" TO ")
                            }

                            append(toValue)
                        },
                        style = TextStyle(
                            fontFamily = PitagonsSans,
                            color = dgenWhite,
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp,
                            lineHeight = 22.sp,
                            textDecoration = TextDecoration.None
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                else{
                    Text(

                        buildAnnotatedString {
                            append("${value.toDouble().formatWithSuffix()} $asset")

                            withStyle(style = SpanStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                textDecoration = TextDecoration.None
                            )
                            ) {
                                append(" FROM ")
                            }

                            append(fromValue)


                        },
                        style = TextStyle(
                            fontFamily = PitagonsSans,
                            color = dgenWhite,
                            fontWeight = FontWeight.Medium,
                            fontSize = 22.sp,
                            lineHeight = 22.sp,
                            textDecoration = TextDecoration.None,

                            ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }

        Box(
            modifier = Modifier
                .height(28.dp)
                .width(56.dp) // Width of the fade gradient
                .align(Alignment.CenterEnd)
                .drawWithContent {
                    drawContent()
                    // Draw gradient from transparent to black
                    val gradientWidth = size.width
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                dgenBlack,
                                dgenBlack
                            ),
                            startX = 0f,
                            endX = gradientWidth
                        ),
                        size = size
                    )
                }
        )
    }
}

enum class TxType{
    SENT,
    RECEIVED,
    SWAPED,
    NFT
}

/**
 * Gets the appropriate network logo resource for native tokens (ETH/MATIC)
 * based on the chain ID
 */
@Composable
private fun getNetworkLogoResource(chainId: Int, asset: String): Int? {
    val assetUppercase = asset.uppercase()

    // Check if it's a native token
    val isNativeToken = when (chainId) {
        137 -> assetUppercase == "MATIC" // Polygon
        else -> assetUppercase == "ETH"
    }

    if (!isNativeToken) return null

    // Return the appropriate network logo
    return when (chainId) {
        1, 5 -> R.drawable.mainnet // Ethereum mainnet
        11155111 -> R.drawable.mainnet // Sepolia (using mainnet logo)
        137 -> R.drawable.polygon // Polygon
        10 -> R.drawable.optimism_logo // Optimism
        42161 -> R.drawable.arbitrum_logo // Arbitrum
        8453 -> R.drawable.base_square // Base
        7777777 -> R.drawable.zorb
        else -> null
    }
}

data class TxEntry (
    val fromAmount: Double = 0.0,
    val toAmount: Double = 0.0,
    val fromTokenName: String = "",
    val toTokenName: String = "",
    val fromAddress: String = "",
    val toAddress: String = "",
    val txType : TxType = TxType.SENT
)

@Preview
@Composable
fun PreviewLogEntity() {
    LogEntry(
        chainId = 1,
        from = "nceornea.et",
        to = "nceornea.eth",
        asset = "ETH",
        value = "123",
        userSent = true,
        txHash = "",
        primaryColor = Color.Red,
        onNavigateToDetail = {}
    )
}
