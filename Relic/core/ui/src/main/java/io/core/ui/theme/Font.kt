package io.core.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.core.ui.R

object RelicFontFamily {

    /**
     * [Google fonts > Ubuntu](https://fonts.google.com/specimen/Ubuntu)
     * */
    val ubuntu = FontFamily(
        Font(R.font.ubuntu_regular, FontWeight.Normal),
        Font(R.font.ubuntu_bold, FontWeight.Bold),
        Font(R.font.ubuntu_boldltalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.ubuntu_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.ubuntu_light, FontWeight.Light),
        Font(R.font.ubuntu_lightltalic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.ubuntu_medium, FontWeight.Medium),
        Font(R.font.ubuntu_mediumltalic, FontWeight.Medium, FontStyle.Italic)
    )

    /**
     * [Google fonts > Fasthand](https://fonts.google.com/specimen/Fasthand)
     * */
    val fasthand = FontFamily(
        Font(R.font.fasthand_regular, FontWeight.Normal)
    )

    /**
     * [Google fonts > Newsreader](https://fonts.google.com/specimen/Newsreader?query=News&sort=popularity)
     * */
    val newsReader = FontFamily(
        Font(R.font.newsreader, FontWeight.Normal),
        Font(R.font.newsreader_italic, FontWeight.Normal, FontStyle.Italic)
    )
}