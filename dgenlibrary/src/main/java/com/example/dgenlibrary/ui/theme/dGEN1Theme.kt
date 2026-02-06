package com.example.dgenlibrary.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Immutable
data class DgenColors(
    val dgenBlack: Color,
    val dgenWhite: Color,
    val dgenGray: Color,
    val dgenGunMetal: Color,
    val dgenRed: Color,
    val dgenDarkRed: Color,
    val dgenGreen: Color,
    val dgenAqua: Color,
    val dgenOrche: Color,
    val dgenOcean: Color,
    val dgenTurqoise: Color,
    val dgenBurgendy: Color,

    val lazerBurn: Color,
    val lazerCore: Color,

    val terminalHack: Color,
    val terminalCore: Color,

    val orcheAsh: Color,
    val orcheCore: Color,

    val oceanAbyss: Color,
    val oceanCore: Color,

    val gunMetalForge: Color,
    val gunMetalCore: Color,
)

@Immutable
data class DgenTypography(
    val header0: TextStyle,
    val header1: TextStyle,
    val header2: TextStyle,
    val header3: TextStyle,
    val header4: TextStyle,
    val body2: TextStyle,
    val body1: TextStyle,
    val button: TextStyle,
    val label: TextStyle,
    val digitalNumber: TextStyle
)

@Immutable
data class DgenFontSize(
    val header0: TextUnit,
    val header1: TextUnit,
    val header2: TextUnit,
    val header3: TextUnit,
    val header4: TextUnit,
    val body2: TextUnit,
    val body1: TextUnit,
    val button: TextUnit,
    val label: TextUnit,
    val tag: TextUnit,
)

@Immutable
data class DgenElevation(
    val default: Dp,
    val pressed: Dp
)


@Immutable
data class DgenDimension(
    val IconSize: Dp,
    val IconButtonSize: Dp,
)


val LocalCustomColors = staticCompositionLocalOf {
    DgenColors(
        dgenBlack = Color.Unspecified,
        dgenWhite = Color.Unspecified,
        dgenGray = Color.Unspecified,
        dgenRed = Color.Unspecified,
        dgenDarkRed = Color.Unspecified,
        dgenGreen = Color.Unspecified,
        dgenAqua = Color.Unspecified,
        dgenOrche = Color.Unspecified,
        dgenOcean = Color.Unspecified,
        dgenTurqoise = Color.Unspecified,
        dgenBurgendy = Color.Unspecified,
        dgenGunMetal = Color.Unspecified,
        lazerBurn = Color.Unspecified,
        lazerCore = Color.Unspecified,
        terminalHack = Color.Unspecified,
        terminalCore = Color.Unspecified,
        orcheAsh = Color.Unspecified,
        orcheCore = Color.Unspecified,
        oceanAbyss = Color.Unspecified,
        oceanCore = Color.Unspecified,
        gunMetalForge = Color.Unspecified,
        gunMetalCore = Color.Unspecified
    )
}
val LocalCustomTypography = staticCompositionLocalOf {
    DgenTypography(
        header0 = TextStyle.Default,
        header1 = TextStyle.Default,
        header2 = TextStyle.Default,
        header3 = TextStyle.Default,
        header4 = TextStyle.Default,
        body2 = TextStyle.Default,
        body1 = TextStyle.Default,
        button = TextStyle.Default,
        label = TextStyle.Default,
        digitalNumber = TextStyle.Default,
    )
}
val LocalCustomElevation = staticCompositionLocalOf {
    DgenElevation(
        default = Dp.Unspecified,
        pressed = Dp.Unspecified
    )
}

val LocalCustomDimension = staticCompositionLocalOf {
    DgenDimension(
        IconSize = Dp.Unspecified,
        IconButtonSize = Dp.Unspecified
    )
}

val LocalCustomFontSize = staticCompositionLocalOf {
    DgenFontSize(
        header0 = TextUnit.Unspecified,
        header1 = TextUnit.Unspecified,
        header2 = TextUnit.Unspecified,
        header3 = TextUnit.Unspecified,
        header4 = TextUnit.Unspecified,
        body2 = TextUnit.Unspecified,
        body1 = TextUnit.Unspecified,
        button = TextUnit.Unspecified,
        label = TextUnit.Unspecified,
        tag = TextUnit.Unspecified
    )
}

