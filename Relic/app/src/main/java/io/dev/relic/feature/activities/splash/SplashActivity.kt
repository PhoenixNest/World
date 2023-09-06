package io.dev.relic.feature.activities.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.activities.intro.IntroActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.viewmodel.SplashViewModel
import io.dev.relic.feature.screen.splash.SplashScreen
import io.dev.relic.global.RelicLifecycleObserver
import io.dev.relic.global.utils.UiUtil
import io.dev.relic.ui.theme.RelicAppTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : AbsBaseActivity() {

    /**
     * VM
     * */
    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    companion object {
        private const val TAG = "SplashActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ SplashActivity::class.java
                ).apply {
                    action = "[Activity] Splash"
                }
            )
        }
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        initUi()
    }

    /* ======================== Logical ======================== */

    override fun initialization() {
        splashViewModel.execute(
            context = this,
            onSplashProcessEnd = {
                checkAndNavigate()
            }
        )
    }

    private fun checkAndNavigate() {
        if (RelicLifecycleObserver.isFirstColdStart) {
            IntroActivity.start(this)
        } else {
            MainActivity.start(this)
        }
    }

    /* ======================== Ui ======================== */

    override fun initUi() {
        setContent {
            // Setup immersive mode.
            UiUtil.SystemUtil.setImmersiveMode()
            UiUtil.StatusBarUtil.setImmersiveStatusBar()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SplashScreen()
                }
            }
        }
    }
}