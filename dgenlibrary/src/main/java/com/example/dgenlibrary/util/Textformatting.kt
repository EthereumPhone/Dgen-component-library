package com.example.dgenlibrary.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale
import kotlin.math.abs

fun formatSmart(value: Double): String {
    // If it's exactly zero, just return "0"
    if (value == 0.0) return "0"
    
    // For integer values, show without decimal part
    if (value % 1.0 == 0.0) {
        return value.toInt().toString()
    }
    
    // For small values, ensure we show enough decimal places
    if (value < 0.0001 && value > 0) {
        val bd = BigDecimal(value).setScale(6, RoundingMode.HALF_UP)
        // If the value is still zero after rounding to 6 places, show a minimum value
        if (bd.compareTo(BigDecimal.ZERO) == 0) {
            return "0.000001"
        }
        // Custom format for very small numbers to avoid scientific notation
        val df = DecimalFormat("0.######")
        df.minimumFractionDigits = 6
        return df.format(value)
    }
    
    // For numbers between 0.0001 and 1, show up to 6 decimal places
    if (value < 1) {
        return String.format(Locale.US, "%.6f", value).trimEnd('0').trimEnd('.')
    }
    
    // For regular numbers, show 2 decimal places
    return String.format(Locale.US, "%.2f", value)
}

fun abbreviateNumber(value: Double): String {
    val suffixes = arrayOf("", "K", "M", "B")
    var num = value
    var index = 0

    // scale down by thousands
    while (abs(num) >= 1000 && index < suffixes.size - 1) {
        num /= 1000
        index++
    }

    // US-style formatter, max 2 decimal places
    val symbols = DecimalFormatSymbols(Locale.US)
    val df = DecimalFormat("#,##0.##", symbols)
    val formatted = df.format(num)

    // if it rounded to "0" but was nonzero (e.g. 3.8E-4), fall back to full precision
    if (formatted == "0" && num != 0.0) {
        val plain = BigDecimal.valueOf(num)
            .stripTrailingZeros()
            .toPlainString()
        return "$plain${suffixes[index]}"
    }

    return "$formatted${suffixes[index]}"
}


fun Double.formatWithSuffix(maxDecimals: Int = 4): String {
    val absValue = kotlin.math.abs(this)

    val (divisor, suffix) = when {
        absValue >= 1_000_000_000_000 -> 1_000_000_000_000.0 to "T"
        absValue >= 1_000_000_000     -> 1_000_000_000.0     to "B"
        absValue >= 1_000_000         -> 1_000_000.0         to "M"
        absValue >= 1_000             -> 1_000.0             to "K"
        else                          -> 1.0                 to ""
    }

    val decimals = if (suffix.isNotEmpty()) 2 else maxDecimals
    val scaled = this / divisor
    val bd = BigDecimal.valueOf(scaled)

    // New logic: avoid rounding to 0 if small
    val scaledAndRounded = if (suffix.isNotEmpty()) {
        // With suffix (e.g., "K", "M") – we can safely round to the desired decimals
        bd.setScale(decimals, RoundingMode.HALF_UP).stripTrailingZeros()
    } else {
        // Without suffix (values < 1K): be careful not to round tiny numbers down to zero
        val candidate = bd.setScale(decimals, RoundingMode.HALF_UP)
        if (candidate.compareTo(BigDecimal.ZERO) == 0 && bd.compareTo(BigDecimal.ZERO) != 0) {
            // Rounding wiped out all significant digits – fall back to full precision
            bd.stripTrailingZeros()
        } else {
            candidate.stripTrailingZeros()
        }
    }

    return scaledAndRounded.toPlainString() + suffix
}

fun formatAddress(input: String, visibleChars: Int = 4): String {
    if (input.length <= visibleChars * 2) return input
    return input.take(visibleChars) + "..." + input.takeLast(visibleChars)
}
