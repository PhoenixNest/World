package io.core.ui.theme.spring

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

object SpringTheme {
    private val lightScheme = lightColorScheme(
        primary = SpringColors.primaryLight,
        onPrimary = SpringColors.onPrimaryLight,
        primaryContainer = SpringColors.primaryContainerLight,
        onPrimaryContainer = SpringColors.onPrimaryContainerLight,
        secondary = SpringColors.secondaryLight,
        onSecondary = SpringColors.onSecondaryLight,
        secondaryContainer = SpringColors.secondaryContainerLight,
        onSecondaryContainer = SpringColors.onSecondaryContainerLight,
        tertiary = SpringColors.tertiaryLight,
        onTertiary = SpringColors.onTertiaryLight,
        tertiaryContainer = SpringColors.tertiaryContainerLight,
        onTertiaryContainer = SpringColors.onTertiaryContainerLight,
        error = SpringColors.errorLight,
        onError = SpringColors.onErrorLight,
        errorContainer = SpringColors.errorContainerLight,
        onErrorContainer = SpringColors.onErrorContainerLight,
        background = SpringColors.backgroundLight,
        onBackground = SpringColors.onBackgroundLight,
        surface = SpringColors.surfaceLight,
        onSurface = SpringColors.onSurfaceLight,
        surfaceVariant = SpringColors.surfaceVariantLight,
        onSurfaceVariant = SpringColors.onSurfaceVariantLight,
        outline = SpringColors.outlineLight,
        outlineVariant = SpringColors.outlineVariantLight,
        scrim = SpringColors.scrimLight,
        inverseSurface = SpringColors.inverseSurfaceLight,
        inverseOnSurface = SpringColors.inverseOnSurfaceLight,
        inversePrimary = SpringColors.inversePrimaryLight,
        surfaceDim = SpringColors.surfaceDimLight,
        surfaceBright = SpringColors.surfaceBrightLight,
        surfaceContainerLowest = SpringColors.surfaceContainerLowestLight,
        surfaceContainerLow = SpringColors.surfaceContainerLowLight,
        surfaceContainer = SpringColors.surfaceContainerLight,
        surfaceContainerHigh = SpringColors.surfaceContainerHighLight,
        surfaceContainerHighest = SpringColors.surfaceContainerHighestLight,
    )

    private val darkScheme = darkColorScheme(
        primary = SpringColors.primaryDark,
        onPrimary = SpringColors.onPrimaryDark,
        primaryContainer = SpringColors.primaryContainerDark,
        onPrimaryContainer = SpringColors.onPrimaryContainerDark,
        secondary = SpringColors.secondaryDark,
        onSecondary = SpringColors.onSecondaryDark,
        secondaryContainer = SpringColors.secondaryContainerDark,
        onSecondaryContainer = SpringColors.onSecondaryContainerDark,
        tertiary = SpringColors.tertiaryDark,
        onTertiary = SpringColors.onTertiaryDark,
        tertiaryContainer = SpringColors.tertiaryContainerDark,
        onTertiaryContainer = SpringColors.onTertiaryContainerDark,
        error = SpringColors.errorDark,
        onError = SpringColors.onErrorDark,
        errorContainer = SpringColors.errorContainerDark,
        onErrorContainer = SpringColors.onErrorContainerDark,
        background = SpringColors.backgroundDark,
        onBackground = SpringColors.onBackgroundDark,
        surface = SpringColors.surfaceDark,
        onSurface = SpringColors.onSurfaceDark,
        surfaceVariant = SpringColors.surfaceVariantDark,
        onSurfaceVariant = SpringColors.onSurfaceVariantDark,
        outline = SpringColors.outlineDark,
        outlineVariant = SpringColors.outlineVariantDark,
        scrim = SpringColors.scrimDark,
        inverseSurface = SpringColors.inverseSurfaceDark,
        inverseOnSurface = SpringColors.inverseOnSurfaceDark,
        inversePrimary = SpringColors.inversePrimaryDark,
        surfaceDim = SpringColors.surfaceDimDark,
        surfaceBright = SpringColors.surfaceBrightDark,
        surfaceContainerLowest = SpringColors.surfaceContainerLowestDark,
        surfaceContainerLow = SpringColors.surfaceContainerLowDark,
        surfaceContainer = SpringColors.surfaceContainerDark,
        surfaceContainerHigh = SpringColors.surfaceContainerHighDark,
        surfaceContainerHighest = SpringColors.surfaceContainerHighestDark,
    )

