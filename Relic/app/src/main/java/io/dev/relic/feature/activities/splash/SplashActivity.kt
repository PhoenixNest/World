package io.dev.relic.feature.activities.splash

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.ui.theme.RelicAppTheme

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val initLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        private const val TAG = "SplashActivity"
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        initUi()
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        initLiveData.observe(this) { finished: Boolean ->
            if (finished) {
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }
    }

    /* ======================== Ui ======================== */

    private fun initUi() {
        setContent {
            val multiplePermissionsState: MultiplePermissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

            LaunchedEffect(key1 = multiplePermissionsState.allPermissionsGranted) {
                if (multiplePermissionsState.allPermissionsGranted) {
                    initLiveData.postValue(true)
                }
            }

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .captionBarPadding()
                        .navigationBarsPadding()
                ) {
                    SplashScreen()
                }
            }
        }
    }
}