package com.example.firstcomposeproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme


import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Black900,
    secondary = Black900,
    onPrimary = Color.White,
    onSecondary = Black500,
//    surface = Color.Blue,
//    inverseOnSurface = Color.White,
//    inverseSurface = Color.Green,
//    surface = Color.Magenta,
//    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.White,
    onPrimary = Black900,
    onSecondary = Black500,

//    surface = Color.Blue,
//    inverseOnSurface = Color.White,
//    inverseSurface = Color.Green,
//    onSurface = Color.White,
)

@Composable
fun FirstComposeProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}