    private val mediumContrastLightColorScheme = lightColorScheme(
        primary = SpringColors.primaryLightMediumContrast,
        onPrimary = SpringColors.onPrimaryLightMediumContrast,
        primaryContainer = SpringColors.primaryContainerLightMediumContrast,
        onPrimaryContainer = SpringColors.onPrimaryContainerLightMediumContrast,
        secondary = SpringColors.secondaryLightMediumContrast,
        onSecondary = SpringColors.onSecondaryLightMediumContrast,
        secondaryContainer = SpringColors.secondaryContainerLightMediumContrast,
        onSecondaryContainer = SpringColors.onSecondaryContainerLightMediumContrast,
        tertiary = SpringColors.tertiaryLightMediumContrast,
        onTertiary = SpringColors.onTertiaryLightMediumContrast,
        tertiaryContainer = SpringColors.tertiaryContainerLightMediumContrast,
        onTertiaryContainer = SpringColors.onTertiaryContainerLightMediumContrast,
        error = SpringColors.errorLightMediumContrast,
        onError = SpringColors.onErrorLightMediumContrast,
        errorContainer = SpringColors.errorContainerLightMediumContrast,
        onErrorContainer = SpringColors.onErrorContainerLightMediumContrast,
        background = SpringColors.backgroundLightMediumContrast,
        onBackground = SpringColors.onBackgroundLightMediumContrast,
        surface = SpringColors.surfaceLightMediumContrast,
        onSurface = SpringColors.onSurfaceLightMediumContrast,
        surfaceVariant = SpringColors.surfaceVariantLightMediumContrast,
        onSurfaceVariant = SpringColors.onSurfaceVariantLightMediumContrast,
        outline = SpringColors.outlineLightMediumContrast,
        outlineVariant = SpringColors.outlineVariantLightMediumContrast,
        scrim = SpringColors.scrimLightMediumContrast,
        inverseSurface = SpringColors.inverseSurfaceLightMediumContrast,
        inverseOnSurface = SpringColors.inverseOnSurfaceLightMediumContrast,
        inversePrimary = SpringColors.inversePrimaryLightMediumContrast,
        surfaceDim = SpringColors.surfaceDimLightMediumContrast,
        surfaceBright = SpringColors.surfaceBrightLightMediumContrast,
        surfaceContainerLowest = SpringColors.surfaceContainerLowestLightMediumContrast,
        surfaceContainerLow = SpringColors.surfaceContainerLowLightMediumContrast,
        surfaceContainer = SpringColors.surfaceContainerLightMediumContrast,
        surfaceContainerHigh = SpringColors.surfaceContainerHighLightMediumContrast,
        surfaceContainerHighest = SpringColors.surfaceContainerHighestLightMediumContrast,
    )

