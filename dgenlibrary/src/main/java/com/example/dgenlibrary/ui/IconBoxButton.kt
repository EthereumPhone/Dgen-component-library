package com.example.dgenlibrary.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen

/**
 * A versatile square icon button with border and optional fill.
 * Combines the functionality of FilterButton and MyTokensButton into a single reusable component.
 *
 * @param icon The icon to display
 * @param contentDescription Accessibility description for the icon
 * @param primaryColor Primary color for the border and icon (or background when active)
 * @param secondaryColor Color for the icon when the button is active
 * @param isActive Whether the button is in an active/selected state
 * @param onClick Callback when the button is clicked
 * @param modifier Modifier to be applied to the button
 * @param size Size of the button (default 40.dp)
 * @param iconSize Size of the icon (default 20.dp)
 * @param cornerRadius Corner radius of the button (default 3.dp)
 */
@Composable
fun IconBoxButton(
    icon: ImageVector,
    contentDescription: String,
    primaryColor: Color,
    secondaryColor: Color = dgenBlack,
    isActive: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    iconSize: Dp = 20.dp,
    cornerRadius: Dp = 3.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(if (isActive) primaryColor else Color.Transparent)
            .border(
                BorderStroke(1.dp, primaryColor),
                RoundedCornerShape(cornerRadius)
            )
            .clickable { onClick() }
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isActive) secondaryColor else primaryColor,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * Preset: Filter button with filter icon.
 * Shows filled background when filters are active.
 */
@Composable
fun FilterButton(
    hasActiveFilters: Boolean,
    primaryColor: Color,
    secondaryColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconBoxButton(
        icon = Icons.Default.FilterAlt,
        contentDescription = "Filter",
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        isActive = hasActiveFilters,
        onClick = onClick,
        modifier = modifier
    )
}

/**
 * Preset: Menu button with hamburger menu icon.
 * Used for accessing user's tokens or menu options.
 */
@Composable
fun MenuButton(
    primaryColor: Color,
    secondaryColor: Color = dgenBlack,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconBoxButton(
        icon = Icons.Default.Menu,
        contentDescription = "Menu",
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        isActive = false,
        onClick = onClick,
        modifier = modifier
    )
}

/**
 * Preset: Search button with search icon.
 */
@Composable
fun SearchButton(
    primaryColor: Color,
    secondaryColor: Color = dgenBlack,
    isActive: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconBoxButton(
        icon = Icons.Default.Search,
        contentDescription = "Search",
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        isActive = isActive,
        onClick = onClick,
        modifier = modifier
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun IconBoxButtonPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        IconBoxButton(
            icon = Icons.Default.Settings,
            contentDescription = "Settings",
            primaryColor = dgenGreen,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun IconBoxButtonActivePreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        IconBoxButton(
            icon = Icons.Default.FilterAlt,
            contentDescription = "Filter",
            primaryColor = dgenGreen,
            secondaryColor = dgenBlack,
            isActive = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun FilterButtonInactivePreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        FilterButton(
            hasActiveFilters = false,
            primaryColor = dgenGreen,
            secondaryColor = dgenBlack,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun FilterButtonActivePreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        FilterButton(
            hasActiveFilters = true,
            primaryColor = dgenGreen,
            secondaryColor = dgenBlack,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun MenuButtonPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        MenuButton(
            primaryColor = dgenGreen,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun SearchButtonPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        SearchButton(
            primaryColor = dgenGreen,
            onClick = {}
        )
    }
}

