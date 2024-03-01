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
        private const val DEFAULT_REQUEST_PROGRESS = 0
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
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                operationInViewModelScope {
                    LogUtil.d(TAG, "[Web Data State] Fetching")
                    setState(_webDataStateFlow, WebDataState.Fetching(DEFAULT_REQUEST_PROGRESS))
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                operationInViewModelScope {
                    LogUtil.d(TAG, "[Web Data State] Fetch succeed.")
                    setState(_webDataStateFlow, WebDataState.FetchSucceed)
                }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
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
                super.onProgressChanged(view, newProgress)
                operationInViewModelScope {
                    LogUtil.d(TAG, "[Web Data State] Fetching, latest progress: $newProgress")
                    setState(_webDataStateFlow, WebDataState.Fetching(newProgress))
                }
            }
        }
    }
}