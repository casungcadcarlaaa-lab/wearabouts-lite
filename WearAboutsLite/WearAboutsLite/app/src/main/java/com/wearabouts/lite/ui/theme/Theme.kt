package com.wearabouts.lite.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Surface,
    secondary = Accent,
    onSecondary = Surface,
    background = Background,
    surface = Surface,
    onSurface = TextPrimary,
    onBackground = TextPrimary,
    surfaceVariant = Color(0xFFF5F5F7),
    onSurfaceVariant = Color(0xFF1A365D)
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Accent,
    onSecondary = Color.White,
    background = Color.Black,
    surface = Color.Black, // Set pure black for dark mode surfaces as requested
    onSurface = Color.White,
    onBackground = Color.White,
    surfaceVariant = Color(0xFF1A1A1A), // Slightly lighter gray for nested elements
    onSurfaceVariant = Color.White
)

@Composable
fun WearAboutsTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
