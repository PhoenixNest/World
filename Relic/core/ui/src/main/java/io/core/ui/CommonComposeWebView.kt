package io.core.ui

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CommonComposeWebView(redirectUrl: String) {
    AndroidView(
        factory = {
            val webView = WebView(it)
            webView.apply {
                loadUrl(redirectUrl)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}