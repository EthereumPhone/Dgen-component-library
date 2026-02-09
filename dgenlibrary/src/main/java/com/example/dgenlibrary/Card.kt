package com.example.dgenlibrary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.dgenBurgendy
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.dgenWhite
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import androidx.compose.animation.core.keyframesWithSpline
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@Composable
fun Card(
    isFirst: Boolean = true,
    modifier: Modifier = Modifier,
    rotation: Float = 0f,
    frontSide: @Composable () -> Unit = {},
    backSide: @Composable () -> Unit = {},
    primaryColor: Color = dgenTurqoise,
    secondaryColor: Color = dgenOcean,
){
    val baseColor by animateColorAsState(if (isFirst) secondaryColor else dgenBlack, tween(300))

    Surface(
        color = baseColor,
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .aspectRatio(16f / 9f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp))
            .border(2.dp, primaryColor, RoundedCornerShape(0.dp))
    ) {
        Box(
            Modifier.fillMaxSize()
        ) {
            val frontVisible = rotation < 90f
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = frontVisible,
                enter = fadeIn(tween(300)),
                exit  = fadeOut(tween(300))
            ) { frontSide() }

            val backVisible = rotation > 90f
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = backVisible,
                enter = fadeIn(tween(300)),
                exit  = fadeOut(tween(300))
            ) { backSide() }
        }
    }
}

fun formatSmart(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString() // No decimal part, show as integer
    } else {
        String.format(Locale.US, "%.2f", value) // Show with two decimals
    }
}

fun abbreviateNumber(value: Double): String {
    val suffixes = arrayOf("", "K", "M", "B")
    var num = value
    var index = 0

    while (num >= 1000 && index < suffixes.size - 1) {
        num /= 1000
        index++
    }

    // Ensure US number format with commas and dots
    val symbols = DecimalFormatSymbols(Locale.US)
    val decimalFormat = DecimalFormat("#,##0.##", symbols)

    return "${decimalFormat.format(num)}${suffixes[index]}"
}

@Preview
@Composable
fun PreviewCard(){
    Card(
        frontSide = {
//            IdleView(
//                amount = 0.13,
//                tokenName = "USDC",
//                fiatAmount = 209.47,
//                icon = R.drawable.usdc,
//            )
        },
        primaryColor = dgenTurqoise,
        secondaryColor = dgenOcean
    )
}

@Preview(device = "spec:width=720px,height=720px,dpi=240", name = "DDevice")
@Composable
fun CardPreviewDDevice(){
    Card(
        frontSide = {
//            IdleView(
//                amount = 0.13,
//                tokenName = "USDC",
//                fiatAmount = 209.47,
//                icon = R.drawable.usdc,
//                modifier = TODO(),
//                navigateToSend = TODO(),
//                enableSend = TODO(),
//                primaryColor = TODO(),
//            )
        },
        primaryColor = dgenTurqoise,
        secondaryColor = dgenOcean
    )
}


data class Asset(
    val amount: Double,
    val tokenName: String,
    val fiatAmount: Double,
    val chainList: List<Int>,
    val backgroundColor: Color,
    val icon: Int,
)
enum class TOKENACTION{
    IDLE,
    SEND,
    SWAP,
    GET
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun IsolatedSendCardView(
    cardId: String,
    asset: Asset,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){

    var rotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )

    var isAnimating by remember { mutableStateOf(false) }
    var hasClicked by remember { mutableStateOf(false) } // Ensures animation does not run on first launch


    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 0.7f else 0.85f,
        animationSpec = tween(600, 200),
        label = "ScaleAnimation"
    )
    var translateY by remember { mutableStateOf(0f) }


    var cardState by remember { mutableStateOf(TOKENACTION.IDLE) }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            
                    Card(
//                        cardId = cardId,
                        modifier = Modifier

                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                rotationY = rotation
                                translationY = -translateY

                                cameraDistance = 12f * density
                            },
                        frontSide = {
                            SendCardView(
                                amount = asset.amount,
                                tokenName = asset.tokenName
                            )
                        },
//                        backgroundColor = asset.backgroundColor,
                        rotation = rotation,
                        backSide = {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        rotationY = -180f
                                    },
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    "SEND",
                                    style = TextStyle(
                                        fontFamily = PitagonsSans,
                                        color = dgenWhite,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 128.sp,
                                        lineHeight = 128.sp,
                                        letterSpacing = 0.sp,
                                        textDecoration = TextDecoration.None
                                    ),
                                )


                            }

//                        }

                        },
//                        sharedTransitionScope = sharedTransitionScope,
//                        animatedVisibilityScope = animatedVisibilityScope
                    )


            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {


                    CircleButton(
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = "Send Icon"
                            )
                        },
                        containerColor = Color.Transparent,
                        contentColor = dgenRed ,
                        buttonSize = 48.dp,
                        onClick = {

                            rotated = false

                            isAnimating = false
                            hasClicked = true

                            cardState = TOKENACTION.IDLE
                            onNavigateBack()
                        }
                    )



                    CircleButton(
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(R.drawable.baseline_arrow_outward_24),
                                contentDescription = "Send Icon"
                            )
                        },
                        containerColor = if(cardState == TOKENACTION.SEND) dgenTurqoise else Color.Transparent,
                        contentColor = if(cardState == TOKENACTION.SEND)  dgenOcean else dgenTurqoise ,
                        buttonSize = 48.dp,
                        onClick = {
                            rotated = !rotated
                            cardState = TOKENACTION.SEND
                        }
                    )

            }
        }
    }

}

//@SuppressLint("UnusedCrossfadeTargetStateParameter")
//@Preview(
//    showBackground = true,
//    widthDp = 447,
//    heightDp = 447,
//)
//@Composable
//fun IsolatedCardViewPreview(){
//    IsolatedCardView()
//}


