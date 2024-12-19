package com.example.dgenlibrary.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.R

val SourceSansProFamily = FontFamily(
    Font(R.font.sourcesans3_light, FontWeight.Light),
    Font(R.font.sourcesans3_regular, FontWeight.Normal),
    Font(R.font.sourcesans3_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.sourcesans3_medium, FontWeight.Medium),
    Font(R.font.sourcesans3_semibold, FontWeight.SemiBold),
    Font(R.font.sourcesans3_bold, FontWeight.Bold)
)

//val DgenTypography = Typography(
//    bodyMedium = TextStyle(
//        fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp/*...*/
//    ),
//    bodyLarge = TextStyle(
//        fontFamily = fontFamily,
//        fontWeight = FontWeight.Bold,
//        letterSpacing = 2.sp,
//        /*...*/
//    ),
//    headlineMedium = TextStyle(
//        fontFamily = fontFamily, fontWeight = FontWeight.SemiBold/*...*/
//    ),
//    /*...*/
//)