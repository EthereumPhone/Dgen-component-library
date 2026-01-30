package com.example.dgenlibrary.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite

/**
 * Data class representing an item with its header visibility status.
 * 
 * @param T The type of the item
 * @property item The actual item
 * @property showHeader Whether to show a header before this item
 */
data class HeaderedItem<T>(
    val item: T,
    val showHeader: Boolean
)

/**
 * Generates a list of HeaderedItems with alphabetical headers.
 * 
 * Items are expected to be sorted alphabetically. A header will be shown
 * before the first item of each new starting letter.
 *
 * @param T The type of items
 * @param items The list of items to process
 * @param getFirstLetter Function to extract the first letter from an item (used for grouping)
 * @return List of HeaderedItem with header visibility flags
 */
fun <T> getItemsWithAlphabeticalHeaders(
    items: List<T>,
    getFirstLetter: (T) -> Char
): List<HeaderedItem<T>> {
    if (items.isEmpty()) return emptyList()
    
    var lastFirstLetter: Char? = null
    return items.map { item ->
        val firstLetter = getFirstLetter(item).uppercaseChar()
        val showHeader = firstLetter != lastFirstLetter
        lastFirstLetter = firstLetter
        HeaderedItem(item = item, showHeader = showHeader)
    }
}

/**
 * A LazyColumn with alphabetical section headers.
 * 
 * This component displays a list of items grouped by their first letter,
 * with headers shown at the start of each alphabetical section.
 *
 * @param T The type of items in the list
 * @param items List of items with header visibility flags
 * @param modifier Modifier for the LazyColumn
 * @param listState State object for the LazyColumn
 * @param headerStyle Text style for the section headers
 * @param headerColor Color for the header text
 * @param headerPadding Padding around the header
 * @param topSpacerHeight Height of the spacer at the top of the list
 * @param bottomSpacerHeight Height of the spacer at the bottom of the list
 * @param getHeaderText Function to get the header text from an item
 * @param itemContent Composable for rendering each item
 */
@Composable
fun <T> HeaderedLazyColumn(
    items: List<HeaderedItem<T>>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    headerStyle: TextStyle = DgenTheme.typography.label,
    headerColor: Color = dgenTurqoise,
    headerPadding: Dp = 24.dp,
    topSpacerHeight: Dp = 86.dp,
    bottomSpacerHeight: Dp = 32.dp,
    getHeaderText: (T) -> String,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        // Top spacer
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topSpacerHeight)
            )
        }

        items(items) { headeredItem ->
            if (headeredItem.showHeader) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = headerPadding, vertical = 4.dp)
                ) {
                    Text(
                        text = getHeaderText(headeredItem.item),
                        color = headerColor,
                        style = headerStyle
                    )
                }
            }
            itemContent(headeredItem.item)
        }

        // Bottom spacer
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomSpacerHeight)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun HeaderedLazyColumnPreview() {
    val sampleItems = listOf(
        "Alice", "Andrew", "Bob", "Carol", "Charlie", 
        "David", "Eve", "Frank", "Grace"
    )
    val headeredItems = getItemsWithAlphabeticalHeaders(sampleItems) { it.first() }
    
    DgenTheme {
        HeaderedLazyColumn(
            items = headeredItems,
            getHeaderText = { it.first().uppercase() },
            itemContent = { name ->
                Text(
                    text = name,
                    color = dgenWhite,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        )
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
private fun HeaderedLazyColumnPreviewDDevice() {
    val sampleItems = listOf(
        "Alice", "Andrew", "Bob", "Carol", "Charlie", 
        "David", "Eve", "Frank", "Grace"
    )
    val headeredItems = getItemsWithAlphabeticalHeaders(sampleItems) { it.first() }
    
    DgenTheme {
        HeaderedLazyColumn(
            items = headeredItems,
            getHeaderText = { it.first().uppercase() },
            itemContent = { name ->
                Text(
                    text = name,
                    color = dgenWhite,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        )
    }
}