    private val highContrastLightColorScheme = lightColorScheme(
        primary = SpringColors.primaryLightHighContrast,
        onPrimary = SpringColors.onPrimaryLightHighContrast,
        primaryContainer = SpringColors.primaryContainerLightHighContrast,
        onPrimaryContainer = SpringColors.onPrimaryContainerLightHighContrast,
        secondary = SpringColors.secondaryLightHighContrast,
        onSecondary = SpringColors.onSecondaryLightHighContrast,
        secondaryContainer = SpringColors.secondaryContainerLightHighContrast,
        onSecondaryContainer = SpringColors.onSecondaryContainerLightHighContrast,
        tertiary = SpringColors.tertiaryLightHighContrast,
        onTertiary = SpringColors.onTertiaryLightHighContrast,
        tertiaryContainer = SpringColors.tertiaryContainerLightHighContrast,
        onTertiaryContainer = SpringColors.onTertiaryContainerLightHighContrast,
        error = SpringColors.errorLightHighContrast,
        onError = SpringColors.onErrorLightHighContrast,
        errorContainer = SpringColors.errorContainerLightHighContrast,
        onErrorContainer = SpringColors.onErrorContainerLightHighContrast,
        background = SpringColors.backgroundLightHighContrast,
        onBackground = SpringColors.onBackgroundLightHighContrast,
        surface = SpringColors.surfaceLightHighContrast,
        onSurface = SpringColors.onSurfaceLightHighContrast,
        surfaceVariant = SpringColors.surfaceVariantLightHighContrast,
        onSurfaceVariant = SpringColors.onSurfaceVariantLightHighContrast,
        outline = SpringColors.outlineLightHighContrast,
        outlineVariant = SpringColors.outlineVariantLightHighContrast,
        scrim = SpringColors.scrimLightHighContrast,
        inverseSurface = SpringColors.inverseSurfaceLightHighContrast,
        inverseOnSurface = SpringColors.inverseOnSurfaceLightHighContrast,
        inversePrimary = SpringColors.inversePrimaryLightHighContrast,
        surfaceDim = SpringColors.surfaceDimLightHighContrast,
        surfaceBright = SpringColors.surfaceBrightLightHighContrast,
        surfaceContainerLowest = SpringColors.surfaceContainerLowestLightHighContrast,
        surfaceContainerLow = SpringColors.surfaceContainerLowLightHighContrast,
        surfaceContainer = SpringColors.surfaceContainerLightHighContrast,
        surfaceContainerHigh = SpringColors.surfaceContainerHighLightHighContrast,
        surfaceContainerHighest = SpringColors.surfaceContainerHighestLightHighContrast,
    )

    private val mediumContrastDarkColorScheme = darkColorScheme(
        primary = SpringColors.primaryDarkMediumContrast,
        onPrimary = SpringColors.onPrimaryDarkMediumContrast,
        primaryContainer = SpringColors.primaryContainerDarkMediumContrast,
        onPrimaryContainer = SpringColors.onPrimaryContainerDarkMediumContrast,
        secondary = SpringColors.secondaryDarkMediumContrast,
        onSecondary = SpringColors.onSecondaryDarkMediumContrast,
        secondaryContainer = SpringColors.secondaryContainerDarkMediumContrast,
        onSecondaryContainer = SpringColors.onSecondaryContainerDarkMediumContrast,
        tertiary = SpringColors.tertiaryDarkMediumContrast,
        onTertiary = SpringColors.onTertiaryDarkMediumContrast,
        tertiaryContainer = SpringColors.tertiaryContainerDarkMediumContrast,
        onTertiaryContainer = SpringColors.onTertiaryContainerDarkMediumContrast,
        error = SpringColors.errorDarkMediumContrast,
        onError = SpringColors.onErrorDarkMediumContrast,
        errorContainer = SpringColors.errorContainerDarkMediumContrast,
        onErrorContainer = SpringColors.onErrorContainerDarkMediumContrast,
        background = SpringColors.backgroundDarkMediumContrast,
        onBackground = SpringColors.onBackgroundDarkMediumContrast,
        surface = SpringColors.surfaceDarkMediumContrast,
        onSurface = SpringColors.onSurfaceDarkMediumContrast,
        surfaceVariant = SpringColors.surfaceVariantDarkMediumContrast,
        onSurfaceVariant = SpringColors.onSurfaceVariantDarkMediumContrast,
        outline = SpringColors.outlineDarkMediumContrast,
        outlineVariant = SpringColors.outlineVariantDarkMediumContrast,
        scrim = SpringColors.scrimDarkMediumContrast,
        inverseSurface = SpringColors.inverseSurfaceDarkMediumContrast,
        inverseOnSurface = SpringColors.inverseOnSurfaceDarkMediumContrast,
        inversePrimary = SpringColors.inversePrimaryDarkMediumContrast,
        surfaceDim = SpringColors.surfaceDimDarkMediumContrast,
        surfaceBright = SpringColors.surfaceBrightDarkMediumContrast,
        surfaceContainerLowest = SpringColors.surfaceContainerLowestDarkMediumContrast,
        surfaceContainerLow = SpringColors.surfaceContainerLowDarkMediumContrast,
        surfaceContainer = SpringColors.surfaceContainerDarkMediumContrast,
        surfaceContainerHigh = SpringColors.surfaceContainerHighDarkMediumContrast,
        surfaceContainerHighest = SpringColors.surfaceContainerHighestDarkMediumContrast,
    )

