package com.example.dgen_component_library

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.SourceSansProFamily
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenWhite
import com.example.dgenlibrary.ui.theme.label_fontSize

@Composable
fun AppDetailHeader(
    modifier: Modifier = Modifier,
    painter: Painter,
    category: String,
    appName: String,
    appDesc: String,
){


    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, end = 24.dp, start = 24.dp), // Adjust size as needed
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.width(100.dp)
        ) {
            BackButton(

            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Surface(
                modifier = Modifier.size(48.dp), // Adjust size as needed
                shape = CircleShape,
                border = BorderStroke(2.dp, Color.White) // White border with 4.dp thickness
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Circular Image",
                    contentScale = ContentScale.Crop, // Crop the image to fill the circle
                    modifier = Modifier.clip(CircleShape) // Clip the image to a circular shape
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = appName,
                    style = TextStyle(
                        fontFamily = SourceSansProFamily,
                        color = dgenWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None
                    ),
                    color = DgenTheme.colors.dgenWhite
                )
                Text(
                    modifier = modifier.width(200.dp),
                    text = appDesc,
                    style = TextStyle(
                        fontFamily = SourceSansProFamily,
                        color = dgenWhite,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        lineHeight = 15.sp,
                        letterSpacing = 0.sp,
                        textDecoration = TextDecoration.None,
                        textAlign = TextAlign.Center
                    ),
                    color = DgenTheme.colors.dgenWhite
                )
            }


            Surface(
                color = dgenRed,
                modifier = Modifier.padding(top=12.dp).height(20.dp), // Adjust size as needed
                shape = RoundedCornerShape(0.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center // Basiszentrierung
                ) {
                    Text(
                        modifier = Modifier

                            .padding(horizontal = 6.dp),
                        text = category,
                        style = TextStyle(
                            fontFamily = SourceSansProFamily,
                            color = dgenWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        ),
                        color = DgenTheme.colors.dgenWhite
                    )
                }

            }
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.width(100.dp).padding(top = 12.dp)
        ) {
            InstallButton (
                modifier = Modifier,
                textStyle = TextStyle(
                    fontFamily = SourceSansProFamily,
                    color = dgenBlack,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    letterSpacing = 1.sp,
                    textDecoration = TextDecoration.None
                )
            ) {
                Text("Install".uppercase())
            }
        }



    }
}

@Preview
@Composable
fun PreviewAppDetailHeader(){
    val context = LocalContext.current
    AppDetailHeader(
        painter = painterResource(R.drawable.coinbase2),
        category = "MARKETPLACE",
        appName = "Coinbase",
        appDesc = "Buy and sell Bitcoin, Ethereum, and more with trust.",

    )
}