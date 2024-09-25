package io.module.media.ui.theme

import androidx.compose.ui.graphics.Color

val mainThemeColor = Color(0xFF22232A)
val mainThemeColorLight = Color(0xFFFFFFFF)
val mainThemeColorAccent = Color(0xFF2975CF)

val errorColorAccent = Color(0xFFD32F2F)

val mainBackgroundColor = Color(0xFF303037)
val mainBackgroundColorLight = mainThemeColorLight.copy(alpha = 0.8F)

val placeHolderHighlightColor = mainBackgroundColorLight

val mainTextColor = mainThemeColorLight.copy(alpha = 0.8F)
val mainTextColor30 = mainTextColor.copy(alpha = 0.3F)
val mainTextColor50 = mainTextColor.copy(alpha = 0.5F)
val mainTextColor80 = mainTextColor.copy(alpha = 0.8F)
val mainTextColorDark = mainThemeColor.copy(alpha = 0.8F)

val mainIconColor = mainThemeColor.copy(alpha = 0.8F)
val mainIconColorLight = mainThemeColorLight.copy(alpha = 0.8F)

val mainButtonColor = mainThemeColor
val mainButtonColorLight = mainTextColor
val mainButtonColorLightDim = mainButtonColorLight.copy(alpha = 0.8F)

val dividerColor = mainThemeColorLight.copy(alpha = 0.1F)
val dividerColorDark = mainThemeColor.copy(alpha = 0.1F)

val bottomSheetBackgroundColor = mainThemeColor