package io.core.ui.theme

import androidx.compose.ui.graphics.Color

val mainThemeColor = Color(0xFF282C34)
val mainThemeColorLight = Color(0xFFD7D3CB)
val mainThemeColorAccent = Color(0xFFDEB654)

val errorColorAccent = Color(0xFFD32F2F)

val mainBackgroundColor = Color.DarkGray
val mainBackgroundColorLight = Color.White.copy(alpha = 0.8F)

val placeHolderHighlightColor = mainBackgroundColorLight

val mainTextColor = Color.White.copy(alpha = 0.8F)
val mainTextColor30 = mainTextColor.copy(alpha = 0.3F)
val mainTextColor50 = mainTextColor.copy(alpha = 0.5F)
val mainTextColor80 = mainTextColor.copy(alpha = 0.8F)

val mainTextColorDark = mainThemeColor.copy(alpha = 0.8F)

val mainIconColor = mainThemeColor
val mainIconColorLight = Color.White.copy(alpha = 0.8F)

val mainButtonColor = mainThemeColor
val mainButtonColorLight = mainTextColor
val mainButtonColorLightDark = mainButtonColorLight.copy(alpha = 0.8F)

val dividerColor = Color.White.copy(alpha = 0.1F)
val dividerColorDark = Color.Black.copy(alpha = 0.1F)