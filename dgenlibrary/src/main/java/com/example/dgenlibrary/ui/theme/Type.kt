package com.example.dgenlibrary.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.R

val DigitalNumbers = FontFamily(
    Font(R.font.digital_numbers, FontWeight.Normal),
)

val SpaceMono = FontFamily(
    Font(R.font.spacemono_bold, FontWeight.Bold),
)

val SourceSans = FontFamily(
    Font(R.font.sourcesans3_bold, FontWeight.Bold),
    Font(R.font.sourcesans3_medium, FontWeight.Medium),
    Font(R.font.sourcesans3_regular, FontWeight.Normal),
    Font(R.font.sourcesans3_semibold, FontWeight.SemiBold),
    Font(R.font.sourcesans3_light, FontWeight.Light),

    Font(R.font.sourcesans3_black, FontWeight.Black),
    Font(R.font.sourcesans3_extralight, FontWeight.ExtraLight),
    Font(R.font.pitagonsanstext_italic, style= FontStyle.Italic ),
)


val PitagonsSans = FontFamily(
    Font(R.font.pitagonsanstext_bold, FontWeight.Bold),
    Font(R.font.pitagonsanstext_medium, FontWeight.Medium),
    Font(R.font.pitagonsanstext_regular, FontWeight.Normal),
    Font(R.font.pitagonsanstext_semibold, FontWeight.SemiBold),
    Font(R.font.pitagonsanstext_light, FontWeight.Light),
)
