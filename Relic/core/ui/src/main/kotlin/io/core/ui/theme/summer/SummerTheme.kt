package io.core.ui.theme.summer

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

object SummerTheme {
    private val lightScheme = lightColorScheme(
        primary = SummerColors.primaryLight,
        onPrimary = SummerColors.onPrimaryLight,
        primaryContainer = SummerColors.primaryContainerLight,
        onPrimaryContainer = SummerColors.onPrimaryContainerLight,
        secondary = SummerColors.secondaryLight,
        onSecondary = SummerColors.onSecondaryLight,
        secondaryContainer = SummerColors.secondaryContainerLight,
        onSecondaryContainer = SummerColors.onSecondaryContainerLight,
        tertiary = SummerColors.tertiaryLight,
        onTertiary = SummerColors.onTertiaryLight,
        tertiaryContainer = SummerColors.tertiaryContainerLight,
        onTertiaryContainer = SummerColors.onTertiaryContainerLight,
        error = SummerColors.errorLight,
        onError = SummerColors.onErrorLight,
        errorContainer = SummerColors.errorContainerLight,
        onErrorContainer = SummerColors.onErrorContainerLight,
        background = SummerColors.backgroundLight,
        onBackground = SummerColors.onBackgroundLight,
        surface = SummerColors.surfaceLight,
        onSurface = SummerColors.onSurfaceLight,
        surfaceVariant = SummerColors.surfaceVariantLight,
        onSurfaceVariant = SummerColors.onSurfaceVariantLight,
        outline = SummerColors.outlineLight,
        outlineVariant = SummerColors.outlineVariantLight,
        scrim = SummerColors.scrimLight,
        inverseSurface = SummerColors.inverseSurfaceLight,
        inverseOnSurface = SummerColors.inverseOnSurfaceLight,
        inversePrimary = SummerColors.inversePrimaryLight,
        surfaceDim = SummerColors.surfaceDimLight,
        surfaceBright = SummerColors.surfaceBrightLight,
        surfaceContainerLowest = SummerColors.surfaceContainerLowestLight,
        surfaceContainerLow = SummerColors.surfaceContainerLowLight,
        surfaceContainer = SummerColors.surfaceContainerLight,
        surfaceContainerHigh = SummerColors.surfaceContainerHighLight,
        surfaceContainerHighest = SummerColors.surfaceContainerHighestLight,
    )

    private val darkScheme = darkColorScheme(
        primary = SummerColors.primaryDark,
        onPrimary = SummerColors.onPrimaryDark,
        primaryContainer = SummerColors.primaryContainerDark,
        onPrimaryContainer = SummerColors.onPrimaryContainerDark,
        secondary = SummerColors.secondaryDark,
        onSecondary = SummerColors.onSecondaryDark,
        secondaryContainer = SummerColors.secondaryContainerDark,
        onSecondaryContainer = SummerColors.onSecondaryContainerDark,
        tertiary = SummerColors.tertiaryDark,
        onTertiary = SummerColors.onTertiaryDark,
        tertiaryContainer = SummerColors.tertiaryContainerDark,
        onTertiaryContainer = SummerColors.onTertiaryContainerDark,
        error = SummerColors.errorDark,
        onError = SummerColors.onErrorDark,
        errorContainer = SummerColors.errorContainerDark,
        onErrorContainer = SummerColors.onErrorContainerDark,
        background = SummerColors.backgroundDark,
        onBackground = SummerColors.onBackgroundDark,
        surface = SummerColors.surfaceDark,
        onSurface = SummerColors.onSurfaceDark,
        surfaceVariant = SummerColors.surfaceVariantDark,
        onSurfaceVariant = SummerColors.onSurfaceVariantDark,
        outline = SummerColors.outlineDark,
        outlineVariant = SummerColors.outlineVariantDark,
        scrim = SummerColors.scrimDark,
        inverseSurface = SummerColors.inverseSurfaceDark,
        inverseOnSurface = SummerColors.inverseOnSurfaceDark,
        inversePrimary = SummerColors.inversePrimaryDark,
        surfaceDim = SummerColors.surfaceDimDark,
        surfaceBright = SummerColors.surfaceBrightDark,
        surfaceContainerLowest = SummerColors.surfaceContainerLowestDark,
        surfaceContainerLow = SummerColors.surfaceContainerLowDark,
        surfaceContainer = SummerColors.surfaceContainerDark,
        surfaceContainerHigh = SummerColors.surfaceContainerHighDark,
        surfaceContainerHighest = SummerColors.surfaceContainerHighestDark,
    )

