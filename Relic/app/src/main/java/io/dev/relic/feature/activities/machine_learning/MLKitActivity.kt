package io.dev.relic.feature.activities.machine_learning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.dev.relic.databinding.ActivityMlkitBinding
import io.dev.relic.feature.activities.AbsBaseActivity

/**
 * [MLKit](https://developers.google.cn/ml-kit/guides?hl=en)
 * */
class MLKitActivity : AbsBaseActivity() {

    private val binding: ActivityMlkitBinding by lazy {
        ActivityMlkitBinding.inflate(layoutInflater)
    }

    companion object {
        private const val TAG = "MLKitActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ MLKitActivity::class.java
                ).apply {
                    action = "[Activity] MLKit"
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