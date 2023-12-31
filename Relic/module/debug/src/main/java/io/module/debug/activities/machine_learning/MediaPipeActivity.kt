package io.module.debug.activities.machine_learning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.domain.app.AbsBaseActivity
import io.module.debug.databinding.ActivityMediapipeBinding

/**
 * [MediaPipe](https://developers.google.cn/mediapipe/framework/getting_started/android)
 * */
class MediaPipeActivity : AbsBaseActivity() {

    private val binding: ActivityMediapipeBinding by lazy {
        ActivityMediapipeBinding.inflate(layoutInflater)
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