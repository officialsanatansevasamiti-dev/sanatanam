package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SwarnaGold,
    onPrimary = TempleCharcoal,
    primaryContainer = BhagwaDark,
    onPrimaryContainer = SwarnaGoldLight,
    secondary = BhagwaLight,
    onSecondary = TempleCharcoal,
    secondaryContainer = TempleSurfaceVariantDark,
    onSecondaryContainer = SwarnaGoldLight,
    tertiary = KumkumRedLight,
    onTertiary = Color.White,
    background = TempleCharcoal,
    onBackground = TempleOnSurfaceDark,
    surface = TempleSurfaceDark,
    onSurface = TempleOnSurfaceDark,
    surfaceVariant = TempleSurfaceVariantDark,
    onSurfaceVariant = Color(0xFFD7CCC8)
)

private val LightColorScheme = lightColorScheme(
    primary = BhagwaPrimary,
    onPrimary = Color.White,
    primaryContainer = BhagwaContainer,
    onPrimaryContainer = OnBhagwaContainer,
    secondary = SwarnaGoldDark,
    onSecondary = Color.White,
    secondaryContainer = ChandanCard,
    onSecondaryContainer = OnBhagwaContainer,
    tertiary = KumkumRed,
    onTertiary = Color.White,
    background = ChandanCream,
    onBackground = Color(0xFF2E1A11),
    surface = Color.White,
    onSurface = Color(0xFF2E1A11),
    surfaceVariant = ChandanCard,
    onSurfaceVariant = Color(0xFF5D4037)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep sacred saffron/gold theme as default for brand consistency
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
