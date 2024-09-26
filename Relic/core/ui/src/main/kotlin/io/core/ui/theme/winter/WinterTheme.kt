package io.core.ui.theme.winter

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

object WinterTheme {
    private val lightScheme = lightColorScheme(
        primary = WinterColors.primaryLight,
        onPrimary = WinterColors.onPrimaryLight,
        primaryContainer = WinterColors.primaryContainerLight,
        onPrimaryContainer = WinterColors.onPrimaryContainerLight,
        secondary = WinterColors.secondaryLight,
        onSecondary = WinterColors.onSecondaryLight,
        secondaryContainer = WinterColors.secondaryContainerLight,
        onSecondaryContainer = WinterColors.onSecondaryContainerLight,
        tertiary = WinterColors.tertiaryLight,
        onTertiary = WinterColors.onTertiaryLight,
        tertiaryContainer = WinterColors.tertiaryContainerLight,
        onTertiaryContainer = WinterColors.onTertiaryContainerLight,
        error = WinterColors.errorLight,
        onError = WinterColors.onErrorLight,
        errorContainer = WinterColors.errorContainerLight,
        onErrorContainer = WinterColors.onErrorContainerLight,
        background = WinterColors.backgroundLight,
        onBackground = WinterColors.onBackgroundLight,
        surface = WinterColors.surfaceLight,
        onSurface = WinterColors.onSurfaceLight,
        surfaceVariant = WinterColors.surfaceVariantLight,
        onSurfaceVariant = WinterColors.onSurfaceVariantLight,
        outline = WinterColors.outlineLight,
        outlineVariant = WinterColors.outlineVariantLight,
        scrim = WinterColors.scrimLight,
        inverseSurface = WinterColors.inverseSurfaceLight,
        inverseOnSurface = WinterColors.inverseOnSurfaceLight,
        inversePrimary = WinterColors.inversePrimaryLight,
        surfaceDim = WinterColors.surfaceDimLight,
        surfaceBright = WinterColors.surfaceBrightLight,
        surfaceContainerLowest = WinterColors.surfaceContainerLowestLight,
        surfaceContainerLow = WinterColors.surfaceContainerLowLight,
        surfaceContainer = WinterColors.surfaceContainerLight,
        surfaceContainerHigh = WinterColors.surfaceContainerHighLight,
        surfaceContainerHighest = WinterColors.surfaceContainerHighestLight,
    )

    private val darkScheme = darkColorScheme(
        primary = WinterColors.primaryDark,
        onPrimary = WinterColors.onPrimaryDark,
        primaryContainer = WinterColors.primaryContainerDark,
        onPrimaryContainer = WinterColors.onPrimaryContainerDark,
        secondary = WinterColors.secondaryDark,
        onSecondary = WinterColors.onSecondaryDark,
        secondaryContainer = WinterColors.secondaryContainerDark,
        onSecondaryContainer = WinterColors.onSecondaryContainerDark,
        tertiary = WinterColors.tertiaryDark,
        onTertiary = WinterColors.onTertiaryDark,
        tertiaryContainer = WinterColors.tertiaryContainerDark,
        onTertiaryContainer = WinterColors.onTertiaryContainerDark,
        error = WinterColors.errorDark,
        onError = WinterColors.onErrorDark,
        errorContainer = WinterColors.errorContainerDark,
        onErrorContainer = WinterColors.onErrorContainerDark,
        background = WinterColors.backgroundDark,
        onBackground = WinterColors.onBackgroundDark,
        surface = WinterColors.surfaceDark,
        onSurface = WinterColors.onSurfaceDark,
        surfaceVariant = WinterColors.surfaceVariantDark,
        onSurfaceVariant = WinterColors.onSurfaceVariantDark,
        outline = WinterColors.outlineDark,
        outlineVariant = WinterColors.outlineVariantDark,
        scrim = WinterColors.scrimDark,
        inverseSurface = WinterColors.inverseSurfaceDark,
        inverseOnSurface = WinterColors.inverseOnSurfaceDark,
        inversePrimary = WinterColors.inversePrimaryDark,
        surfaceDim = WinterColors.surfaceDimDark,
        surfaceBright = WinterColors.surfaceBrightDark,
        surfaceContainerLowest = WinterColors.surfaceContainerLowestDark,
        surfaceContainerLow = WinterColors.surfaceContainerLowDark,
        surfaceContainer = WinterColors.surfaceContainerDark,
        surfaceContainerHigh = WinterColors.surfaceContainerHighDark,
        surfaceContainerHighest = WinterColors.surfaceContainerHighestDark,
    )

