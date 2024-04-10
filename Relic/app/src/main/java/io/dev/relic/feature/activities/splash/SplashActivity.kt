package io.dev.relic.feature.activities.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.common.util.LogUtil
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.BuildConfig
import io.dev.relic.feature.activities.intro.IntroActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.viewmodel.SplashViewModel
import io.dev.relic.feature.screens.splash.SplashScreen
import io.dev.relic.global.RelicApplication
import io.dev.relic.global.RelicLifecycleObserver
import io.dev.relic.global.RelicSdkManager
import io.dev.relic.global.RelicSdkManager.getSdkInitStatusLiveData
import io.dev.relic.global.RelicSdkManager.initRequiredAppComponent
import io.domain.app.AbsBaseActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AbsBaseActivity() {

    /**
     * ViewModel
     * */
    private val splashViewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    /**
     * Change this marker to true if you want to enable debug mode.
     * */
    private val isDebugMode = BuildConfig.DEBUG_MODE

    companion object {
        private const val TAG = "SplashActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ SplashActivity::class.java
                ).apply {
                    action = INTENT_ACTION_VIEW
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
            context = RelicApplication.getApplicationContext(),
            onSplashProcessEnd = {
                LogUtil.d(TAG, "[Splash-Process] Finished, start main logic.")
                checkAndNavigate().also { finish() }
            }
        )
    }

    private fun checkAndNavigate() {
        when {
            RelicLifecycleObserver.isFirstColdStart -> navigateToIntroActivity()
            else -> checkIsAgreeUserPrivacy()
        }
    }

    /**
     * Double check this app is already gains the user permission.
     *
     * @see RelicSdkManager.updateUserAgreementMarker
     * @see RelicSdkManager.getUserAgreementMarker
     * */
    private fun checkIsAgreeUserPrivacy() {
        val isAgreeUserAgreement = RelicSdkManager.getUserAgreementMarker()
        if (isAgreeUserAgreement) {
            initRequiredAppComponent(applicationContext)
            checkSdkInitStatus()
        } else {
            navigateToIntroActivity()
        }
    }

    /**
     * Double check the necessary sdk is already initialized.
     *
     * @see RelicSdkManager.initRequiredAppComponent
     * */
    private fun checkSdkInitStatus() {
        getSdkInitStatusLiveData().observe(this) { isFinish ->
            if (isFinish) {
                LogUtil.d(TAG, "[Splash - Sdk] Init finished.")
                navigateToMainActivity()
            }
        }
    }

    /**
     * Navigate to the Intro activity.
     *
     * @see IntroActivity
     * */
    private fun navigateToIntroActivity() {
        LogUtil.d(TAG, "[Splash Navigator] Navigate to IntroActivity")
        IntroActivity.start(this)
    }

    /**
     * Navigate to the Main activity.
     *
     * @see MainActivity
     * */
    private fun navigateToMainActivity() {
        LogUtil.d(TAG, "[Splash Navigator] Navigate to MainActivity")
        MainActivity.start(this)
    }

    /* ======================== Ui ======================== */

    @OptIn(ExperimentalPermissionsApi::class)
    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            val multiplePermissionsState = rememberMultiplePermissionsState(
                permissions = RelicSdkManager.permissionList
            )

            LaunchedEffect(multiplePermissionsState.allPermissionsGranted) {
                val isGranted = multiplePermissionsState.allPermissionsGranted
                RelicSdkManager.updatePermissionLiveData(isGranted)
            }

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