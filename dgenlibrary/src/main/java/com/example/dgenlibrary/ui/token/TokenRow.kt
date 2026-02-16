package com.example.dgenlibrary.ui.token

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.body1_fontSize
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.label_fontSize
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

/**
 * Abbreviates a number with suffix (K, M, B).
 */
fun abbreviateNumber(value: Double): String {
    val suffixes = arrayOf("", "K", "M", "B")
    var num = value
    var index = 0

    while (abs(num) >= 1000 && index < suffixes.size - 1) {
        num /= 1000
        index++
    }

    val symbols = DecimalFormatSymbols(Locale.US)
    val df = DecimalFormat("#,##0.##", symbols)
    val formatted = df.format(num)

    if (formatted == "0" && num != 0.0) {
        val plain = BigDecimal.valueOf(num)
            .stripTrailingZeros()
            .toPlainString()
        return "$plain${suffixes[index]}"
    }

    return "$formatted${suffixes[index]}"
}

/**
 * A row component displaying a token logo, formatted amount with symbol,
 * and an optional claimed status badge.
 *
 * @param symbol Token symbol (e.g., "ETH")
 * @param amount Display amount (already formatted, e.g., "1.5K")
 * @param primaryColor Primary color for accents and claimed badge
 * @param secondaryColor Secondary color for logo placeholder
 * @param claimed Whether to show a "[CLAIMED]" badge
 * @param leadingContent Optional composable for leading content (e.g., token logo)
 * @param modifier Modifier to be applied
 */
@Composable
fun TokenRow(
    symbol: String,
    amount: String,
    primaryColor: Color,
    secondaryColor: Color,
    claimed: Boolean = false,
    leadingContent: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.weight(1f, fill = false)
        ) {
            if (leadingContent != null) {
                leadingContent()
            } else {
                TokenLogoPlaceholder(
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    symbol = symbol
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                "$amount $symbol",
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = dgenWhite,
                    fontWeight = FontWeight.Medium,
                    fontSize = body1_fontSize,
                    lineHeight = body1_fontSize,
                    textDecoration = TextDecoration.None
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            buildAnnotatedString {
                if (claimed) {
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontFamily = SpaceMono,
                            color = primaryColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = label_fontSize,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        )
                    ) {
                        append("[CLAIMED]")
                    }
                }
            },
            style = TextStyle(
                fontFamily = SpaceMono,
                color = dgenWhite,
                fontWeight = FontWeight.SemiBold,
                fontSize = label_fontSize,
                lineHeight = label_fontSize,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenRowPreview() {
    TokenRow(
        symbol = "ETH",
        amount = "1.5K",
        primaryColor = dgenGreen,
        secondaryColor = Color(0xFF081B03),
        claimed = false
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenRowClaimedPreview() {
    TokenRow(
        symbol = "DEGEN",
        amount = "250",
        primaryColor = dgenGreen,
        secondaryColor = Color(0xFF081B03),
        claimed = true
    )
}
