package com.example.dgen_component_library

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dgenlibrary.PrimaryButton
import com.example.dgenlibrary.ui.theme.DgenTheme

@Composable
fun InstallButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    textStyle: TextStyle = DgenTheme.typography.button,
    content: @Composable RowScope.() -> Unit
){

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = DgenTheme.colors.dgenWhite,
            contentColor = DgenTheme.colors.dgenBlack,
            ),
        shape = CircleShape,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = DgenTheme.elevation.default,
            pressedElevation = DgenTheme.elevation.pressed
            /* disabledElevation = 0.dp */
        ),
        onClick = onClick,
        modifier = modifier.height(24.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
        content = {
            ProvideTextStyle(
                value = textStyle
            ) {
                content()
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewInstallButton(){
    InstallButton(){
        Text(text = "Mint")
    }
}