@Composable
fun DgenTheme(
    content: @Composable () -> Unit
) {
    val customColors = DgenColors(
        dgenBlack = dgenBlack,
        dgenWhite = dgenWhite,
        dgenGray = dgenGray,
        dgenRed = dgenRed,
        dgenDarkRed = dgenDarkRed,
        dgenGreen = dgenGreen,
        dgenAqua = dgenAqua,
        dgenOrche = dgenOrche,
        dgenOcean = dgenOcean,
        dgenTurqoise = dgenTurqoise,
        dgenBurgendy = dgenBurgendy,
        dgenGunMetal = dgenGunMetal,
        lazerBurn = lazerBurn,
        lazerCore = lazerCore,
        terminalHack = terminalHack,
        terminalCore = terminalCore,
        orcheAsh = orcheAsh,
        orcheCore = orcheCore,
        oceanAbyss = oceanAbyss,
        oceanCore = oceanCore,
        gunMetalForge = gunMetalForge,
        gunMetalCore = gunMetalCore
    )
    val customTypography = DgenTypography(
        header0 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = header0_fontSize,
            lineHeight = header0_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        header1 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = header1_fontSize,
            lineHeight = header1_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        header2 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = header2_fontSize,
            lineHeight = header2_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        header3 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = header3_fontSize,
            lineHeight = header3_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        header4 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = header4_fontSize,
            lineHeight = header4_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        body2 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = body2_fontSize,
            lineHeight = body2_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        body1 = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenWhite,
            fontWeight = FontWeight.SemiBold,
            fontSize = body1_fontSize,
            lineHeight = body1_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        button = TextStyle(
            fontFamily = PitagonsSans,
            color = dgenBlack,
            fontWeight = FontWeight.SemiBold,
            fontSize = button_fontSize,
            lineHeight = button_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        label = TextStyle(
            fontFamily = SpaceMono,
            color = dgenWhite,
            fontWeight = FontWeight.Normal,
            fontSize = label_fontSize,
            lineHeight = label_fontSize,
            letterSpacing = 0.sp,
            textDecoration = TextDecoration.None
        ),
        digitalNumber = TextStyle(
            fontFamily = DigitalNumbers,
            color = dgenWhite,
            fontWeight = FontWeight.Normal,
            fontSize = header2_fontSize,
            textAlign = TextAlign.Center,
            letterSpacing = 60.sp * -0.15f
        ),
    )
    val customElevation = DgenElevation(
        default = 4.dp,
        pressed = 8.dp
    )

    val customDimension = DgenDimension(
        IconSize = IconSize,
        IconButtonSize = IconButtonSize
    )

    val customFontSize = DgenFontSize(
        tag = tag_fontSize,
        label = label_fontSize,
        button = button_fontSize,
        body1 = body1_fontSize,
        body2 = body2_fontSize,
        header0 = header0_fontSize,
        header1 = header1_fontSize,
        header2 = header2_fontSize,
        header3 = header3_fontSize,
        header4 = header4_fontSize
    )

    CompositionLocalProvider(
        LocalCustomColors provides customColors,
        LocalCustomTypography provides customTypography,
        LocalCustomElevation provides customElevation,
        LocalCustomDimension provides customDimension,
        LocalCustomFontSize provides customFontSize,
        content = content
    )
}

// Use with eg. CustomTheme.elevation.small
object DgenTheme {
    val colors: DgenColors
        @Composable
        get() = LocalCustomColors.current
    val typography: DgenTypography
        @Composable
        get() = LocalCustomTypography.current
    val elevation: DgenElevation
        @Composable
        get() = LocalCustomElevation.current
    val dimensions: DgenDimension
        @Composable
        get() = LocalCustomDimension.current
    val fontSize: DgenFontSize
        @Composable
        get() = LocalCustomFontSize.current
}