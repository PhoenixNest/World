package io.dev.relic.feature.activities.intro

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.global.utils.UiUtil
import io.dev.relic.ui.theme.RelicAppTheme

@OptIn(ExperimentalPermissionsApi::class)
class IntroActivity : AbsBaseActivity() {

    private val permissionLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        private const val TAG = "IntroActivity"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        initUi()
    }

    /* ======================== Logical ======================== */

    override fun initialization() {
        permissionLiveData.observe(this) { isGranted: Boolean ->
            if (isGranted) {
                MainActivity.start(this@IntroActivity)
                finish()
            }
        }
    }

    /* ======================== Ui ======================== */

    override fun initUi() {
        setContent {
            val multiplePermissionsState: MultiplePermissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

            LaunchedEffect(key1 = multiplePermissionsState.allPermissionsGranted) {
                permissionLiveData.postValue(multiplePermissionsState.allPermissionsGranted)
            }

            // Setup immersive status bar.
            UiUtil.StatusBarUtil.setImmersiveStatusBar()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .captionBarPadding()
                        .navigationBarsPadding()
                ) {
                    IntroScreen(onClick = multiplePermissionsState::launchMultiplePermissionRequest)
                }
            }
        }
    }
}