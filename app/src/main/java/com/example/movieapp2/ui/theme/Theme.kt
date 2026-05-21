package com.example.movieapp2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkPalette = darkColorScheme(
    primary = Color(0xFFFF9800),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFCC7700),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFFF9800),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightPalette = lightColorScheme(
    primary = Color(0xFFF57C00),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFF9800),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFFF57C00),
    onSecondary = Color.White,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun MovieAppTheme(
    darkMode: Boolean = isSystemInDarkTheme(),
    useDynamicColors: Boolean = false,
    appContent: @Composable () -> Unit
) {
    val colorScheme = when {
        useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkMode) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkMode -> DarkPalette
        else -> LightPalette
    }
    val currentView = LocalView.current
    if (!currentView.isInEditMode) {
        SideEffect {
            val window = (currentView.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, currentView).isAppearanceLightStatusBars = !darkMode
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = appContent
    )
}