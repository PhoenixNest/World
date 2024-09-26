package io.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = mainThemeColorLight,
    primaryVariant = mainThemeColorLight,
    secondary = mainThemeColorAccent,

    // Other default colors to override
    background = mainThemeColorLight,
    surface = mainThemeColorLight,
    error = errorColorAccent,
    onPrimary = mainThemeColorLight,
    onSecondary = mainThemeColorAccent,
    onBackground = mainThemeColorLight,
    onSurface = mainThemeColorLight,
    onError = errorColorAccent
)

private val DarkColorPalette = darkColors(
    primary = mainThemeColor,
    primaryVariant = mainThemeColor,
    secondary = mainThemeColorAccent,

    // Other default colors to override
    background = mainThemeColor,
    surface = mainThemeColor,
    error = errorColorAccent,
    onPrimary = mainThemeColor,
    onSecondary = mainThemeColorAccent,
    onBackground = mainThemeColor,
    onSurface = mainThemeColor,
    onError = errorColorAccent
)

@Composable
fun RelicAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}