package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryYellow,
    onPrimary = TextPrimary,
    primaryContainer = DeepPink,
    onPrimaryContainer = Color.White,
    secondary = PrimaryPink,
    onSecondary = Color.White,
    secondaryContainer = DarkCard,
    onSecondaryContainer = LightPink,
    tertiary = AccentRed,
    onTertiary = Color.White,
    background = DarkBackground,
    onBackground = Color.White,
    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = DarkCard,
    onSurfaceVariant = Color(0xFFD6D0DA)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryPink,
    onPrimary = Color.White,
    primaryContainer = LightYellow,
    onPrimaryContainer = TextPrimary,
    secondary = PrimaryYellow,
    onSecondary = TextPrimary,
    secondaryContainer = LightPink.copy(alpha = 0.2f),
    onSecondaryContainer = DeepPink,
    tertiary = AccentRed,
    onTertiary = Color.White,
    background = BackgroundCream,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFF9F5EE),
    onSurfaceVariant = TextSecondary
)

@Composable
fun MulShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
