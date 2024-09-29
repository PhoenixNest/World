package io.dev.relic.feature.activities.web.viewmodel

import android.app.Application
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.RelicConstants
import io.common.ext.ViewModelExt.operationInViewModelScope
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var mWebViewClient: WebViewClient? = null
        private set

    var mWebChromeClient: WebChromeClient? = null
        private set

    private val _webDataStateFlow = MutableStateFlow<WebDataState>(WebDataState.Init)
    val webDataStateFlow: StateFlow<WebDataState> get() = _webDataStateFlow

    companion object {
        private const val TAG = "WebViewModel"
    }

    init {
        initWebClient()
        initWebChromeClient()
    }

    override fun onCleared() {
        super.onCleared()
        mWebViewClient = null
        mWebChromeClient = null
    }

    private fun initWebClient() {
        mWebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val urlString = (request?.url ?: RelicConstants.URL.DEFAULT_PLACEHOLDER_URL).toString()
                view?.loadUrl(urlString)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                operationInViewModelScope {
                    LogUtil.d(TAG, "[Web Data State] Fetch succeed.")
                    setState(_webDataStateFlow, WebDataState.FetchSucceed)
                }
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.cancel()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                val errorCode = error?.errorCode
                val errorMessage = error?.description.toString()
                operationInViewModelScope {
                    LogUtil.e(TAG, "[Web Data] Fetch failed, ($errorCode, $errorMessage)")
                    setState(_webDataStateFlow, WebDataState.FetchFailed(errorCode, errorMessage))
                }
            }
        }
    }

    private fun initWebChromeClient() {
        mWebChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                operationInViewModelScope {
                    LogUtil.d(TAG, "[Web Data State] Fetching, latest progress: $newProgress")
                    setState(_webDataStateFlow, WebDataState.Fetching(newProgress))
                }
            }
        }
    }
}