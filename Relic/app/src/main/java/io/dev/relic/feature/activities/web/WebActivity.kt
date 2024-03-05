package io.dev.relic.feature.activities.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.common.util.LogUtil
import io.core.ui.utils.RelicUiUtil.toggleUiGone
import io.core.ui.utils.RelicUiUtil.toggleUiVisibility
import io.dev.relic.R
import io.dev.relic.databinding.ActivityWebBinding
import io.dev.relic.feature.activities.web.viewmodel.WebDataState
import io.dev.relic.feature.activities.web.viewmodel.WebViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebActivity : AppCompatActivity() {

    /**
     * View binding
     * */
    private val binding by lazy {
        ActivityWebBinding.inflate(layoutInflater)
    }

    /**
     * ViewModel
     * */
    private val webViewModel by lazy {
        ViewModelProvider(this)[WebViewModel::class.java]
    }

    companion object {

        private const val TAG = "WebActivity"

        private const val ARG_REQUEST_URL = "arg_request_url"

        /**
         * Redirect to the specified http/https url.
         *
         * @param context
         * @param url
         * */
        fun redirect(context: Context, url: String) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ WebActivity::class.java
                ).apply {
                    action = INTENT_ACTION_VIEW
                    putExtra(ARG_REQUEST_URL, url)
                }
            )
        }
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        initialization()
    }

    /* ======================== override ======================== */

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val webView = binding.webView

        // Intercept the BACK key from system.
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            LogUtil.d(TAG, "[Event: onKeyDown] Intercept the BACK key from system.")
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        setupUi()
        handleWebProgress()
    }

    private fun setupWebClient() {
        val webView = binding.webView

        webViewModel.apply {
            mWebViewClient?.also { webView.webViewClient = it }
            mWebChromeClient?.also { webView.webChromeClient = it }
        }
    }

    private fun handleWebProgress() {
        lifecycleScope.launch(Dispatchers.IO) {
            webViewModel.webDataStateFlow.collect {
                when (val dataState = it) {
                    is WebDataState.Init -> {
                        showLoadingView()
                    }

                    is WebDataState.Empty,
                    is WebDataState.NoWebData,
                    is WebDataState.FetchFailed -> {
                        hideLoadingView()
                        showEmptyView()
                    }

                    is WebDataState.Fetching -> {
                        if (dataState.latestProgress >= 30) {
                            hideLoadingView()
                            showWebView()
                        }
                    }

                    is WebDataState.FetchSucceed -> {
                        hideLoadingView()
                    }
                }
            }
        }
    }

    /* ======================== Ui ======================== */

    private fun setupUi() {
        setupWebView()
        setupListener()
    }

    private fun setupListener() {
        binding.apply {
            imageViewClose.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            // Web client
            setupWebClient()

            // Web Settings
            settings.apply {
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
        }.loadUrl(intent.getStringExtra("http_url") ?: "https://www.bing.com")
    }

    private fun showLoadingView() {
        binding.lottieAnimationViewLoading.toggleUiVisibility(true)
    }

    private fun hideLoadingView() {
        binding.lottieAnimationViewLoading.toggleUiGone(true)
    }

    private fun showWebView() {
        binding.webView.toggleUiVisibility(true)
    }

    private fun showEmptyView() {
        binding.linearLayoutNoData.toggleUiVisibility(true)
    }
}