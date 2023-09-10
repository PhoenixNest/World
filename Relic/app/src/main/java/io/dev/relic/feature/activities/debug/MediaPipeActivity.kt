package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.dev.relic.databinding.ActivityMediaPipeBinding
import io.dev.relic.feature.activities.AbsBaseActivity

class MediaPipeActivity : AbsBaseActivity() {

    private val binding: ActivityMediaPipeBinding by lazy {
        ActivityMediaPipeBinding.inflate(layoutInflater)
    }

    companion object {
        private const val TAG = "MediaPipeActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ MediaPipeActivity::class.java
                ).apply {
                    action = "[Activity] MediaPipe"
                }
            )
        }
    }

    /* ======================== override ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        //
    }

    override fun initUi(savedInstanceState: Bundle?) {
        setContentView(binding.root)
    }

}