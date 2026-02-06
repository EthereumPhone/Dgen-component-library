package com.example.dgenlibrary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun AppGrid(
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(3),
    contentPadding: PaddingValues = PaddingValues(20.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(20.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(20.dp),
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .zIndex(0f),
        columns = columns,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}
