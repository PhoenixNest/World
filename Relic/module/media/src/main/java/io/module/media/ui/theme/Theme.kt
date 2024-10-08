package io.module.media.ui.theme

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
    onPrimary = mainThemeColorLight,
    onSecondary = mainThemeColorAccent,
    onBackground = mainThemeColorLight,
    onSurface = mainThemeColorLight
)

private val DarkColorPalette = darkColors(
    primary = mainThemeColor,
    primaryVariant = mainThemeColor,
    secondary = mainThemeColorAccent,

    // Other default colors to override
    background = mainThemeColor,
    surface = mainThemeColor,
    onPrimary = mainThemeColor,
    onSecondary = mainThemeColorAccent,
    onBackground = mainThemeColor,
    onSurface = mainThemeColor
)

@Composable
fun MediaModuleTheme(
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
        content = content
    )
}