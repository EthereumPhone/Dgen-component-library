package com.example.dgenlibrary.contacts

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.util.Haptics

@Composable
fun ContactListItem(
    name: String,
    primaryColor: Color,
    onClick: () -> Unit,
    view: View
){
    val interactionSource = remember { MutableInteractionSource() }
    //when user hovers over ContactListItem
    val isHover by interactionSource.collectIsHoveredAsState()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isHover) {
                    Color(0xFF1a1a1a)
                } else {
                    Color.Transparent
                }
            )
            .padding(start = 24.dp, end = 42.dp, top = 12.dp, bottom = 12.dp)
            .clickable {
                onClick()
                view.performHapticFeedback(Haptics().NEUTRAL_HAPTIC)
            },
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        verticalAlignment = Alignment.CenterVertically

    ){

        Text(
            text = name,
            style = DgenTheme.typography.body2,
            color = primaryColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ContactListItemPreview() {
    val view = LocalView.current
    ContactListItem(
        name = "John Doe",
        primaryColor = Color.White,
        onClick = {},
        view = view
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun ContactListItemLongNamePreview() {
    val view = LocalView.current
    ContactListItem(
        name = "Alexander Christopher Montgomery III",
        primaryColor = Color.White,
        onClick = {},
        view = view
    )
}