    private val highContrastDarkColorScheme = darkColorScheme(
        primary = SpringColors.primaryDarkHighContrast,
        onPrimary = SpringColors.onPrimaryDarkHighContrast,
        primaryContainer = SpringColors.primaryContainerDarkHighContrast,
        onPrimaryContainer = SpringColors.onPrimaryContainerDarkHighContrast,
        secondary = SpringColors.secondaryDarkHighContrast,
        onSecondary = SpringColors.onSecondaryDarkHighContrast,
        secondaryContainer = SpringColors.secondaryContainerDarkHighContrast,
        onSecondaryContainer = SpringColors.onSecondaryContainerDarkHighContrast,
        tertiary = SpringColors.tertiaryDarkHighContrast,
        onTertiary = SpringColors.onTertiaryDarkHighContrast,
        tertiaryContainer = SpringColors.tertiaryContainerDarkHighContrast,
        onTertiaryContainer = SpringColors.onTertiaryContainerDarkHighContrast,
        error = SpringColors.errorDarkHighContrast,
        onError = SpringColors.onErrorDarkHighContrast,
        errorContainer = SpringColors.errorContainerDarkHighContrast,
        onErrorContainer = SpringColors.onErrorContainerDarkHighContrast,
        background = SpringColors.backgroundDarkHighContrast,
        onBackground = SpringColors.onBackgroundDarkHighContrast,
        surface = SpringColors.surfaceDarkHighContrast,
        onSurface = SpringColors.onSurfaceDarkHighContrast,
        surfaceVariant = SpringColors.surfaceVariantDarkHighContrast,
        onSurfaceVariant = SpringColors.onSurfaceVariantDarkHighContrast,
        outline = SpringColors.outlineDarkHighContrast,
        outlineVariant = SpringColors.outlineVariantDarkHighContrast,
        scrim = SpringColors.scrimDarkHighContrast,
        inverseSurface = SpringColors.inverseSurfaceDarkHighContrast,
        inverseOnSurface = SpringColors.inverseOnSurfaceDarkHighContrast,
        inversePrimary = SpringColors.inversePrimaryDarkHighContrast,
        surfaceDim = SpringColors.surfaceDimDarkHighContrast,
        surfaceBright = SpringColors.surfaceBrightDarkHighContrast,
        surfaceContainerLowest = SpringColors.surfaceContainerLowestDarkHighContrast,
        surfaceContainerLow = SpringColors.surfaceContainerLowDarkHighContrast,
        surfaceContainer = SpringColors.surfaceContainerDarkHighContrast,
        surfaceContainerHigh = SpringColors.surfaceContainerHighDarkHighContrast,
        surfaceContainerHighest = SpringColors.surfaceContainerHighestDarkHighContrast,
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
            typography = SpringType.AppTypography,
            content = content
        )
    }
} 