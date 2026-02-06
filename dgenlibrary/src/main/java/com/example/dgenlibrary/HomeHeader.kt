package com.example.dgenlibrary

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.SpaceMono

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onClickSearch: () -> Unit,
    onClickSettings: () -> Unit,
    icon: @Composable () -> Unit,
    enableSettings: Boolean,
    primaryColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures {
                        onClickSearch()
                    }
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.searchicon),
                contentDescription = "Search",
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "SEARCH",
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                )
            )
        }

        IconButton(
            modifier = Modifier.alpha(if (enableSettings) 1f else 0f),
            onClick = { if (enableSettings) onClickSettings() },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = primaryColor
            )
        ) {
            icon()
        }
    }
}
