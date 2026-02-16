package com.example.dgenlibrary.ui.token

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.R
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.body1_fontSize
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * Maps chain names to their corresponding drawable resources.
 */
private fun getChainDrawable(chainName: String): Int {
    return when {
        chainName.contains("Base", ignoreCase = true) -> R.drawable.base_square
        chainName.contains("Ethereum", ignoreCase = true) -> R.drawable.mainnet
        chainName.contains("Optimism", ignoreCase = true) -> R.drawable.optimism
        chainName.contains("Arbitrum", ignoreCase = true) -> R.drawable.arbitrum
        chainName.contains("Polygon", ignoreCase = true) -> R.drawable.polygon
        else -> R.drawable.placeholer_chain
    }
}

/**
 * A row component displaying a chain icon, chain name, and a chevron indicator.
 *
 * @param modifier Modifier to be applied
 * @param chain Name of the blockchain (e.g., "Base", "Ethereum")
 * @param primaryColor Color for the chevron icon
 * @param onClick Callback when the row is clicked
 */
@Composable
fun ChainRow(
    modifier: Modifier = Modifier,
    chain: String = "Chain",
    primaryColor: Color,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(getChainDrawable(chain)),
            contentDescription = "$chain chain icon"
        )

        Text(
            text = chain.uppercase(),
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = dgenWhite,
                fontWeight = FontWeight.SemiBold,
                fontSize = body1_fontSize,
                lineHeight = body1_fontSize,
                textDecoration = TextDecoration.None
            ),
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(R.drawable.chevron_right),
            tint = primaryColor,
            contentDescription = "Chevron",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun ChainRowPreview() {
    ChainRow(
        chain = "Base",
        primaryColor = dgenGreen,
        onClick = {}
    )
}
