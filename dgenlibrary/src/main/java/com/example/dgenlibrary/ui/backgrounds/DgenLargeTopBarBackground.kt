package com.example.dgenlibrary.ui.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.dgenlibrary.ui.headers.DgenLargeTopBar
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * A scaffold background with a collapsible large top bar.
 * 
 * This component provides a complete screen layout with:
 * - DgenLargeTopBar as the top bar that collapses on scroll
 * - Nested scroll connection for smooth collapse behavior
 * - Optional fade overlay at the top of the content
 * - Customizable content area with inner padding
 *
 * Use this component when you need a screen with a large collapsible header.
 *
 * @param title The main title text for the top bar
 * @param collapsedTitle Optional different title when collapsed
 * @param subtitle Optional subtitle text
 * @param onNavigationClick Callback when navigation icon is clicked
 * @param modifier Modifier for the scaffold
 * @param scrollBehavior Scroll behavior for the top bar
 * @param primaryColor Primary theme color
 * @param backgroundColor Background color for the screen
 * @param topBarPadding Top padding for the top bar
 * @param navigationIcon Composable for the navigation icon
 * @param actions Actions composable for the top bar
 * @param showFadeOverlay Whether to show a fade overlay at the top of content
 * @param fadeOverlayHeight Height of the fade overlay
 * @param titleStyle Text style for the expanded title
 * @param collapsedTitleStyle Text style for the collapsed title
 * @param subtitleStyle Text style for the subtitle
 * @param content Content composable receiving inner padding
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DgenLargeTopBarBackground(
    title: String,
    collapsedTitle: String = title,
    subtitle: String? = null,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = dgenBlack,
    topBarPadding: Dp = 16.dp,
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = primaryColor
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
    showFadeOverlay: Boolean = true,
    fadeOverlayHeight: Dp = 8.dp,
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
        color = primaryColor,
        fontWeight = FontWeight.Bold,
        fontFamily = SpaceMono
    ),
    content: @Composable BoxScope.(PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = backgroundColor,
        topBar = {
            DgenLargeTopBar(
                title = title,
                collapsedTitle = collapsedTitle,
                subtitle = subtitle,
                onNavigationClick = onNavigationClick,
                topPadding = topBarPadding,
                scrollBehavior = scrollBehavior,
                primaryColor = primaryColor,
                backgroundColor = backgroundColor,
                navigationIcon = navigationIcon,
                actions = actions,
                titleStyle = titleStyle,
                collapsedTitleStyle = collapsedTitleStyle,
                subtitleStyle = subtitleStyle
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            content(innerPadding)

            // Fade overlay on top of content (transparent → background)
            if (showFadeOverlay) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fadeOverlayHeight)
                        .padding(top = innerPadding.calculateTopPadding())
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, backgroundColor)
                            )
                        )
                        .align(Alignment.TopCenter)
                        .zIndex(1f)
                )
            }
        }
    }
}

/**
 * A simpler version of DgenLargeTopBarBackground that creates its own scroll behavior.
 * Use this when you don't need to manage scroll behavior externally.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DgenLargeTopBarScreen(
    title: String,
    collapsedTitle: String = title,
    subtitle: String? = null,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = dgenBlack,
    topBarPadding: Dp = 16.dp,
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = primaryColor
        )
    },
    actions: @Composable RowScope.() -> Unit = {},
    showFadeOverlay: Boolean = true,
    fadeOverlayHeight: Dp = 8.dp,
    content: @Composable BoxScope.(PaddingValues) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    
    DgenLargeTopBarBackground(
        title = title,
        collapsedTitle = collapsedTitle,
        subtitle = subtitle,
        onNavigationClick = onNavigationClick,
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        primaryColor = primaryColor,
        backgroundColor = backgroundColor,
        topBarPadding = topBarPadding,
        navigationIcon = navigationIcon,
        actions = actions,
        showFadeOverlay = showFadeOverlay,
        fadeOverlayHeight = fadeOverlayHeight,
        content = content
    )
}

// ==================== PREVIEWS ====================

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505, heightDp = 400)
@Composable
private fun DgenLargeTopBarBackgroundPreview() {
    DgenTheme {
        DgenLargeTopBarScreen(
            title = "My Notes",
            subtitle = "JAN 2, 2026 • 14:30",
            onNavigationClick = {}
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Content goes here...",
                    color = dgenWhite
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505, heightDp = 400)
@Composable
private fun DgenLargeTopBarBackgroundWithActionsPreview() {
    DgenTheme {
        DgenLargeTopBarScreen(
            title = "My Notes",
            subtitle = "JAN 2, 2026 • 14:30",
            onNavigationClick = {},
            actions = {
                Text(
                    text = "FOLDER",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = dgenTurqoise,
                        fontFamily = SpaceMono,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Content with actions in the top bar...",
                    color = dgenWhite
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505, heightDp = 400)
@Composable
private fun DgenLargeTopBarBackgroundCustomColorsPreview() {
    DgenTheme {
        DgenLargeTopBarScreen(
            title = "Custom Theme",
            subtitle = "GREEN THEME",
            onNavigationClick = {},
            primaryColor = Color(0xFF00FF00),
            backgroundColor = Color(0xFF0A0A0A)
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Green themed content...",
                    color = Color(0xFF00FF00)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF050505, heightDp = 400)
@Composable
private fun DgenLargeTopBarBackgroundNoFadePreview() {
    DgenTheme {
        DgenLargeTopBarScreen(
            title = "No Fade Overlay",
            subtitle = "CLEAN EDGE",
            onNavigationClick = {},
            showFadeOverlay = false
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Content without fade overlay...",
                    color = dgenWhite
                )
            }
        }
    }
}

