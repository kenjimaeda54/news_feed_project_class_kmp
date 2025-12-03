package com.newsandfeed.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.newsandfeed.R

val googleFonts = FontFamily(
    Font(R.font.google_font),
)

val typography = Typography(
    titleLarge = TextStyle(
        fontSize = 17.sp,
        fontFamily = googleFonts,
        fontWeight = FontWeight.Bold,
    ),
    bodyMedium = TextStyle(
        fontSize = 15.sp,
        fontFamily = googleFonts,
        fontWeight = FontWeight.Normal,
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = googleFonts,
        fontWeight = FontWeight.Thin,
    )
)