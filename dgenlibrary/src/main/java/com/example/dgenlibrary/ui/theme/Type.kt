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

val MonomaniacOneFamily = FontFamily(
    Font(R.font.monomaniacone_regular, FontWeight.Normal),
)

val Exo2Family = FontFamily(
    Font(R.font.exo2_medium, FontWeight.Normal),
    Font(R.font.exo2_semibold, FontWeight.SemiBold),
)

val SpaceMono = FontFamily(
    Font(R.font.spacemono_bold, FontWeight.Bold),
)


val PitagonsSans = FontFamily(
    Font(R.font.pitagonsanstext_bold, FontWeight.Bold),
    Font(R.font.pitagonsanstext_medium, FontWeight.Medium),
    Font(R.font.pitagonsanstext_regular, FontWeight.Normal),
    Font(R.font.pitagonsanstext_semibold, FontWeight.SemiBold),
    Font(R.font.pitagonsanstext_light, FontWeight.Light),
)
