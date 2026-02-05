package com.example.dgenlibrary.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utility functions for Ethereum address and ENS validation/formatting
 */
object EthereumUtils {

    /**
     * Validates Ethereum address input to only allow valid hex characters and proper format
     * @param input The raw input string
     * @return A properly formatted Ethereum address string (with 0x prefix, max 40 hex chars)
     */
    fun validateEthereumAddress(input: String): String {
        if (input.isEmpty()) return input

        // Normalize prefix casing
        val normalized = if (input.startsWith("0X")) "0x" + input.drop(2) else input

        return when {
            normalized.startsWith("0x") -> {
                val hexPart = normalized.drop(2).filter { char ->
                    char.isDigit() || char.lowercaseChar() in 'a'..'f'
                }
                if (hexPart.isEmpty()) "" else "0x" + hexPart.take(40)
            }
            normalized.all { char -> char.isDigit() || char.lowercaseChar() in 'a'..'f' } -> {
                val hexPart = normalized.filter { char -> char.isDigit() || char.lowercaseChar() in 'a'..'f' }
                if (hexPart.isEmpty()) "" else "0x" + hexPart.take(40)
            }
            else -> {
                normalized.filter { char -> char.isDigit() || char.lowercaseChar() in 'a'..'f' }
            }
        }
    }

    /**
     * Checks if an Ethereum address is valid (proper format and length)
     * @param address The address to validate
     * @return true if the address is a valid Ethereum address (0x followed by 40 hex characters)
     */
    fun isValidEthereumAddress(address: String): Boolean {
        return address.matches(Regex("^0x[a-fA-F0-9]{40}$"))
    }

    /**
     * Checks if an ENS domain is valid
     * Must end with .eth and follow proper ENS naming conventions
     * @param ens The ENS name to validate
     * @return true if the ENS name is valid or empty (optional field)
     */
    fun isValidEnsName(ens: String): Boolean {
        if (ens.isEmpty()) return true // Allow empty (optional field)

        return ens.endsWith(".eth") &&
                ens.length > 4 &&
                !ens.startsWith(".") &&
                !ens.contains("..") &&
                ens.count { it == '.' } >= 1 &&
                ens != ".eth" // Cannot be just ".eth"
    }

    /**
     * Checks if the device has an active internet connection
     * Useful for ENS resolution which requires network access
     * @param context The Android context
     * @return true if the device has a validated internet connection
     */
    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
