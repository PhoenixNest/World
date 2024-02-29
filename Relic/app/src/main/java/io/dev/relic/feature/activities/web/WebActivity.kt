package io.dev.relic.feature.activities.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.dev.relic.R
import io.dev.relic.databinding.ActivityWebBinding
import io.dev.relic.feature.activities.web.viewmodel.WebViewModel

class WebActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWebBinding.inflate(layoutInflater)
    }

    private val webViewModel by lazy {
        ViewModelProvider(this)[WebViewModel::class.java]
    }

    companion object {

        /**
         * Redirect to the specified http url.
         *
         * @param context
         * @param httpUrl
         * */
        fun redirect(context: Context, httpUrl: String) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ WebActivity::class.java
                ).apply {
                    action = "[Activity] Web"
                    putExtra("http_url", httpUrl)
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
            webView.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        setupUi()
    }

    private fun setupWebClient() {
        val webView = binding.webView

        webViewModel.apply {
            mWebViewClient?.also { webView.webViewClient = it }
            mWebChromeClient?.also { webView.webChromeClient = it }
        }
    }

    /* ======================== Ui ======================== */

    private fun setupUi() {
        setupWebView()
        setupListener()
    }

    private fun setupListener() {
        binding.apply {
            imageButtonClose.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            // Load the url from passing string
            loadUrl(intent.getStringExtra("http_url") ?: "https://www.bing.com")

            // Web client
            setupWebClient()

            // Web Settings
            settings.apply {
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
        }
    }

}