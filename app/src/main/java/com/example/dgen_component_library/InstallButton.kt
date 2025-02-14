package com.example.dgen_component_library

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.PrimaryButton
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.dgenWhite

@Composable
fun InstallButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    textStyle: TextStyle = DgenTheme.typography.button,
    text: String,
){
    var isLoading by remember { mutableStateOf(false) }





    val progressAlpha by animateFloatAsState(
        targetValue = if (isLoading) 1f else 0f,
        animationSpec = tween(durationMillis = 200, delayMillis = 150)
    )
    val textAlpha by animateFloatAsState(
        targetValue = if (isLoading) 0f else 1f,
        animationSpec = tween(durationMillis = 300)
    )
    val buttonPadding by animateDpAsState(
        targetValue = if (isLoading) 0.dp else 10.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isLoading) Color.Transparent else dgenWhite,
        animationSpec = tween(durationMillis = 100)
    )

    AnimatedContent(
        targetState = isLoading,
        transitionSpec = {
            scaleIn(animationSpec = tween(150, 150)) togetherWith
                    scaleOut(animationSpec = tween(150)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                // Shrink vertically first.
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                // Expand horizontally first.
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }
        }, label = "size transform"
    ){ targetLoading ->
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = if(isLoading) { Color.Transparent } else { DgenTheme.colors.dgenWhite  },
            ),
            shape = CircleShape,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = DgenTheme.elevation.default,
                pressedElevation = DgenTheme.elevation.pressed
                /* disabledElevation = 0.dp */
            ),
            onClick = {
                onClick
                isLoading = !isLoading
            },
            modifier = modifier.animateContentSize().height(25.dp).width(if (isLoading) 25.dp else 100.dp,),
            contentPadding = PaddingValues(horizontal = buttonPadding, vertical = 0.dp),
            content = {
                ProvideTextStyle(
                    value = textStyle
                ) {


                    if (targetLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),//.alpha(progressAlpha),
                            color = dgenWhite,
                            strokeWidth = 2.dp,

                            )
                    } else {
                        Text(text.uppercase(),modifier = Modifier)//.alpha(textAlpha))
                    }



                }
            }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewInstallButton(){
//    InstallButton(){
//        Text(text = "Mint")
//    }
}