    private val mediumContrastLightColorScheme = lightColorScheme(
        primary = WinterColors.primaryLightMediumContrast,
        onPrimary = WinterColors.onPrimaryLightMediumContrast,
        primaryContainer = WinterColors.primaryContainerLightMediumContrast,
        onPrimaryContainer = WinterColors.onPrimaryContainerLightMediumContrast,
        secondary = WinterColors.secondaryLightMediumContrast,
        onSecondary = WinterColors.onSecondaryLightMediumContrast,
        secondaryContainer = WinterColors.secondaryContainerLightMediumContrast,
        onSecondaryContainer = WinterColors.onSecondaryContainerLightMediumContrast,
        tertiary = WinterColors.tertiaryLightMediumContrast,
        onTertiary = WinterColors.onTertiaryLightMediumContrast,
        tertiaryContainer = WinterColors.tertiaryContainerLightMediumContrast,
        onTertiaryContainer = WinterColors.onTertiaryContainerLightMediumContrast,
        error = WinterColors.errorLightMediumContrast,
        onError = WinterColors.onErrorLightMediumContrast,
        errorContainer = WinterColors.errorContainerLightMediumContrast,
        onErrorContainer = WinterColors.onErrorContainerLightMediumContrast,
        background = WinterColors.backgroundLightMediumContrast,
        onBackground = WinterColors.onBackgroundLightMediumContrast,
        surface = WinterColors.surfaceLightMediumContrast,
        onSurface = WinterColors.onSurfaceLightMediumContrast,
        surfaceVariant = WinterColors.surfaceVariantLightMediumContrast,
        onSurfaceVariant = WinterColors.onSurfaceVariantLightMediumContrast,
        outline = WinterColors.outlineLightMediumContrast,
        outlineVariant = WinterColors.outlineVariantLightMediumContrast,
        scrim = WinterColors.scrimLightMediumContrast,
        inverseSurface = WinterColors.inverseSurfaceLightMediumContrast,
        inverseOnSurface = WinterColors.inverseOnSurfaceLightMediumContrast,
        inversePrimary = WinterColors.inversePrimaryLightMediumContrast,
        surfaceDim = WinterColors.surfaceDimLightMediumContrast,
        surfaceBright = WinterColors.surfaceBrightLightMediumContrast,
        surfaceContainerLowest = WinterColors.surfaceContainerLowestLightMediumContrast,
        surfaceContainerLow = WinterColors.surfaceContainerLowLightMediumContrast,
        surfaceContainer = WinterColors.surfaceContainerLightMediumContrast,
        surfaceContainerHigh = WinterColors.surfaceContainerHighLightMediumContrast,
        surfaceContainerHighest = WinterColors.surfaceContainerHighestLightMediumContrast,
    )

