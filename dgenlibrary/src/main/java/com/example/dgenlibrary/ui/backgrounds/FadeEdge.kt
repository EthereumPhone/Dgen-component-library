package com.example.dgenlibrary.ui.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.dgenBlack

/**
 * Direction from which the fade originates.
 * The gradient runs from transparent at the [FadeEdge]'s outer edge
 * toward [FadeEdge]'s `color` at the inner edge.
 */
enum class FadeDirection {
    Top, Bottom, Left, Right
}

/**
 * A gradient fade overlay that transitions from [Color.Transparent] to [color].
 *
 * Use inside a [Box] with the appropriate [Alignment] to place the fade along
 * an edge of a container. For vertical directions ([FadeDirection.Top] /
 * [FadeDirection.Bottom]) the composable fills the parent width and uses [size]
 * as its height. For horizontal directions ([FadeDirection.Left] /
 * [FadeDirection.Right]) it fills the parent height and uses [size] as its width.
 *
 * @param direction Which edge the fade sits on.
 * @param modifier  Modifier applied to the underlying [Box].
 * @param color     The opaque end of the gradient. Defaults to [dgenBlack].
 * @param size      Thickness of the gradient band. Defaults to 32.dp.
 */
@Composable
fun FadeEdge(
    direction: FadeDirection,
    modifier: Modifier = Modifier,
    color: Color = dgenBlack,
    size: Dp = 32.dp
) {
    val isVertical = direction == FadeDirection.Top || direction == FadeDirection.Bottom

    val colors = when (direction) {
        FadeDirection.Top    -> listOf(color, Color.Transparent)
        FadeDirection.Bottom -> listOf(Color.Transparent, color)
        FadeDirection.Left   -> listOf(color, Color.Transparent)
        FadeDirection.Right  -> listOf(Color.Transparent, color)
    }

    val brush = if (isVertical) {
        Brush.verticalGradient(colors)
    } else {
        Brush.horizontalGradient(colors)
    }

    val sizeModifier = if (isVertical) {
        Modifier.fillMaxWidth().height(size)
    } else {
        Modifier.fillMaxHeight().width(size)
    }

    Box(
        modifier = modifier
            .then(sizeModifier)
            .background(brush)
    )
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
private fun FadeEdgeTopPreview() {
    Box {
        FadeEdge(direction = FadeDirection.Top, size = 64.dp)
    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
private fun FadeEdgeBottomPreview() {
    Box {
        FadeEdge(direction = FadeDirection.Bottom, size = 64.dp)
    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
private fun FadeEdgeLeftPreview() {
    Box {
        FadeEdge(direction = FadeDirection.Left, size = 64.dp)
    }
}

@Preview(showBackground = true, widthDp = 200, heightDp = 200)
@Composable
private fun FadeEdgeRightPreview() {
    Box {
        FadeEdge(direction = FadeDirection.Right, size = 64.dp)
    }
}
