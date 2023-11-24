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
import androidx.lifecycle.MutableLiveData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.screens.intro.IntroScreen
import io.module.common.util.LogUtil
import io.ui.theme.RelicAppTheme
import io.module.core.datastore.RelicDatastoreCenter.writeSyncData
import io.module.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_PRIVACY
import io.module.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_TERMS
import io.module.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_SHOW_USER_AGREEMENT
import io.ui.ext.SystemUiControllerExt.enableImmersiveMode

@OptIn(ExperimentalPermissionsApi::class)
class IntroActivity : AbsBaseActivity() {

    private val permissionLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        private const val TAG = "IntroActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ IntroActivity::class.java
                ).apply {
                    action = "[Activity] Intro"
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
        permissionLiveData.observe(this) { isGranted: Boolean ->
            LogUtil.debug(TAG, "[Permission] isGranted: $isGranted")
            updateUserAgreementMarker(
                isAgreeUserTerms = isGranted,
                isAgreeUserPrivacy = isGranted
            )
            if (isGranted) {
                MainActivity.start(this@IntroActivity)
                finish()
            }
        }
    }

    private fun updateUserAgreementMarker(
        isAgreeUserTerms: Boolean,
        isAgreeUserPrivacy: Boolean
    ) {
        writeSyncData(KEY_IS_SHOW_USER_AGREEMENT, true)
        writeSyncData(KEY_IS_AGREE_USER_TERMS, isAgreeUserTerms)
        writeSyncData(KEY_IS_AGREE_USER_PRIVACY, isAgreeUserPrivacy)
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            val multiplePermissionsState: MultiplePermissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

            LaunchedEffect(multiplePermissionsState.allPermissionsGranted) {
                permissionLiveData.postValue(multiplePermissionsState.allPermissionsGranted)
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