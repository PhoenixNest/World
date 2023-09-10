package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.dev.relic.databinding.ActivityMlKitBinding
import io.dev.relic.feature.activities.AbsBaseActivity

class MLKitActivity : AbsBaseActivity() {

    private val binding: ActivityMlKitBinding by lazy {
        ActivityMlKitBinding.inflate(layoutInflater)
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