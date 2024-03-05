package io.dev.relic.feature.activities.intro

import android.Manifest
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
import io.common.RelicConstants.IntentAction.INTENT_ACTION_VIEW
import io.common.util.LogUtil
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.activities.intro.viewmodel.IntroViewModel
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.screens.intro.IntroScreen
import io.domain.app.AbsBaseActivity

@OptIn(ExperimentalPermissionsApi::class)
class IntroActivity : AbsBaseActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[IntroViewModel::class.java]
    }

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

    private fun checkPermission() {
        viewModel.getPermissionLiveData().observe(this) { isGranted ->
            LogUtil.d(TAG, "[Permission] isGranted: $isGranted")
            if (isGranted) onPermissionGranted()
        }
    }

    private fun onPermissionGranted() {
        viewModel.apply {
            updateUserAgreementMarker()
            initRequiredAppComponent(applicationContext)
        }

        navigateToMainActivity()
    }

    private fun navigateToMainActivity() {
        MainActivity.start(this)
        finish()
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            val multiplePermissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            )

            LaunchedEffect(multiplePermissionsState.allPermissionsGranted) {
                val isGranted = multiplePermissionsState.allPermissionsGranted
                viewModel.updatePermissionLiveData(isGranted)
            }

            // Setup immersive mode.
            rememberSystemUiController().enableImmersiveMode()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    IntroScreen(onClick = multiplePermissionsState::launchMultiplePermissionRequest)
                }
            }
        }
    }
}