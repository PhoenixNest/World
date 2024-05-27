package io.dev.relic.feature.activities.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.common.util.LogUtil
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.screens.intro.IntroScreen
import io.dev.relic.global.RelicSdkManager
import io.dev.relic.global.RelicSdkManager.getSdkInitStatusLiveData
import io.dev.relic.global.RelicSdkManager.initRequiredAppComponent
import io.dev.relic.global.RelicSdkManager.updateUserAgreementMarker
import io.domain.app.AbsBaseActivity

@OptIn(ExperimentalPermissionsApi::class)
class IntroActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "IntroActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ IntroActivity::class.java
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
        checkPermission()
    }

    /**
     * First check the necessary permission is granted by user.
     * */
    private fun checkPermission() {
        RelicSdkManager.getPermissionLiveData().observe(this) { isGranted ->
            LogUtil.d(TAG, "[Permission] isGranted: $isGranted")
            if (isGranted) onPermissionGranted()
        }
    }

    /**
     * Callback invoke when user has agreed the privacy and agreement.
     *
     * @see RelicSdkManager.updateUserAgreementMarker
     * @see RelicSdkManager.getUserAgreementMarker
     * */
    private fun onPermissionGranted() {
        updateUserAgreementMarker()
        initRequiredAppComponent(applicationContext)
        checkSdkInitStatus()
    }

    /**
     * Check the init status of sdk.
     * */
    private fun checkSdkInitStatus() {
        getSdkInitStatusLiveData().observe(this) { isFinish ->
            if (isFinish) {
                LogUtil.d(TAG, "[Intro - Sdk] Init finished.")
                navigateToMainActivity()
            }
        }
    }

    /**
     * Navigate to Main activity.
     *
     * @see MainActivity
     * */
    private fun navigateToMainActivity() {
        MainActivity.start(this)
        finish()
    }

    /* ======================== Ui ======================== */

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
                    IntroScreen(onNavigateClick = multiplePermissionsState::launchMultiplePermissionRequest)
                }
            }
        }
    }
}