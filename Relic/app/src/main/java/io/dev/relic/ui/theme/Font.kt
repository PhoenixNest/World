package io.dev.relic.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import io.dev.relic.R

object RelicFontFamily {

    /**
     * [Google fonts > Ubuntu](https://fonts.google.com/specimen/Ubuntu)
     * */
    val ubuntu: FontFamily = FontFamily(
        Font(R.font.ubuntu_regular),
        Font(R.font.ubuntu_bold),
        Font(R.font.ubuntu_boldltalic),
        Font(R.font.ubuntu_italic),
        Font(R.font.ubuntu_light),
        Font(R.font.ubuntu_lightltalic),
        Font(R.font.ubuntu_medium),
        Font(R.font.ubuntu_mediumltalic)
    )

    /**
     * [Google fonts > Fasthand](https://fonts.google.com/specimen/Fasthand)
     * */
    val fasthand: FontFamily = FontFamily(
        Font(R.font.fasthand_regular)
    )
}