    private val mediumContrastLightColorScheme = lightColorScheme(
        primary = SummerColors.primaryLightMediumContrast,
        onPrimary = SummerColors.onPrimaryLightMediumContrast,
        primaryContainer = SummerColors.primaryContainerLightMediumContrast,
        onPrimaryContainer = SummerColors.onPrimaryContainerLightMediumContrast,
        secondary = SummerColors.secondaryLightMediumContrast,
        onSecondary = SummerColors.onSecondaryLightMediumContrast,
        secondaryContainer = SummerColors.secondaryContainerLightMediumContrast,
        onSecondaryContainer = SummerColors.onSecondaryContainerLightMediumContrast,
        tertiary = SummerColors.tertiaryLightMediumContrast,
        onTertiary = SummerColors.onTertiaryLightMediumContrast,
        tertiaryContainer = SummerColors.tertiaryContainerLightMediumContrast,
        onTertiaryContainer = SummerColors.onTertiaryContainerLightMediumContrast,
        error = SummerColors.errorLightMediumContrast,
        onError = SummerColors.onErrorLightMediumContrast,
        errorContainer = SummerColors.errorContainerLightMediumContrast,
        onErrorContainer = SummerColors.onErrorContainerLightMediumContrast,
        background = SummerColors.backgroundLightMediumContrast,
        onBackground = SummerColors.onBackgroundLightMediumContrast,
        surface = SummerColors.surfaceLightMediumContrast,
        onSurface = SummerColors.onSurfaceLightMediumContrast,
        surfaceVariant = SummerColors.surfaceVariantLightMediumContrast,
        onSurfaceVariant = SummerColors.onSurfaceVariantLightMediumContrast,
        outline = SummerColors.outlineLightMediumContrast,
        outlineVariant = SummerColors.outlineVariantLightMediumContrast,
        scrim = SummerColors.scrimLightMediumContrast,
        inverseSurface = SummerColors.inverseSurfaceLightMediumContrast,
        inverseOnSurface = SummerColors.inverseOnSurfaceLightMediumContrast,
        inversePrimary = SummerColors.inversePrimaryLightMediumContrast,
        surfaceDim = SummerColors.surfaceDimLightMediumContrast,
        surfaceBright = SummerColors.surfaceBrightLightMediumContrast,
        surfaceContainerLowest = SummerColors.surfaceContainerLowestLightMediumContrast,
        surfaceContainerLow = SummerColors.surfaceContainerLowLightMediumContrast,
        surfaceContainer = SummerColors.surfaceContainerLightMediumContrast,
        surfaceContainerHigh = SummerColors.surfaceContainerHighLightMediumContrast,
        surfaceContainerHighest = SummerColors.surfaceContainerHighestLightMediumContrast,
    )

