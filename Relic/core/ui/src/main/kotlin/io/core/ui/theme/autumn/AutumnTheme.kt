package io.core.ui.theme.autumn

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

object AutumnTheme {
    private val lightScheme = lightColorScheme(
        primary = AutumnColors.primaryLight,
        onPrimary = AutumnColors.onPrimaryLight,
        primaryContainer = AutumnColors.primaryContainerLight,
        onPrimaryContainer = AutumnColors.onPrimaryContainerLight,
        secondary = AutumnColors.secondaryLight,
        onSecondary = AutumnColors.onSecondaryLight,
        secondaryContainer = AutumnColors.secondaryContainerLight,
        onSecondaryContainer = AutumnColors.onSecondaryContainerLight,
        tertiary = AutumnColors.tertiaryLight,
        onTertiary = AutumnColors.onTertiaryLight,
        tertiaryContainer = AutumnColors.tertiaryContainerLight,
        onTertiaryContainer = AutumnColors.onTertiaryContainerLight,
        error = AutumnColors.errorLight,
        onError = AutumnColors.onErrorLight,
        errorContainer = AutumnColors.errorContainerLight,
        onErrorContainer = AutumnColors.onErrorContainerLight,
        background = AutumnColors.backgroundLight,
        onBackground = AutumnColors.onBackgroundLight,
        surface = AutumnColors.surfaceLight,
        onSurface = AutumnColors.onSurfaceLight,
        surfaceVariant = AutumnColors.surfaceVariantLight,
        onSurfaceVariant = AutumnColors.onSurfaceVariantLight,
        outline = AutumnColors.outlineLight,
        outlineVariant = AutumnColors.outlineVariantLight,
        scrim = AutumnColors.scrimLight,
        inverseSurface = AutumnColors.inverseSurfaceLight,
        inverseOnSurface = AutumnColors.inverseOnSurfaceLight,
        inversePrimary = AutumnColors.inversePrimaryLight,
        surfaceDim = AutumnColors.surfaceDimLight,
        surfaceBright = AutumnColors.surfaceBrightLight,
        surfaceContainerLowest = AutumnColors.surfaceContainerLowestLight,
        surfaceContainerLow = AutumnColors.surfaceContainerLowLight,
        surfaceContainer = AutumnColors.surfaceContainerLight,
        surfaceContainerHigh = AutumnColors.surfaceContainerHighLight,
        surfaceContainerHighest = AutumnColors.surfaceContainerHighestLight,
    )

    private val darkScheme = darkColorScheme(
        primary = AutumnColors.primaryDark,
        onPrimary = AutumnColors.onPrimaryDark,
        primaryContainer = AutumnColors.primaryContainerDark,
        onPrimaryContainer = AutumnColors.onPrimaryContainerDark,
        secondary = AutumnColors.secondaryDark,
        onSecondary = AutumnColors.onSecondaryDark,
        secondaryContainer = AutumnColors.secondaryContainerDark,
        onSecondaryContainer = AutumnColors.onSecondaryContainerDark,
        tertiary = AutumnColors.tertiaryDark,
        onTertiary = AutumnColors.onTertiaryDark,
        tertiaryContainer = AutumnColors.tertiaryContainerDark,
        onTertiaryContainer = AutumnColors.onTertiaryContainerDark,
        error = AutumnColors.errorDark,
        onError = AutumnColors.onErrorDark,
        errorContainer = AutumnColors.errorContainerDark,
        onErrorContainer = AutumnColors.onErrorContainerDark,
        background = AutumnColors.backgroundDark,
        onBackground = AutumnColors.onBackgroundDark,
        surface = AutumnColors.surfaceDark,
        onSurface = AutumnColors.onSurfaceDark,
        surfaceVariant = AutumnColors.surfaceVariantDark,
        onSurfaceVariant = AutumnColors.onSurfaceVariantDark,
        outline = AutumnColors.outlineDark,
        outlineVariant = AutumnColors.outlineVariantDark,
        scrim = AutumnColors.scrimDark,
        inverseSurface = AutumnColors.inverseSurfaceDark,
        inverseOnSurface = AutumnColors.inverseOnSurfaceDark,
        inversePrimary = AutumnColors.inversePrimaryDark,
        surfaceDim = AutumnColors.surfaceDimDark,
        surfaceBright = AutumnColors.surfaceBrightDark,
        surfaceContainerLowest = AutumnColors.surfaceContainerLowestDark,
        surfaceContainerLow = AutumnColors.surfaceContainerLowDark,
        surfaceContainer = AutumnColors.surfaceContainerDark,
        surfaceContainerHigh = AutumnColors.surfaceContainerHighDark,
        surfaceContainerHighest = AutumnColors.surfaceContainerHighestDark,
    )

