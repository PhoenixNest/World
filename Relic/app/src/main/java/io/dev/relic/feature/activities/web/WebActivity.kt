package io.dev.relic.feature.activities.web

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import io.dev.relic.R

class WebActivity : Activity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val webView = findViewById<WebView>(R.id.webView)
        webView.loadUrl(intent.getStringExtra("http_url") ?: "https://www.bing.com")
    }

}