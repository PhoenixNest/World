package io.common.util

import android.text.Html
import android.text.Spanned

object StringUtil {

    /**
     * Format the given string to html type.
     *
     * @param sourceString
     * @param formatFlags
     * */
    fun formatHTML(
        sourceString: String,
        formatFlags: Int = Html.FROM_HTML_MODE_COMPACT
    ): Spanned {
        return Html.fromHtml(
            /* source = */ sourceString,
            /* flags = */ formatFlags
        )
    }

}