package com.newsandfeed.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007BFF),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF5D5D5D),
    onSecondary = Color(0xFFFFFFFF),
    //por padrão o Scaffold usa background para a cor de fundo
    background = Color(0xFFF8F9FA),
    //cor do texto principal
    onBackground = Color(0xFF1E1E1E),
    surface = Color(0xFFF8F9FA),
    onSurface = Color(0xFF1E1E1E),
    surfaceContainer = Color(0xFFEBEFF2),
    tertiary = Color(0xFF03DAC5),
    onTertiary = Color(0xFF000000),
    error = Color(0xFFDC3545),
    onError = Color(0xFFFFFFFF),
    outline = Color(0xFFBBBBBB),
    outlineVariant = Color(0xFFE0E0E0)
)