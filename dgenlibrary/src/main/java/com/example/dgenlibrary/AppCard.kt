package com.example.dgenlibrary

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ExtensionDensity.toDp
import com.example.dgenlibrary.ExtensionDensity.toPx
import com.example.dgenlibrary.util.drawCategoryTag
import com.example.dgenlibrary.util.drawCheckCircle

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    isInstalled: Boolean,
    context: Context,
    imageUrl: String,
    name: String,
    category: String,
    primaryColor: Color,
    secondaryColor: Color,
    onClick: () -> Unit
) {
    // Base placeholder image painter.
    val basePlaceholderPainter = painterResource(R.drawable.dgen_image_placeholder)
    // Painter with dark overlay - created once per composition.
    val darkPlaceholderPainter = remember {
        object : Painter() {
            override val intrinsicSize get() = basePlaceholderPainter.intrinsicSize

            override fun DrawScope.onDraw() {
                with(basePlaceholderPainter) {
                    draw(size, alpha = 1f)
                }
                drawRect(dgenBlack.copy(alpha = 0.6f))
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when (imageUrl.isNotEmpty()) {
            true -> {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        // Enable a small cross-fade only when the image isn't cached to avoid flashing on scroll.
                        .crossfade(true)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = name,
                    placeholder = darkPlaceholderPainter,
                    error = basePlaceholderPainter,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { onClick() }
                        .clip(RoundedCornerShape(0.dp))
                        .drawWithContent {
                            // Draw the original content (the image)
                            drawContent()

                            drawCategoryTag(
                                category = category.uppercase(),
                                textSize = 24f,
                                textColor = dgenWhite.toAndroidColor(),
                                shapeColor = dgenRed,
                                rotationAngle = -90f,
                                fontResId = R.font.spacemono_bold,
                                context = context
                            )
                            val padding = 8.dp
                            val width = size.width.toDp()

                            val radiusPx = 10.dp.toPx()
                            val strokePx = 2.dp.toPx()
                            val offset = DpOffset(width - (2 * radiusPx).dp, padding)
                            // Calculate the center position using the offset.
                            val center = Offset(
                                x = offset.x.toPx() + radiusPx,
                                y = offset.y.toPx() + radiusPx
                            )
                            if (isInstalled) {
                                drawCheckCircle(
                                    circleColor = primaryColor,
                                    checkColor = secondaryColor,
                                    radius = radiusPx,
                                    strokeWidth = strokePx,
                                    center = center
                                )
                            }
                        }
                        .border(1.dp, primaryColor, RoundedCornerShape(0.dp))
                )
            }
            false -> {
                // TODO: change the placeholder image
                Image(
                    painter = basePlaceholderPainter,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // Makes the card square
                        .clickable { onClick() }
                        .clip(RoundedCornerShape(0.dp))
                        .drawWithContent {
                            // Draw the original content (the image)
                            drawContent()

                            drawCategoryTag(
                                category = category.uppercase(),
                                textSize = 22f,
                                textColor = dgenWhite.toAndroidColor(),
                                shapeColor = primaryColor,
                                rotationAngle = -90f,
                                fontResId = R.font.spacemono_bold,
                                context = context
                            )
                        }
                        .border(1.dp, primaryColor, RoundedCornerShape(0.dp))
                )
            }
        }
        Text(
            text = name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontFamily = PitagonsSans,
                color = primaryColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.sp,
                textDecoration = TextDecoration.None
            ),
            color = primaryColor
        )
    }
}

@Preview
@Composable
fun PreviewAppCard() {
    val context = LocalContext.current

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 24.dp),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(6) {
            AppCard(
                context = context,
                category = "MARKETPLACE",
                name = "Coinbase",
                imageUrl = "",
                onClick = { },
                isInstalled = true,
                primaryColor = Color.Red,
                secondaryColor = Color.Blue
            )
        }
    }
}

