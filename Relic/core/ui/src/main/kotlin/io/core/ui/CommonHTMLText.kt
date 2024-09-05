package io.core.ui

import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.common.util.StringUtil

@Composable
fun CommonHTMLText(htmlText: String) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context -> TextView(context) },
        update = { it.text = StringUtil.formatHTML(htmlText) }
    )
}