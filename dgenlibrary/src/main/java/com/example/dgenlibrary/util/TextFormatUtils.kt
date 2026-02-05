package com.example.dgenlibrary.util

import java.text.Normalizer

/**
 * Utility functions for text formatting and input filtering
 */
object TextFormatUtils {

    /**
     * Comprehensive ENS label input filter according to ENS specifications
     * Filters input for a single ENS label (part between dots)
     */
    fun filterEnsInput(input: String): String {
        if (input.isEmpty()) return input

        // Step 1: Case-fold (convert to lowercase)
        val normalized = input.lowercase()

        // Step 2: NFC normalize
        val nfcNormalized = Normalizer.normalize(normalized, Normalizer.Form.NFC)

        // Step 3: Character by character validation
        val result = StringBuilder()
        var i = 0
        var hasSeenNonUnderscore = false
        var lastWasFencedChar = false

        while (i < nfcNormalized.length) {
            val char = nfcNormalized[i]
            val codePoint = nfcNormalized.codePointAt(i)

            // Check for valid characters
            when {
                // ASCII lowercase letters
                char in 'a'..'z' -> {
                    result.append(char)
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                // ASCII digits
                char in '0'..'9' -> {
                    result.append(char)
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                // Hyphen-minus (but not positions 3-4 for pure ASCII to avoid xn--)
                char == '-' -> {
                    // Check if this would create xn-- pattern (positions 3-4 both being -)
                    if (result.length == 2 && result.toString() == "xn" &&
                        i + 1 < nfcNormalized.length && nfcNormalized[i + 1] == '-'
                    ) {
                        // Skip this hyphen to prevent xn-- pattern
                    } else {
                        result.append(char)
                        hasSeenNonUnderscore = true
                        lastWasFencedChar = false
                    }
                }

                // Dot (period) - essential for ENS domains
                char == '.' -> {
                    result.append(char)
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                // Dollar sign
                char == '$' -> {
                    result.append(char)
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                // Underscore (only as leading characters)
                char == '_' -> {
                    if (!hasSeenNonUnderscore) {
                        result.append(char)
                        lastWasFencedChar = false
                    }
                    // Otherwise skip - underscores only allowed at the beginning
                }

                // Greek capital xi (Ethereum symbol)
                char == 'Ξ' || char == 'ξ' -> {
                    result.append('ξ') // Always lowercase
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                // Fenced characters (punctuation that can appear inside but not first/last)
                char == '\u2019' || char == '\u30FB' -> { // RIGHT SINGLE QUOTATION MARK, KATAKANA MIDDLE DOT
                    if (result.isNotEmpty() && !lastWasFencedChar && i < nfcNormalized.length - 1) {
                        result.append(char)
                        hasSeenNonUnderscore = true
                        lastWasFencedChar = true
                    } else {
                        lastWasFencedChar = false
                    }
                }

                // Unicode letters and digits
                Character.isLetter(codePoint) || Character.getType(codePoint) == Character.DECIMAL_DIGIT_NUMBER.toInt() -> {
                    // Note: Full ENS compliance requires script consistency checking
                    // (all Unicode letters in a label must be from the same script)
                    // This simplified implementation allows mixing, which may not be fully compliant
                    result.append(char)
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                // Emoji sequences - simplified check for common emoji ranges
                // This is a basic implementation - full emoji validation is very complex
                codePoint in 0x1F600..0x1F64F || // Emoticons
                        codePoint in 0x1F300..0x1F5FF || // Misc Symbols and Pictographs
                        codePoint in 0x1F680..0x1F6FF || // Transport and Map
                        codePoint in 0x1F1E6..0x1F1FF || // Regional indicators (flags)
                        codePoint in 0x2600..0x26FF ||   // Misc symbols
                        codePoint in 0x2700..0x27BF -> { // Dingbats
                    result.append(char)
                    hasSeenNonUnderscore = true
                    lastWasFencedChar = false
                }

                else -> {
                    // Skip invalid characters
                    lastWasFencedChar = false
                }
            }

            // Move to next character (handle surrogate pairs)
            i += if (Character.isSupplementaryCodePoint(codePoint)) 2 else 1
        }

        // Step 4: Final validation - remove trailing fenced characters
        var finalResult = result.toString()
        while (finalResult.isNotEmpty() &&
            (finalResult.last() == '\u2019' || finalResult.last() == '\u30FB')
        ) {
            finalResult = finalResult.dropLast(1)
        }

        return finalResult
    }
}