    private val highContrastLightColorScheme = lightColorScheme(
        primary = WinterColors.primaryLightHighContrast,
        onPrimary = WinterColors.onPrimaryLightHighContrast,
        primaryContainer = WinterColors.primaryContainerLightHighContrast,
        onPrimaryContainer = WinterColors.onPrimaryContainerLightHighContrast,
        secondary = WinterColors.secondaryLightHighContrast,
        onSecondary = WinterColors.onSecondaryLightHighContrast,
        secondaryContainer = WinterColors.secondaryContainerLightHighContrast,
        onSecondaryContainer = WinterColors.onSecondaryContainerLightHighContrast,
        tertiary = WinterColors.tertiaryLightHighContrast,
        onTertiary = WinterColors.onTertiaryLightHighContrast,
        tertiaryContainer = WinterColors.tertiaryContainerLightHighContrast,
        onTertiaryContainer = WinterColors.onTertiaryContainerLightHighContrast,
        error = WinterColors.errorLightHighContrast,
        onError = WinterColors.onErrorLightHighContrast,
        errorContainer = WinterColors.errorContainerLightHighContrast,
        onErrorContainer = WinterColors.onErrorContainerLightHighContrast,
        background = WinterColors.backgroundLightHighContrast,
        onBackground = WinterColors.onBackgroundLightHighContrast,
        surface = WinterColors.surfaceLightHighContrast,
        onSurface = WinterColors.onSurfaceLightHighContrast,
        surfaceVariant = WinterColors.surfaceVariantLightHighContrast,
        onSurfaceVariant = WinterColors.onSurfaceVariantLightHighContrast,
        outline = WinterColors.outlineLightHighContrast,
        outlineVariant = WinterColors.outlineVariantLightHighContrast,
        scrim = WinterColors.scrimLightHighContrast,
        inverseSurface = WinterColors.inverseSurfaceLightHighContrast,
        inverseOnSurface = WinterColors.inverseOnSurfaceLightHighContrast,
        inversePrimary = WinterColors.inversePrimaryLightHighContrast,
        surfaceDim = WinterColors.surfaceDimLightHighContrast,
        surfaceBright = WinterColors.surfaceBrightLightHighContrast,
        surfaceContainerLowest = WinterColors.surfaceContainerLowestLightHighContrast,
        surfaceContainerLow = WinterColors.surfaceContainerLowLightHighContrast,
        surfaceContainer = WinterColors.surfaceContainerLightHighContrast,
        surfaceContainerHigh = WinterColors.surfaceContainerHighLightHighContrast,
        surfaceContainerHighest = WinterColors.surfaceContainerHighestLightHighContrast,
    )

    private val mediumContrastDarkColorScheme = darkColorScheme(
        primary = WinterColors.primaryDarkMediumContrast,
        onPrimary = WinterColors.onPrimaryDarkMediumContrast,
        primaryContainer = WinterColors.primaryContainerDarkMediumContrast,
        onPrimaryContainer = WinterColors.onPrimaryContainerDarkMediumContrast,
        secondary = WinterColors.secondaryDarkMediumContrast,
        onSecondary = WinterColors.onSecondaryDarkMediumContrast,
        secondaryContainer = WinterColors.secondaryContainerDarkMediumContrast,
        onSecondaryContainer = WinterColors.onSecondaryContainerDarkMediumContrast,
        tertiary = WinterColors.tertiaryDarkMediumContrast,
        onTertiary = WinterColors.onTertiaryDarkMediumContrast,
        tertiaryContainer = WinterColors.tertiaryContainerDarkMediumContrast,
        onTertiaryContainer = WinterColors.onTertiaryContainerDarkMediumContrast,
        error = WinterColors.errorDarkMediumContrast,
        onError = WinterColors.onErrorDarkMediumContrast,
        errorContainer = WinterColors.errorContainerDarkMediumContrast,
        onErrorContainer = WinterColors.onErrorContainerDarkMediumContrast,
        background = WinterColors.backgroundDarkMediumContrast,
        onBackground = WinterColors.onBackgroundDarkMediumContrast,
        surface = WinterColors.surfaceDarkMediumContrast,
        onSurface = WinterColors.onSurfaceDarkMediumContrast,
        surfaceVariant = WinterColors.surfaceVariantDarkMediumContrast,
        onSurfaceVariant = WinterColors.onSurfaceVariantDarkMediumContrast,
        outline = WinterColors.outlineDarkMediumContrast,
        outlineVariant = WinterColors.outlineVariantDarkMediumContrast,
        scrim = WinterColors.scrimDarkMediumContrast,
        inverseSurface = WinterColors.inverseSurfaceDarkMediumContrast,
        inverseOnSurface = WinterColors.inverseOnSurfaceDarkMediumContrast,
        inversePrimary = WinterColors.inversePrimaryDarkMediumContrast,
        surfaceDim = WinterColors.surfaceDimDarkMediumContrast,
        surfaceBright = WinterColors.surfaceBrightDarkMediumContrast,
        surfaceContainerLowest = WinterColors.surfaceContainerLowestDarkMediumContrast,
        surfaceContainerLow = WinterColors.surfaceContainerLowDarkMediumContrast,
        surfaceContainer = WinterColors.surfaceContainerDarkMediumContrast,
        surfaceContainerHigh = WinterColors.surfaceContainerHighDarkMediumContrast,
        surfaceContainerHighest = WinterColors.surfaceContainerHighestDarkMediumContrast,
    )