    private val highContrastLightColorScheme = lightColorScheme(
        primary = SummerColors.primaryLightHighContrast,
        onPrimary = SummerColors.onPrimaryLightHighContrast,
        primaryContainer = SummerColors.primaryContainerLightHighContrast,
        onPrimaryContainer = SummerColors.onPrimaryContainerLightHighContrast,
        secondary = SummerColors.secondaryLightHighContrast,
        onSecondary = SummerColors.onSecondaryLightHighContrast,
        secondaryContainer = SummerColors.secondaryContainerLightHighContrast,
        onSecondaryContainer = SummerColors.onSecondaryContainerLightHighContrast,
        tertiary = SummerColors.tertiaryLightHighContrast,
        onTertiary = SummerColors.onTertiaryLightHighContrast,
        tertiaryContainer = SummerColors.tertiaryContainerLightHighContrast,
        onTertiaryContainer = SummerColors.onTertiaryContainerLightHighContrast,
        error = SummerColors.errorLightHighContrast,
        onError = SummerColors.onErrorLightHighContrast,
        errorContainer = SummerColors.errorContainerLightHighContrast,
        onErrorContainer = SummerColors.onErrorContainerLightHighContrast,
        background = SummerColors.backgroundLightHighContrast,
        onBackground = SummerColors.onBackgroundLightHighContrast,
        surface = SummerColors.surfaceLightHighContrast,
        onSurface = SummerColors.onSurfaceLightHighContrast,
        surfaceVariant = SummerColors.surfaceVariantLightHighContrast,
        onSurfaceVariant = SummerColors.onSurfaceVariantLightHighContrast,
        outline = SummerColors.outlineLightHighContrast,
        outlineVariant = SummerColors.outlineVariantLightHighContrast,
        scrim = SummerColors.scrimLightHighContrast,
        inverseSurface = SummerColors.inverseSurfaceLightHighContrast,
        inverseOnSurface = SummerColors.inverseOnSurfaceLightHighContrast,
        inversePrimary = SummerColors.inversePrimaryLightHighContrast,
        surfaceDim = SummerColors.surfaceDimLightHighContrast,
        surfaceBright = SummerColors.surfaceBrightLightHighContrast,
        surfaceContainerLowest = SummerColors.surfaceContainerLowestLightHighContrast,
        surfaceContainerLow = SummerColors.surfaceContainerLowLightHighContrast,
        surfaceContainer = SummerColors.surfaceContainerLightHighContrast,
        surfaceContainerHigh = SummerColors.surfaceContainerHighLightHighContrast,
        surfaceContainerHighest = SummerColors.surfaceContainerHighestLightHighContrast,
    )

    private val mediumContrastDarkColorScheme = darkColorScheme(
        primary = SummerColors.primaryDarkMediumContrast,
        onPrimary = SummerColors.onPrimaryDarkMediumContrast,
        primaryContainer = SummerColors.primaryContainerDarkMediumContrast,
        onPrimaryContainer = SummerColors.onPrimaryContainerDarkMediumContrast,
        secondary = SummerColors.secondaryDarkMediumContrast,
        onSecondary = SummerColors.onSecondaryDarkMediumContrast,
        secondaryContainer = SummerColors.secondaryContainerDarkMediumContrast,
        onSecondaryContainer = SummerColors.onSecondaryContainerDarkMediumContrast,
        tertiary = SummerColors.tertiaryDarkMediumContrast,
        onTertiary = SummerColors.onTertiaryDarkMediumContrast,
        tertiaryContainer = SummerColors.tertiaryContainerDarkMediumContrast,
        onTertiaryContainer = SummerColors.onTertiaryContainerDarkMediumContrast,
        error = SummerColors.errorDarkMediumContrast,
        onError = SummerColors.onErrorDarkMediumContrast,
        errorContainer = SummerColors.errorContainerDarkMediumContrast,
        onErrorContainer = SummerColors.onErrorContainerDarkMediumContrast,
        background = SummerColors.backgroundDarkMediumContrast,
        onBackground = SummerColors.onBackgroundDarkMediumContrast,
        surface = SummerColors.surfaceDarkMediumContrast,
        onSurface = SummerColors.onSurfaceDarkMediumContrast,
        surfaceVariant = SummerColors.surfaceVariantDarkMediumContrast,
        onSurfaceVariant = SummerColors.onSurfaceVariantDarkMediumContrast,
        outline = SummerColors.outlineDarkMediumContrast,
        outlineVariant = SummerColors.outlineVariantDarkMediumContrast,
        scrim = SummerColors.scrimDarkMediumContrast,
        inverseSurface = SummerColors.inverseSurfaceDarkMediumContrast,
        inverseOnSurface = SummerColors.inverseOnSurfaceDarkMediumContrast,
        inversePrimary = SummerColors.inversePrimaryDarkMediumContrast,
        surfaceDim = SummerColors.surfaceDimDarkMediumContrast,
        surfaceBright = SummerColors.surfaceBrightDarkMediumContrast,
        surfaceContainerLowest = SummerColors.surfaceContainerLowestDarkMediumContrast,
        surfaceContainerLow = SummerColors.surfaceContainerLowDarkMediumContrast,
        surfaceContainer = SummerColors.surfaceContainerDarkMediumContrast,
        surfaceContainerHigh = SummerColors.surfaceContainerHighDarkMediumContrast,
        surfaceContainerHighest = SummerColors.surfaceContainerHighestDarkMediumContrast,
    )

