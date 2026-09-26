package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Mul Shop Brand Identity
val PrimaryYellow = Color(0xFFFFD814)
val LightYellow = Color(0xFFFFF1A8)
val DarkYellow = Color(0xFFE5B800)
val PrimaryPink = Color(0xFFEC0877)
val LightPink = Color(0xFFFF3D9A)
val DeepPink = Color(0xFFC2005C)
val AccentRed = Color(0xFFE90046)
val BackgroundCream = Color(0xFFFFFAF0)
val SurfaceWhite = Color(0xFFFFFFFF)
val CardBackground = Color(0xFFFFFFFF)
val TextPrimary = Color(0xFF15101A)
val TextSecondary = Color(0xFF74717A)
val SuccessGreen = Color(0xFF16A05D)
val RatingGold = Color(0xFFFFA000)
val DividerColor = Color(0xFFEDE7DF)
val ChipSelected = Color(0xFFFFE082)
val BadgeRed = Color(0xFFE90046)
val DarkBackground = Color(0xFF15101A)
val DarkSurface = Color(0xFF231C29)
val DarkCard = Color(0xFF2C2433)

// Brand Gradients
val MulShopHeaderGradient = Brush.horizontalGradient(
    colors = listOf(
        PrimaryYellow,
        Color(0xFFFF851B),
        PrimaryPink
    )
)

val MulShopHeroGradient = Brush.verticalGradient(
    colors = listOf(
        PrimaryYellow.copy(alpha = 0.9f),
        PrimaryPink.copy(alpha = 0.85f)
    )
)

val DealTagGradient = Brush.horizontalGradient(
    colors = listOf(
        AccentRed,
        PrimaryPink
    )
)

val GoldTagGradient = Brush.horizontalGradient(
    colors = listOf(
        PrimaryYellow,
        Color(0xFFFFB300)
    )
)