    private val highContrastDarkColorScheme = darkColorScheme(
        primary = WinterColors.primaryDarkHighContrast,
        onPrimary = WinterColors.onPrimaryDarkHighContrast,
        primaryContainer = WinterColors.primaryContainerDarkHighContrast,
        onPrimaryContainer = WinterColors.onPrimaryContainerDarkHighContrast,
        secondary = WinterColors.secondaryDarkHighContrast,
        onSecondary = WinterColors.onSecondaryDarkHighContrast,
        secondaryContainer = WinterColors.secondaryContainerDarkHighContrast,
        onSecondaryContainer = WinterColors.onSecondaryContainerDarkHighContrast,
        tertiary = WinterColors.tertiaryDarkHighContrast,
        onTertiary = WinterColors.onTertiaryDarkHighContrast,
        tertiaryContainer = WinterColors.tertiaryContainerDarkHighContrast,
        onTertiaryContainer = WinterColors.onTertiaryContainerDarkHighContrast,
        error = WinterColors.errorDarkHighContrast,
        onError = WinterColors.onErrorDarkHighContrast,
        errorContainer = WinterColors.errorContainerDarkHighContrast,
        onErrorContainer = WinterColors.onErrorContainerDarkHighContrast,
        background = WinterColors.backgroundDarkHighContrast,
        onBackground = WinterColors.onBackgroundDarkHighContrast,
        surface = WinterColors.surfaceDarkHighContrast,
        onSurface = WinterColors.onSurfaceDarkHighContrast,
        surfaceVariant = WinterColors.surfaceVariantDarkHighContrast,
        onSurfaceVariant = WinterColors.onSurfaceVariantDarkHighContrast,
        outline = WinterColors.outlineDarkHighContrast,
        outlineVariant = WinterColors.outlineVariantDarkHighContrast,
        scrim = WinterColors.scrimDarkHighContrast,
        inverseSurface = WinterColors.inverseSurfaceDarkHighContrast,
        inverseOnSurface = WinterColors.inverseOnSurfaceDarkHighContrast,
        inversePrimary = WinterColors.inversePrimaryDarkHighContrast,
        surfaceDim = WinterColors.surfaceDimDarkHighContrast,
        surfaceBright = WinterColors.surfaceBrightDarkHighContrast,
        surfaceContainerLowest = WinterColors.surfaceContainerLowestDarkHighContrast,
        surfaceContainerLow = WinterColors.surfaceContainerLowDarkHighContrast,
        surfaceContainer = WinterColors.surfaceContainerDarkHighContrast,
        surfaceContainerHigh = WinterColors.surfaceContainerHighDarkHighContrast,
        surfaceContainerHighest = WinterColors.surfaceContainerHighestDarkHighContrast,
    )

    @Immutable
    data class ColorFamily(
        val color: Color,
        val onColor: Color,
        val colorContainer: Color,
        val onColorContainer: Color
    )

    val unspecified_scheme = ColorFamily(
        color = Color.Unspecified,
        onColor = Color.Unspecified,
        colorContainer = Color.Unspecified,
        onColorContainer = Color.Unspecified
    )

    @Composable
    fun AppTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable() () -> Unit
    ) {
        val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> darkScheme
            else -> lightScheme
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = WinterType.AppTypography,
            content = content
        )
    }
}