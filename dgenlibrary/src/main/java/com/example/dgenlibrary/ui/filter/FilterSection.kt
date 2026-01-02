package com.example.dgenlibrary.ui.filter

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.label_fontSize

/**
 * A collapsible filter section with selectable options.
 * Used in filter overlays to group related filter options.
 *
 * @param title Section title (e.g., "SORT BY", "VOLUME")
 * @param options List of option strings to display
 * @param selectedOption Currently selected option (null if none selected)
 * @param primaryColor Primary color for borders and selected state background
 * @param secondaryColor Color for text when option is selected
 * @param onOptionSelected Callback when an option is selected/deselected (null to deselect)
 * @param initiallyExpanded Whether the section starts expanded
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(
    title: String,
    options: List<String>,
    selectedOption: String?,
    primaryColor: Color,
    secondaryColor: Color,
    onOptionSelected: (String?) -> Unit,
    initiallyExpanded: Boolean = true
) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .animateContentSize(animationSpec = tween(300))
    ) {
        // Section header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = label_fontSize,
                    letterSpacing = 1.sp
                )
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = primaryColor,
                modifier = Modifier.size(20.dp)
            )
        }

        // Filter options
        if (isExpanded) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                options.forEach { option ->
                    val isSelected = selectedOption == option

                    FilterOptionButton(
                        text = option,
                        isSelected = isSelected,
                        onClick = {
                            onOptionSelected(if (isSelected) null else option)
                        },
                        primaryColor = primaryColor,
                        secondaryColor = secondaryColor
                    )
                }
            }
        }
    }
}

/**
 * A selectable filter option button.
 * Shows different styles for selected/unselected states.
 *
 * @param text Button text
 * @param isSelected Whether the option is currently selected
 * @param primaryColor Color for border and selected background
 * @param secondaryColor Color for text when selected
 * @param onClick Callback when the button is clicked
 */
@Composable
fun FilterOptionButton(
    text: String,
    isSelected: Boolean,
    primaryColor: Color,
    secondaryColor: Color,
    onClick: () -> Unit
) {
    val fontSize = 16.sp
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(if (isSelected) primaryColor else Color.Transparent)
            .border(BorderStroke(1.dp, primaryColor), RoundedCornerShape(3.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            style = TextStyle(
                fontFamily = SpaceMono,
                color = if (isSelected) secondaryColor else primaryColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize,
                lineHeight = fontSize,
                letterSpacing = 1.sp,
                textDecoration = TextDecoration.None
            ),
            maxLines = 1
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun FilterSectionPreview() {
    FilterSection(
        title = "SORT BY",
        options = listOf("trending", "24h % change", "volume", "market cap"),
        selectedOption = "trending",
        primaryColor = dgenGreen,
        secondaryColor = dgenBlack,
        onOptionSelected = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun FilterSectionCollapsedPreview() {
    FilterSection(
        title = "VOLUME",
        options = listOf("<\$100k", "\$100k-\$1m", "\$1m-\$10m", "\$10m+"),
        selectedOption = null,
        primaryColor = dgenGreen,
        secondaryColor = dgenBlack,
        onOptionSelected = {},
        initiallyExpanded = false
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun FilterOptionButtonSelectedPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        FilterOptionButton(
            text = "24h % change",
            isSelected = true,
            primaryColor = dgenGreen,
            secondaryColor = dgenBlack,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun FilterOptionButtonUnselectedPreview() {
    Box(modifier = Modifier.padding(8.dp)) {
        FilterOptionButton(
            text = "volume",
            isSelected = false,
            primaryColor = dgenGreen,
            secondaryColor = dgenBlack,
            onClick = {}
        )
    }
}

