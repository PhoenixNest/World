package io.core.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.core.ui.R

object RelicFontFamily {

    val googleSans = FontFamily(
        Font(R.font.google_sans_regular, FontWeight.Normal),
        Font(R.font.google_sans_bold, FontWeight.Bold),
        Font(R.font.google_sans_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.google_sans_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.google_sans_medium, FontWeight.Medium),
        Font(R.font.google_sans_medium_italic, FontWeight.Medium, FontStyle.Italic)
    )

    val googleSansDisplay = FontFamily(
        Font(R.font.google_sans_display_regular, FontWeight.Normal),
    )

    val googleProductSans = FontFamily(
        Font(R.font.google_product_sans, FontWeight.Normal)
    )

    /**
     * [Google fonts > Ubuntu](https://fonts.google.com/specimen/Ubuntu)
     * */
    val ubuntu = FontFamily(
        Font(R.font.ubuntu_regular, FontWeight.Normal),
        Font(R.font.ubuntu_bold, FontWeight.Bold),
        Font(R.font.ubuntu_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.ubuntu_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.ubuntu_light, FontWeight.Light),
        Font(R.font.ubuntu_light_italic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.ubuntu_medium, FontWeight.Medium),
        Font(R.font.ubuntu_medium_italic, FontWeight.Medium, FontStyle.Italic)
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