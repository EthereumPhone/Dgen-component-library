package com.example.dgenlibrary.ui.headers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * A customizable large top app bar component that collapses on scroll.
 * 
 * This component wraps Material3's LargeTopAppBar with dgen styling defaults.
 * It supports:
 * - Collapsible title with different collapsed/expanded states
 * - Custom navigation icon
 * - Subtitle text (like date/time)
 * - Action buttons on the right side
 * - Scroll behavior for exit-until-collapsed effect
 *
 * @param title The main title text displayed when expanded
 * @param collapsedTitle Optional different title to show when collapsed (defaults to title)
 * @param subtitle Optional subtitle text displayed below the title
 * @param onNavigationClick Callback when navigation icon is clicked
 * @param modifier Modifier for the top bar
 * @param topPadding Top padding for the top bar
 * @param scrollBehavior Scroll behavior for collapsing effect
 * @param titleStyle Text style for the expanded title
 * @param collapsedTitleStyle Text style for the collapsed title
 * @param subtitleStyle Text style for the subtitle
 * @param primaryColor Primary theme color
 * @param backgroundColor Background color for the top bar
 * @param navigationIcon Composable for the navigation icon
 * @param actions Composable lambda for action buttons on the right
 * @param showTitleWhenCollapsed Whether to show the title when collapsed
 * @param collapsedFractionThreshold The scroll fraction at which to show collapsed title
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DgenLargeTopBar(
    title: String,
    collapsedTitle: String = title,
    subtitle: String? = null,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    topPadding: Dp = 16.dp,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleStyle: TextStyle = TextStyle(
        fontSize = 28.sp,
        color = dgenWhite,
        fontWeight = FontWeight.Bold,
        fontFamily = PitagonsSans
    ),
    collapsedTitleStyle: TextStyle = TextStyle(
        fontSize = 28.sp,
        color = dgenWhite,
        fontWeight = FontWeight.Bold,
        fontFamily = PitagonsSans
    ),
    subtitleStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        color = dgenTurqoise,
        fontWeight = FontWeight.Bold,
        fontFamily = SpaceMono
    ),
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = dgenBlack,
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = primaryColor,
            modifier = Modifier.size(width = 24.dp, height = 28.dp)
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
    showTitleWhenCollapsed: Boolean = true,
    collapsedFractionThreshold: Float = 0.98f
) {
    val collapsedFraction = scrollBehavior?.state?.collapsedFraction ?: 0f

    LargeTopAppBar(
        modifier = modifier.padding(top = topPadding),
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                navigationIcon()
            }
        },
        title = {
            Column {
                // Show collapsed title when fully collapsed
                if (showTitleWhenCollapsed && collapsedFraction > collapsedFractionThreshold) {
                    Text(
                        text = collapsedTitle.ifEmpty { "Untitled" },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = collapsedTitleStyle,
                        modifier = Modifier.padding(end = 48.dp)
                    )
                }

                // Subtitle is always visible
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = subtitleStyle
                    )
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor
        )
    )
}

/**
 * A version of DgenLargeTopBar with a painter-based navigation icon.
 * Useful when you need to use a custom drawable resource.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DgenLargeTopBar(
    title: String,
    collapsedTitle: String = title,
    subtitle: String? = null,
    onNavigationClick: () -> Unit,
    navigationIconPainter: Painter,
    navigationIconSize: Dp = 24.dp,
    modifier: Modifier = Modifier,
    topPadding: Dp = 16.dp,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    titleStyle: TextStyle = TextStyle(
        fontSize = 28.sp,
        color = dgenWhite,
        fontWeight = FontWeight.Bold,
        fontFamily = PitagonsSans
    ),
    collapsedTitleStyle: TextStyle = TextStyle(
        fontSize = 28.sp,
        color = dgenWhite,
        fontWeight = FontWeight.Bold,
        fontFamily = PitagonsSans
    ),
    subtitleStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        color = dgenTurqoise,
        fontWeight = FontWeight.Bold,
        fontFamily = SpaceMono
    ),
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = dgenBlack,
    actions: @Composable RowScope.() -> Unit = {},
    showTitleWhenCollapsed: Boolean = true,
    collapsedFractionThreshold: Float = 0.98f
) {
    DgenLargeTopBar(
        title = title,
        collapsedTitle = collapsedTitle,
        subtitle = subtitle,
        onNavigationClick = onNavigationClick,
        modifier = modifier,
        topPadding = topPadding,
        scrollBehavior = scrollBehavior,
        titleStyle = titleStyle,
        collapsedTitleStyle = collapsedTitleStyle,
        subtitleStyle = subtitleStyle,
        primaryColor = primaryColor,
        backgroundColor = backgroundColor,
        navigationIcon = {
            Image(
                painter = navigationIconPainter,
                contentDescription = "Back",
                colorFilter = ColorFilter.tint(primaryColor),
                modifier = Modifier.size(navigationIconSize)
            )
        },
        actions = actions,
        showTitleWhenCollapsed = showTitleWhenCollapsed,
        collapsedFractionThreshold = collapsedFractionThreshold
    )
}

/**
 * A folder action button commonly used in the DgenLargeTopBar actions slot.
 * Displays a folder icon with a label.
 */
@Composable
fun DgenLargeTopBarFolderAction(
    folderName: String,
    onClick: () -> Unit,
    folderIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = dgenTurqoise,
    textStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        color = dgenTurqoise,
        fontFamily = SpaceMono,
        fontWeight = FontWeight.Bold
    )
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        folderIcon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = folderName,
            style = textStyle
        )
    }
}

// ==================== PREVIEWS ====================

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenLargeTopBarPreview() {
    DgenTheme {
        DgenLargeTopBar(
            title = "My Note Title",
            subtitle = "JAN 2, 2026 • 14:30",
            onNavigationClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenLargeTopBarWithActionsPreview() {
    DgenTheme {
        DgenLargeTopBar(
            title = "My Note Title",
            subtitle = "JAN 2, 2026 • 14:30",
            onNavigationClick = {},
            actions = {
                Text(
                    text = "NOTES",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = dgenTurqoise,
                        fontFamily = SpaceMono,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenLargeTopBarNoSubtitlePreview() {
    DgenTheme {
        DgenLargeTopBar(
            title = "Settings",
            onNavigationClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenLargeTopBarCustomColorsPreview() {
    DgenTheme {
        DgenLargeTopBar(
            title = "Custom Colors",
            subtitle = "CUSTOM SUBTITLE",
            onNavigationClick = {},
            primaryColor = Color(0xFF00FF00),
            subtitleStyle = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF00FF00),
                fontWeight = FontWeight.Bold,
                fontFamily = SpaceMono
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun DgenLargeTopBarPreviewDDevice() {
    DgenTheme {
        DgenLargeTopBar(
            title = "My Note Title",
            subtitle = "JAN 2, 2026 • 14:30",
            onNavigationClick = {}
        )
    }
}