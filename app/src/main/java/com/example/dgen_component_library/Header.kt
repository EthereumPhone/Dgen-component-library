package com.example.dgen_component_library

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgen_component_library.ExtensionDensity.toDp
import com.example.dgen_component_library.ExtensionDensity.toPx
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.Exo2Family
import com.example.dgenlibrary.ui.theme.SourceSansProFamily
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenAqua
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    minHeight: Dp,
    maxHeight: Dp,
    progress: Float,
    progressDp: Dp,
    onNavigateBack: () -> Unit,
    app: AppMetadata,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,

    )
{

    val animatedHeight by animateDpAsState(
        targetValue = if (progress <= COLLAPSE_START) minHeight else progressDp,
        animationSpec = tween(durationMillis = 200) // Adjust duration as needed
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedHeight) // progressDp

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(minHeight)
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 0.dp,
                    bottom = 0.dp
                )
            , // Adjust size as needed
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.width(100.dp)
                ) {
                    BackButton(
                        onClick = onNavigateBack
                    )
                }



            Surface(
                modifier = Modifier.size(48.dp), // Adjust size as needed
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.White) // White border with 4.dp thickness
            ) {
                Image(
                    painter = painterResource(app.logo),
                    contentDescription = "Circular Image",
                    contentScale = ContentScale.Crop, // Crop the image to fill the circle
                    modifier = Modifier.clip(CircleShape) // Clip the image to a circular shape
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(100.dp)
            ) {
                InstallButton (
                    modifier = Modifier.align(Alignment.Center),
                    textStyle = TextStyle(
                        fontFamily = SpaceMono,//SourceSansProFamily,
                        color = dgenBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.sp,
                        textDecoration = TextDecoration.None,
                        textAlign = TextAlign.Center
                    ),
                    text = "Install"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 0.dp,
                    bottom = 0.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = app.name,
                style = DgenTheme.typography.body2,
                color = DgenTheme.colors.dgenWhite
            )
            Text(
                modifier = modifier.width(200.dp),
                text = app.description,
                style = TextStyle(
                    fontFamily = SourceSansProFamily, //Exo2Family, //
                    color = dgenWhite,
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center
                ),
                color = DgenTheme.colors.dgenWhite
            )
            Surface(
                color = dgenRed,
                modifier = Modifier
                .padding(8.dp),
                shape = RoundedCornerShape(0.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp),
                        contentAlignment = Alignment.Center // Basiszentrierung
                    ) {
                        Text(
                            text = app.tags[0],
                            style = TextStyle(
                                fontFamily = SpaceMono, //SourceSansProFamily
                                color = dgenWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = 13.sp,
                                letterSpacing = 0.5.sp,
                                textDecoration = TextDecoration.None
                            ),
                            color = DgenTheme.colors.dgenWhite
                        )
                    }

                }
        }

    }


}

//Text(
//                text = "$progress",
//                style = TextStyle(
//                    fontFamily = SourceSansProFamily,
//                    color = dgenWhite,
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 32.sp,
//                    lineHeight = 32.sp,
//                    letterSpacing = 0.sp,
//                    textDecoration = TextDecoration.None
//                ),
//                color = DgenTheme.colors.dgenWhite
//            )

