package io.dev.relic.feature.activities.web.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var mWebViewClient: WebViewClient? = null
        private set

    var mWebChromeClient: WebChromeClient? = null
        private set

    companion object {
        private const val TAG = "WebViewModel"
    }

    init {
        initWebClient()
        initWebChromeClient()
    }

    private fun initWebClient() {
        mWebViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }
        }
    }

    private fun initWebChromeClient() {
        mWebChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
    }

}