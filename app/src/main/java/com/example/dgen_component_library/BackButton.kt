package com.example.dgen_component_library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    color: Color = DgenTheme.colors.dgenWhite,
    size: Dp = 20.dp
    //content: @Composable RowScope.() -> Unit
){
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .graphicsLayer { translationX = -20f }
        ,
        onClick = {
            onClick()
        }) {
        Icon(painter = painterResource(R.drawable.backicon), contentDescription = "BackButton", modifier = modifier.size(size), tint = color)
    }
}