    private val mediumContrastLightColorScheme = lightColorScheme(
        primary = AutumnColors.primaryLightMediumContrast,
        onPrimary = AutumnColors.onPrimaryLightMediumContrast,
        primaryContainer = AutumnColors.primaryContainerLightMediumContrast,
        onPrimaryContainer = AutumnColors.onPrimaryContainerLightMediumContrast,
        secondary = AutumnColors.secondaryLightMediumContrast,
        onSecondary = AutumnColors.onSecondaryLightMediumContrast,
        secondaryContainer = AutumnColors.secondaryContainerLightMediumContrast,
        onSecondaryContainer = AutumnColors.onSecondaryContainerLightMediumContrast,
        tertiary = AutumnColors.tertiaryLightMediumContrast,
        onTertiary = AutumnColors.onTertiaryLightMediumContrast,
        tertiaryContainer = AutumnColors.tertiaryContainerLightMediumContrast,
        onTertiaryContainer = AutumnColors.onTertiaryContainerLightMediumContrast,
        error = AutumnColors.errorLightMediumContrast,
        onError = AutumnColors.onErrorLightMediumContrast,
        errorContainer = AutumnColors.errorContainerLightMediumContrast,
        onErrorContainer = AutumnColors.onErrorContainerLightMediumContrast,
        background = AutumnColors.backgroundLightMediumContrast,
        onBackground = AutumnColors.onBackgroundLightMediumContrast,
        surface = AutumnColors.surfaceLightMediumContrast,
        onSurface = AutumnColors.onSurfaceLightMediumContrast,
        surfaceVariant = AutumnColors.surfaceVariantLightMediumContrast,
        onSurfaceVariant = AutumnColors.onSurfaceVariantLightMediumContrast,
        outline = AutumnColors.outlineLightMediumContrast,
        outlineVariant = AutumnColors.outlineVariantLightMediumContrast,
        scrim = AutumnColors.scrimLightMediumContrast,
        inverseSurface = AutumnColors.inverseSurfaceLightMediumContrast,
        inverseOnSurface = AutumnColors.inverseOnSurfaceLightMediumContrast,
        inversePrimary = AutumnColors.inversePrimaryLightMediumContrast,
        surfaceDim = AutumnColors.surfaceDimLightMediumContrast,
        surfaceBright = AutumnColors.surfaceBrightLightMediumContrast,
        surfaceContainerLowest = AutumnColors.surfaceContainerLowestLightMediumContrast,
        surfaceContainerLow = AutumnColors.surfaceContainerLowLightMediumContrast,
        surfaceContainer = AutumnColors.surfaceContainerLightMediumContrast,
        surfaceContainerHigh = AutumnColors.surfaceContainerHighLightMediumContrast,
        surfaceContainerHighest = AutumnColors.surfaceContainerHighestLightMediumContrast,
    )

