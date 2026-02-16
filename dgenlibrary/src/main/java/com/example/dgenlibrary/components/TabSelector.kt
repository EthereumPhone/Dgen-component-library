package com.example.dgenlibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenWhite

@Composable
fun CustomTabSelector(
    selectedTabIndex: MutableState<Int>,
    tabTitles: List<String>,
    onTabSelected: (Int) -> Unit,
    selectedTabColor: Color = dgenWhite,
    unselectedTabColor: Color = Color.Transparent
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .background(Color.Transparent, shape = CircleShape)
    ) {
        tabTitles.forEachIndexed { index, title ->
            val isSelected = index == selectedTabIndex.value
            TabItem(
                title = title,
                isSelected = isSelected,
                onTabClick = { onTabSelected(index) },
                selectedTabColor = selectedTabColor,
                unselectedTabColor = unselectedTabColor
            )
        }
    }
}

@Composable
fun TabItem(
    title: String,
    isSelected: Boolean,
    onTabClick: () -> Unit,
    selectedTabColor: Color,
    unselectedTabColor: Color
) {
    val backgroundColor = if (isSelected) selectedTabColor else unselectedTabColor
    val textColor = if (isSelected) dgenBlack else dgenWhite
    val fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = CircleShape)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { onTabClick() },
                    onTap = { onTabClick() },
                )
            }
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = textColor,
            fontFamily = PitagonsSans,
            fontWeight = fontWeight
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun CustomTabSelectorPreview() {
    val list = listOf("SMS", "XMTP")
    val index = remember { mutableIntStateOf(0) }
    CustomTabSelector(index, list, { index.intValue = it })
}
