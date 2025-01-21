package io.module.media.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.module.media.R

object FontFamily {

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
        Font(R.font.ubuntu_boldltalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.ubuntu_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.ubuntu_light, FontWeight.Light),
        Font(R.font.ubuntu_lightltalic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.ubuntu_medium, FontWeight.Medium),
        Font(R.font.ubuntu_mediumltalic, FontWeight.Medium, FontStyle.Italic)
    )
}