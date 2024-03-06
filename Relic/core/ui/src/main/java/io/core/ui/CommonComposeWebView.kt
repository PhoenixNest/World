package io.core.ui

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.core.ui.theme.mainThemeColor

@Composable
fun CommonComposeWebView(redirectUrl: String) {
    AndroidView(
        factory = {
            val webView = WebView(it)
            webView.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                loadUrl(redirectUrl)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = mainThemeColor)
    )
}