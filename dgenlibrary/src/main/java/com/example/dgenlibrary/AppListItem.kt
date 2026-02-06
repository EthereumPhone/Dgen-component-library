package com.example.dgenlibrary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.neonOpacity
import com.example.dgenlibrary.util.drawCategoryTag

@Composable
fun AppListItem(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    name: String,
    category: String,
    author: String,
    secondaryColor: Color,
    primaryColor: Color,
    onClick: () -> Unit,
    isInstalled: Boolean = false
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = "app icon",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(RoundedCornerShape(0.dp))
                .size(80.dp)
                .aspectRatio(1f)
                .drawWithContent {
                    // Draw the original content (the image)
                    drawContent()

                    drawCategoryTag(
                        category = category.uppercase(),
                        textSize = 13f,
                        textColor = dgenWhite.toAndroidColor(),
                        shapeColor = dgenRed,
                        rotationAngle = -90f,
                        fontResId = R.font.spacemono_bold,
                        context = context
                    )
                }
                .border(1.dp, primaryColor, RoundedCornerShape(0.dp))
        )

        Spacer(modifier = modifier.width(16.dp))
        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                name,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                modifier = modifier
            )
            Text(
                author,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor.copy(neonOpacity),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                modifier = modifier
            )
        }
    }
}

@Composable
fun InstalledAppItem(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    name: String,
    category: String,
    isInstalled: Boolean,
    visible: Boolean,
    isDeleting: Boolean = false,
    secondaryColor: Color,
    primaryColor: Color,
    onClickItem: () -> Unit,
    onDelete: () -> Unit,
    onClickButton: () -> Unit
) {
    val context = LocalContext.current
    AnimatedVisibility(
        visible = visible,
        exit = fadeOut(tween(300)) + shrinkVertically(tween(300))
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .alpha(if (isDeleting) 0.5f else 1f)
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (!isDeleting) onClickItem()
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = "app icon",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(RoundedCornerShape(0.dp))
                    .size(80.dp)
                    .aspectRatio(1f)
                    .drawWithContent {
                        // Draw the original content (the image)
                        drawContent()

                        drawCategoryTag(
                            category = category.uppercase(),
                            textSize = 13f,
                            textColor = dgenWhite.toAndroidColor(),
                            shapeColor = dgenRed,
                            rotationAngle = -90f,
                            fontResId = R.font.spacemono_bold,
                            context = context
                        )
                    }
                    .border(1.dp, primaryColor, RoundedCornerShape(0.dp))
            )
            Spacer(modifier = modifier.width(16.dp))
            Column(
                modifier = modifier.weight(1f)
            ) {
                Text(
                    name,
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = primaryColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    modifier = modifier
                )
            }
            Spacer(modifier = modifier.width(16.dp))
            Column(
                modifier = Modifier.width(100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DgenSecondaryButton(
                    text = if (isDeleting) "Deleting..." else "Delete",
                    containerColor = if (isDeleting) primaryColor.copy(alpha = 0.5f) else primaryColor,
                    fontSize = 16.sp,
                    verticalPadding = 8.dp,
                    horizontalPadding = 12.dp,
                    onClick = { if (!isDeleting) onDelete() }
                )
            }
        }
    }
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
fun AppListItemPreview() {
    InstalledAppItem(
        imageUrl = null,
        name = "Coinbase",
        onClickItem = {},
        category = "DEFI",
        onDelete = {},
        onClickButton = {},
        isInstalled = false,
        visible = true,
        isDeleting = false,
        secondaryColor = dgenBlack,
        primaryColor = dgenWhite
    )
}
