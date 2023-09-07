package io.dev.relic.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200: Color = Color(0xFFBB86FC)
val Purple500: Color = Color(0xFF6200EE)
val Purple700: Color = Color(0xFF3700B3)
val Teal200: Color = Color(0xFF03DAC5)

val mainThemeColor: Color = Color(0xFF282c34)
val mainThemeColorAccent: Color = Color(0xFF0061FF)

val mainTextColor: Color = Color.White.copy(alpha = 0.8F)
val mainTextColorLight: Color = mainTextColor.copy(alpha = 0.6F)
val mainTextColorDark: Color = mainThemeColor.copy(alpha = 0.8F)
val mainTextColorDarkLight: Color = mainTextColorDark.copy(0.6F)

val mainButtonColor: Color = mainThemeColor

val dividerColor: Color = Color.White.copy(alpha = 0.1F)