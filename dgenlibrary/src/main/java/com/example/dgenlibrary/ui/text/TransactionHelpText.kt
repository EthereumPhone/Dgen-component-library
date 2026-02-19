package com.example.dgenlibrary.ui.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenRed

/**
 * Default help text rules for common transaction errors.
 * Each pair maps a keyword (matched case-insensitively) to a user-friendly help message.
 */
val defaultTransactionHelpRules = listOf(
    "gas" to "Add ETH to your wallet to pay for gas fees",
    "nonce" to "Wait for pending transactions to complete",
    "signature" to "Please try signing the transaction again",
    "expired" to "Transaction took too long. Please try again",
    "insufficient funds" to "Add more funds to your wallet",
    "paymaster" to "Sponsorship service unavailable. Try again later",
    "account not deployed" to "Your account needs to be activated first",
    "throttled" to "Too many requests. Please wait and try again",
    "slippage" to "Price moved too much. Try increasing slippage tolerance",
    "liquidity" to "Not enough liquidity for this swap",
    "cross-chain" to "Cross-chain swaps require bridge integration"
)

/**
 * Resolves an error message to a user-friendly help text by matching against known patterns.
 *
 * @param errorMessage The error message to match against
 * @param additionalRules Extra keyword-to-help-text mappings appended after defaults
 * @return The first matching help text, or null if no match found
 */
fun getTransactionHelpText(
    errorMessage: String?,
    additionalRules: List<Pair<String, String>> = emptyList()
): String? {
    if (errorMessage.isNullOrEmpty()) return null
    val allRules = defaultTransactionHelpRules + additionalRules
    return allRules.firstOrNull { (keyword, _) ->
        errorMessage.contains(keyword, ignoreCase = true)
    }?.second
}

/**
 * Displays contextual help text based on a transaction error message.
 * Matches the error against known patterns and shows a human-readable suggestion
 * styled with the dgen design system.
 *
 * @param errorMessage The error message to match against
 * @param primaryColor The primary theme color for text tinting
 * @param alpha Opacity multiplier for the text (applied as alpha * 0.7f)
 * @param additionalRules Extra keyword-to-help-text rules beyond the built-in defaults
 * @param modifier Modifier for the Text composable
 */
@Composable
fun TransactionHelpText(
    errorMessage: String?,
    primaryColor: Color,
    alpha: Float = 1f,
    additionalRules: List<Pair<String, String>> = emptyList(),
    modifier: Modifier = Modifier
) {
    val helpText = getTransactionHelpText(errorMessage, additionalRules)
    helpText?.let {
        Text(
            text = it,
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = primaryColor.copy(alpha = alpha * 0.7f),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None,
                textAlign = TextAlign.Center
            ),
            modifier = modifier.padding(horizontal = 24.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TransactionHelpTextPreview() {
    TransactionHelpText(
        errorMessage = "insufficient gas for transaction",
        primaryColor = dgenGreen
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TransactionHelpTextFailurePreview() {
    TransactionHelpText(
        errorMessage = "slippage tolerance exceeded",
        primaryColor = dgenRed,
        additionalRules = listOf(
            "slippage" to "Price moved too much. Try increasing slippage tolerance"
        )
    )
}
