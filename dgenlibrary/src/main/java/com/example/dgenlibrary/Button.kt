package com.example.dgenlibrary

import android.graphics.drawable.Drawable
import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.ui.theme.DgenTheme
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenGray
import com.example.dgenlibrary.ui.theme.dgenOcean
import com.example.dgenlibrary.ui.theme.dgenTurqoise

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = DgenTheme.colors.dgenWhite,
                contentColor = DgenTheme.colors.dgenBlack,


            ),
            shape = RoundedCornerShape(0.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = DgenTheme.elevation.default,
                pressedElevation = DgenTheme.elevation.pressed
                /* disabledElevation = 0.dp */
            ),
            onClick = onClick,
            modifier = modifier,
            content = {
                ProvideTextStyle(
                    value = DgenTheme.typography.button
                ) {
                    content()
                }
            }
        )

    
}

@Preview(showBackground = true)
@Composable
fun PreviewPrimaryButton(){
    PrimaryButton(){
        Text(text = "Mint")
    }
}


@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    buttonSize: Dp = 40.dp,
    containerColor: Color = dgenOcean,
    contentColor: Color = dgenTurqoise,
) {
    IconButton(
        modifier = modifier.size(buttonSize),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = dgenGray,
            disabledContentColor = dgenBlack
        ),
        onClick = onClick,
    ){
        icon()
    }


}

@Preview()
@Composable
fun PreviewCircleButton(){
    CircleButton(
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.qr_code),
                contentDescription = "QR-Code"
            )
        },

        onClick = {}
    )
}