    private val highContrastLightColorScheme = lightColorScheme(
        primary = AutumnColors.primaryLightHighContrast,
        onPrimary = AutumnColors.onPrimaryLightHighContrast,
        primaryContainer = AutumnColors.primaryContainerLightHighContrast,
        onPrimaryContainer = AutumnColors.onPrimaryContainerLightHighContrast,
        secondary = AutumnColors.secondaryLightHighContrast,
        onSecondary = AutumnColors.onSecondaryLightHighContrast,
        secondaryContainer = AutumnColors.secondaryContainerLightHighContrast,
        onSecondaryContainer = AutumnColors.onSecondaryContainerLightHighContrast,
        tertiary = AutumnColors.tertiaryLightHighContrast,
        onTertiary = AutumnColors.onTertiaryLightHighContrast,
        tertiaryContainer = AutumnColors.tertiaryContainerLightHighContrast,
        onTertiaryContainer = AutumnColors.onTertiaryContainerLightHighContrast,
        error = AutumnColors.errorLightHighContrast,
        onError = AutumnColors.onErrorLightHighContrast,
        errorContainer = AutumnColors.errorContainerLightHighContrast,
        onErrorContainer = AutumnColors.onErrorContainerLightHighContrast,
        background = AutumnColors.backgroundLightHighContrast,
        onBackground = AutumnColors.onBackgroundLightHighContrast,
        surface = AutumnColors.surfaceLightHighContrast,
        onSurface = AutumnColors.onSurfaceLightHighContrast,
        surfaceVariant = AutumnColors.surfaceVariantLightHighContrast,
        onSurfaceVariant = AutumnColors.onSurfaceVariantLightHighContrast,
        outline = AutumnColors.outlineLightHighContrast,
        outlineVariant = AutumnColors.outlineVariantLightHighContrast,
        scrim = AutumnColors.scrimLightHighContrast,
        inverseSurface = AutumnColors.inverseSurfaceLightHighContrast,
        inverseOnSurface = AutumnColors.inverseOnSurfaceLightHighContrast,
        inversePrimary = AutumnColors.inversePrimaryLightHighContrast,
        surfaceDim = AutumnColors.surfaceDimLightHighContrast,
        surfaceBright = AutumnColors.surfaceBrightLightHighContrast,
        surfaceContainerLowest = AutumnColors.surfaceContainerLowestLightHighContrast,
        surfaceContainerLow = AutumnColors.surfaceContainerLowLightHighContrast,
        surfaceContainer = AutumnColors.surfaceContainerLightHighContrast,
        surfaceContainerHigh = AutumnColors.surfaceContainerHighLightHighContrast,
        surfaceContainerHighest = AutumnColors.surfaceContainerHighestLightHighContrast,
    )

    private val mediumContrastDarkColorScheme = darkColorScheme(
        primary = AutumnColors.primaryDarkMediumContrast,
        onPrimary = AutumnColors.onPrimaryDarkMediumContrast,
        primaryContainer = AutumnColors.primaryContainerDarkMediumContrast,
        onPrimaryContainer = AutumnColors.onPrimaryContainerDarkMediumContrast,
        secondary = AutumnColors.secondaryDarkMediumContrast,
        onSecondary = AutumnColors.onSecondaryDarkMediumContrast,
        secondaryContainer = AutumnColors.secondaryContainerDarkMediumContrast,
        onSecondaryContainer = AutumnColors.onSecondaryContainerDarkMediumContrast,
        tertiary = AutumnColors.tertiaryDarkMediumContrast,
        onTertiary = AutumnColors.onTertiaryDarkMediumContrast,
        tertiaryContainer = AutumnColors.tertiaryContainerDarkMediumContrast,
        onTertiaryContainer = AutumnColors.onTertiaryContainerDarkMediumContrast,
        error = AutumnColors.errorDarkMediumContrast,
        onError = AutumnColors.onErrorDarkMediumContrast,
        errorContainer = AutumnColors.errorContainerDarkMediumContrast,
        onErrorContainer = AutumnColors.onErrorContainerDarkMediumContrast,
        background = AutumnColors.backgroundDarkMediumContrast,
        onBackground = AutumnColors.onBackgroundDarkMediumContrast,
        surface = AutumnColors.surfaceDarkMediumContrast,
        onSurface = AutumnColors.onSurfaceDarkMediumContrast,
        surfaceVariant = AutumnColors.surfaceVariantDarkMediumContrast,
        onSurfaceVariant = AutumnColors.onSurfaceVariantDarkMediumContrast,
        outline = AutumnColors.outlineDarkMediumContrast,
        outlineVariant = AutumnColors.outlineVariantDarkMediumContrast,
        scrim = AutumnColors.scrimDarkMediumContrast,
        inverseSurface = AutumnColors.inverseSurfaceDarkMediumContrast,
        inverseOnSurface = AutumnColors.inverseOnSurfaceDarkMediumContrast,
        inversePrimary = AutumnColors.inversePrimaryDarkMediumContrast,
        surfaceDim = AutumnColors.surfaceDimDarkMediumContrast,
        surfaceBright = AutumnColors.surfaceBrightDarkMediumContrast,
        surfaceContainerLowest = AutumnColors.surfaceContainerLowestDarkMediumContrast,
        surfaceContainerLow = AutumnColors.surfaceContainerLowDarkMediumContrast,
        surfaceContainer = AutumnColors.surfaceContainerDarkMediumContrast,
        surfaceContainerHigh = AutumnColors.surfaceContainerHighDarkMediumContrast,
        surfaceContainerHighest = AutumnColors.surfaceContainerHighestDarkMediumContrast,
    )