    private val highContrastDarkColorScheme = darkColorScheme(
        primary = SummerColors.primaryDarkHighContrast,
        onPrimary = SummerColors.onPrimaryDarkHighContrast,
        primaryContainer = SummerColors.primaryContainerDarkHighContrast,
        onPrimaryContainer = SummerColors.onPrimaryContainerDarkHighContrast,
        secondary = SummerColors.secondaryDarkHighContrast,
        onSecondary = SummerColors.onSecondaryDarkHighContrast,
        secondaryContainer = SummerColors.secondaryContainerDarkHighContrast,
        onSecondaryContainer = SummerColors.onSecondaryContainerDarkHighContrast,
        tertiary = SummerColors.tertiaryDarkHighContrast,
        onTertiary = SummerColors.onTertiaryDarkHighContrast,
        tertiaryContainer = SummerColors.tertiaryContainerDarkHighContrast,
        onTertiaryContainer = SummerColors.onTertiaryContainerDarkHighContrast,
        error = SummerColors.errorDarkHighContrast,
        onError = SummerColors.onErrorDarkHighContrast,
        errorContainer = SummerColors.errorContainerDarkHighContrast,
        onErrorContainer = SummerColors.onErrorContainerDarkHighContrast,
        background = SummerColors.backgroundDarkHighContrast,
        onBackground = SummerColors.onBackgroundDarkHighContrast,
        surface = SummerColors.surfaceDarkHighContrast,
        onSurface = SummerColors.onSurfaceDarkHighContrast,
        surfaceVariant = SummerColors.surfaceVariantDarkHighContrast,
        onSurfaceVariant = SummerColors.onSurfaceVariantDarkHighContrast,
        outline = SummerColors.outlineDarkHighContrast,
        outlineVariant = SummerColors.outlineVariantDarkHighContrast,
        scrim = SummerColors.scrimDarkHighContrast,
        inverseSurface = SummerColors.inverseSurfaceDarkHighContrast,
        inverseOnSurface = SummerColors.inverseOnSurfaceDarkHighContrast,
        inversePrimary = SummerColors.inversePrimaryDarkHighContrast,
        surfaceDim = SummerColors.surfaceDimDarkHighContrast,
        surfaceBright = SummerColors.surfaceBrightDarkHighContrast,
        surfaceContainerLowest = SummerColors.surfaceContainerLowestDarkHighContrast,
        surfaceContainerLow = SummerColors.surfaceContainerLowDarkHighContrast,
        surfaceContainer = SummerColors.surfaceContainerDarkHighContrast,
        surfaceContainerHigh = SummerColors.surfaceContainerHighDarkHighContrast,
        surfaceContainerHighest = SummerColors.surfaceContainerHighestDarkHighContrast,
    )

    @Immutable
    data class ColorFamily(
        val color: Color,
        val onColor: Color,
        val colorContainer: Color,
        val onColorContainer: Color
    )

    val unspecified_scheme = ColorFamily(
        Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
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
            typography = SummerType.AppTypography,
            content = content
        )
    }
}