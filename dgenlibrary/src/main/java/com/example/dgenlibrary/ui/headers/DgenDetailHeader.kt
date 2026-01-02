package com.example.dgenlibrary.ui.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * A detail screen header with back button, editable title, and edit/save toggle.
 * 
 * This header is designed for detail screens where the title can be edited.
 * It provides:
 * - Back navigation button on the left
 * - Centered title that can switch between display and edit modes
 * - Edit/Save toggle button on the right
 * - Gradient overlays at top and bottom
 *
 * @param title The current title text
 * @param isEditMode Whether the header is in edit mode
 * @param onBackClick Callback when back button is clicked
 * @param onEditToggle Callback when edit/save button is clicked
 * @param onTitleTap Callback when title is tapped (in view mode)
 * @param modifier Modifier for the header
 * @param height Height of the header
 * @param primaryColor Primary theme color
 * @param backgroundColor Background color
 * @param titleTextStyle Text style for the title
 * @param backIcon Composable for the back icon
 * @param editIcon Icon for edit mode toggle
 * @param saveIcon Icon for save mode toggle
 * @param iconSize Size of the icons
 * @param showTopGradient Whether to show the top gradient
 * @param showBottomGradient Whether to show the bottom gradient
 * @param editableContent Optional composable for custom editable content
 */
@Composable
fun DgenDetailHeader(
    title: String,
    isEditMode: Boolean,
    onBackClick: () -> Unit,
    onEditToggle: () -> Unit,
    onTitleTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 80.dp,
    primaryColor: Color = dgenTurqoise,
    backgroundColor: Color = Color.Transparent,
    titleTextStyle: TextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = PitagonsSans,
        color = dgenWhite,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        textDecoration = TextDecoration.None
    ),
    backIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Rounded.Check, // Replace with back arrow
            contentDescription = "Back",
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
    },
    editIcon: ImageVector = Icons.Default.Edit,
    saveIcon: ImageVector = Icons.Rounded.Check,
    iconSize: Dp = 24.dp,
    showTopGradient: Boolean = true,
    showBottomGradient: Boolean = true,
    editableContent: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(backgroundColor)
    ) {
        // Center content
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 64.dp)
                    .pointerInput(isEditMode, title) {
                        if (!isEditMode) {
                            detectTapGestures(
                                onTap = { onTitleTap() }
                            )
                        }
                    }
            ) {
                if (isEditMode && editableContent != null) {
                    editableContent()
                } else {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title.ifEmpty { "N/A" },
                            style = titleTextStyle.copy(
                                color = if (!isEditMode) dgenWhite.copy(alpha = 0.8f) else dgenWhite
                            )
                        )
                    }
                }
            }

            // Top gradient
            if (showTopGradient) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(dgenBlack, Color.Transparent)
                            )
                        )
                )
            }

            // Bottom gradient
            if (showBottomGradient) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    dgenBlack
                                )
                            )
                        )
                )
            }
        }

        // Back button
        IconButton(
            modifier = Modifier
                .size(56.dp)
                .padding(start = 16.dp)
                .align(Alignment.CenterStart),
            onClick = onBackClick
        ) {
            backIcon()
        }

        // Edit/Save toggle button
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd),
            onClick = onEditToggle
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = if (isEditMode) saveIcon else editIcon,
                contentDescription = if (isEditMode) "Save changes" else "Edit",
                tint = primaryColor
            )
        }
    }
}

/**
 * A simpler detail header without edit functionality.
 * 
 * Use this for read-only detail screens where editing is not needed.
 *
 * @param title The title text
 * @param onBackClick Callback when back button is clicked
 * @param onTitleTap Callback when title is tapped
 * @param modifier Modifier for the header
 * @param height Height of the header
 * @param primaryColor Primary theme color
 * @param titleTextStyle Text style for the title
 * @param backIcon Composable for the back icon
 * @param actionContent Optional content on the right side
 */
@Composable
fun DgenSimpleDetailHeader(
    title: String,
    onBackClick: () -> Unit,
    onTitleTap: () -> Unit = {},
    modifier: Modifier = Modifier,
    height: Dp = 80.dp,
    primaryColor: Color = dgenTurqoise,
    titleTextStyle: TextStyle = TextStyle(
        textAlign = TextAlign.Center,
        fontFamily = PitagonsSans,
        color = dgenWhite,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        textDecoration = TextDecoration.None
    ),
    backIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = "Back",
            tint = primaryColor,
            modifier = Modifier.size(24.dp)
        )
    },
    actionContent: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        // Center title
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .pointerInput(title) {
                    detectTapGestures(onTap = { onTitleTap() })
                }
        ) {
            Text(
                text = title.ifEmpty { "N/A" },
                style = titleTextStyle,
                modifier = Modifier.padding(horizontal = 64.dp)
            )
        }

        // Back button
        IconButton(
            modifier = Modifier
                .size(56.dp)
                .padding(start = 16.dp)
                .align(Alignment.CenterStart),
            onClick = onBackClick
        ) {
            backIcon()
        }

        // Action content
        if (actionContent != null) {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd)
            ) {
                actionContent()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenDetailHeaderPreview() {
    DgenTheme {
        DgenDetailHeader(
            title = "John Doe",
            isEditMode = false,
            onBackClick = {},
            onEditToggle = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenDetailHeaderEditModePreview() {
    DgenTheme {
        DgenDetailHeader(
            title = "John Doe",
            isEditMode = true,
            onBackClick = {},
            onEditToggle = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun DgenSimpleDetailHeaderPreview() {
    DgenTheme {
        DgenSimpleDetailHeader(
            title = "Contact Details",
            onBackClick = {}
        )
    }
}