    private val highContrastDarkColorScheme = darkColorScheme(
        primary = AutumnColors.primaryDarkHighContrast,
        onPrimary = AutumnColors.onPrimaryDarkHighContrast,
        primaryContainer = AutumnColors.primaryContainerDarkHighContrast,
        onPrimaryContainer = AutumnColors.onPrimaryContainerDarkHighContrast,
        secondary = AutumnColors.secondaryDarkHighContrast,
        onSecondary = AutumnColors.onSecondaryDarkHighContrast,
        secondaryContainer = AutumnColors.secondaryContainerDarkHighContrast,
        onSecondaryContainer = AutumnColors.onSecondaryContainerDarkHighContrast,
        tertiary = AutumnColors.tertiaryDarkHighContrast,
        onTertiary = AutumnColors.onTertiaryDarkHighContrast,
        tertiaryContainer = AutumnColors.tertiaryContainerDarkHighContrast,
        onTertiaryContainer = AutumnColors.onTertiaryContainerDarkHighContrast,
        error = AutumnColors.errorDarkHighContrast,
        onError = AutumnColors.onErrorDarkHighContrast,
        errorContainer = AutumnColors.errorContainerDarkHighContrast,
        onErrorContainer = AutumnColors.onErrorContainerDarkHighContrast,
        background = AutumnColors.backgroundDarkHighContrast,
        onBackground = AutumnColors.onBackgroundDarkHighContrast,
        surface = AutumnColors.surfaceDarkHighContrast,
        onSurface = AutumnColors.onSurfaceDarkHighContrast,
        surfaceVariant = AutumnColors.surfaceVariantDarkHighContrast,
        onSurfaceVariant = AutumnColors.onSurfaceVariantDarkHighContrast,
        outline = AutumnColors.outlineDarkHighContrast,
        outlineVariant = AutumnColors.outlineVariantDarkHighContrast,
        scrim = AutumnColors.scrimDarkHighContrast,
        inverseSurface = AutumnColors.inverseSurfaceDarkHighContrast,
        inverseOnSurface = AutumnColors.inverseOnSurfaceDarkHighContrast,
        inversePrimary = AutumnColors.inversePrimaryDarkHighContrast,
        surfaceDim = AutumnColors.surfaceDimDarkHighContrast,
        surfaceBright = AutumnColors.surfaceBrightDarkHighContrast,
        surfaceContainerLowest = AutumnColors.surfaceContainerLowestDarkHighContrast,
        surfaceContainerLow = AutumnColors.surfaceContainerLowDarkHighContrast,
        surfaceContainer = AutumnColors.surfaceContainerDarkHighContrast,
        surfaceContainerHigh = AutumnColors.surfaceContainerHighDarkHighContrast,
        surfaceContainerHighest = AutumnColors.surfaceContainerHighestDarkHighContrast,
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
            typography = AutumnType.AppTypography,
            content = content
        )
    }
}