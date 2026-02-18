package com.example.dgenlibrary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack

@Composable
fun ChainButton(
    chainId: Int? = null,
    isSelected: Boolean,
    primaryColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (isSelected) primaryColor else Color.Transparent)
            .border(BorderStroke(1.dp, primaryColor), RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            ChainIcon(chainId = chainId, size = 16.dp)
            Text(
                text = getChainName(chainId),
                style = TextStyle(
                    fontFamily = SpaceMono,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) dgenBlack else primaryColor
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select chain",
                tint = if (isSelected) dgenBlack else primaryColor,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ChainIcon(
    chainId: Int?,
    size: Dp = 24.dp,
    modifier: Modifier = Modifier
) {
    val iconModifier = modifier.size(size)

    when (chainId) {
        1 -> Icon(
            painter = painterResource(id = R.drawable.ethereum_placeholder),
            contentDescription = "Ethereum",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
        10 -> Icon(
            painter = painterResource(id = R.drawable.optimism_logo),
            contentDescription = "Optimism",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
        137 -> Icon(
            painter = painterResource(id = R.drawable.polygon),
            contentDescription = "Polygon",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
        8453 -> Icon(
            painter = painterResource(id = R.drawable.base_square),
            contentDescription = "Base",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
        42161 -> Icon(
            painter = painterResource(id = R.drawable.arbitrum_logo),
            contentDescription = "Arbitrum",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
        7777777 -> Icon(
            painter = painterResource(id = R.drawable.zorb),
            contentDescription = "Zora",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
        else -> Icon(
            painter = painterResource(id = R.drawable.ethereum_placeholder),
            contentDescription = "All Chains",
            modifier = iconModifier,
            tint = Color.Unspecified
        )
    }
}

fun getChainName(chainId: Int?): String {
    return when (chainId) {
        1 -> "ETH"
        10 -> "OP"
        137 -> "POL"
        8453 -> "BASE"
        42161 -> "ARB"
        7777777 -> "ZORA"
        null -> "ALL"
        else -> "ALL"
    }
}