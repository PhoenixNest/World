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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.common.util.LogUtil
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.BuildConfig
import io.dev.relic.feature.activities.intro.IntroActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.viewmodel.SplashViewModel
import io.dev.relic.feature.screens.splash.SplashScreen
import io.dev.relic.global.RelicLifecycleObserver
import io.domain.AbsBaseActivity
import io.module.debug.activities.debug.DebugActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AbsBaseActivity() {

    /**
     * VM
     * */
    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    /**
     * Change this marker to true if you want to enable debug mode.
     * */
    private val isDebugMode: Boolean = BuildConfig.DEBUG_MODE

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
        initialization(savedInstanceState)
        initUi(savedInstanceState)
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        splashViewModel.execute(
            context = this,
            onSplashProcessEnd = {
                LogUtil.debug(TAG, "[Splash-Process] Finished, start main logic.")
                checkAndNavigate()
            }
        )
    }

    private fun checkAndNavigate() {
        when {
            RelicLifecycleObserver.isFirstColdStart -> navigateToIntroActivity()
            isDebugMode -> navigateToDebugActivity()
            else -> navigateToMainActivity()
        }.also { finish() }
    }

    private fun navigateToIntroActivity() {
        IntroActivity.start(this)
    }

    private fun navigateToDebugActivity() {
        DebugActivity.start(this)
    }

    private fun navigateToMainActivity() {
        MainActivity.start(this)
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            // Setup immersive mode.
            rememberSystemUiController().enableImmersiveMode()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SplashScreen()
                }
            }
        }
    }
}