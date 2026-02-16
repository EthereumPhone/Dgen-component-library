package com.example.dgenlibrary.ui.token

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGreen
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.ghostOpacity
import com.example.dgenlibrary.ui.theme.neonOpacity
import kotlinx.coroutines.delay

/**
 * A composable that shows a token image picker box with a label and animated background.
 * The background fades in when the box is tapped and fades out automatically after a short delay.
 *
 * @param imageUri Currently selected image URI, or null if none selected.
 * @param primaryColor Primary accent color
 * @param secondaryColor Background color for the image box
 * @param onPickImage Lambda invoked when the user taps the box to select an image.
 * @param modifier Modifier for this composable.
 * @param label Text displayed above the box. Defaults to "TOKEN IMAGE".
 * @param activeBackgroundColor Color used for the animated tap-highlight background
 */
@Composable
fun TokenImageField(
    imageUri: Uri?,
    primaryColor: Color,
    secondaryColor: Color,
    onPickImage: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "TOKEN IMAGE",
    activeBackgroundColor: Color = dgenOcean,
) {
    var isPressed by remember { mutableStateOf(false) }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(300)
            isPressed = false
        }
    }

    val animatedBgAlpha by animateFloatAsState(
        targetValue = if (isPressed) ghostOpacity else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "bgAlpha"
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(0.dp))
            .drawBehind {
                drawRect(
                    color = activeBackgroundColor,
                    size = size,
                    topLeft = Offset.Zero,
                    alpha = animatedBgAlpha
                )
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = DgenTheme.typography.label,
            color = primaryColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier
                .size(148.dp)
                .background(secondaryColor, RoundedCornerShape(8.dp))
                .clickable {
                    isPressed = true
                    onPickImage()
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected token image. Double-tap to change image.",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add token image",
                        modifier = Modifier.size(32.dp),
                        tint = primaryColor.copy(neonOpacity)
                    )
                    Text(
                        text = "UPLOAD",
                        style = DgenTheme.typography.label,
                        fontSize = 20.sp,
                        color = primaryColor.copy(neonOpacity),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
private fun TokenImageFieldEmptyPreview() {
    DgenTheme {
        TokenImageField(
            imageUri = null,
            primaryColor = dgenGreen,
            secondaryColor = dgenBlack,
            onPickImage = {}
        )
    }
}
