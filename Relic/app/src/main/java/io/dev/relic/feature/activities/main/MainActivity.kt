package io.dev.relic.feature.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.common.util.LogUtil
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_PRIVACY
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_SHOW_USER_AGREEMENT
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screens.main.MainScreen
import io.dev.relic.global.RelicApplication
import io.domain.AbsBaseActivity
import io.module.map.amap.AMapPrivacyCenter

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AbsBaseActivity() {

    /**
     * VM
     * */
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    companion object {
        private const val TAG = "MainActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ MainActivity::class.java
                ).apply {
                    action = "[Activity] Main"
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        verifyAMapPrivacyAgreement()
    }

    private fun verifyAMapPrivacyAgreement() {
        val isShowUserAgreement: Boolean = readSyncData(KEY_IS_SHOW_USER_AGREEMENT, false)
        val isAgreeUserPrivacy: Boolean = readSyncData(KEY_IS_AGREE_USER_PRIVACY, false)
        LogUtil.debug(TAG, "[UserAgreement] 是否同意用户协议: $isShowUserAgreement")
        LogUtil.debug(TAG, "[UserPrivacy] 是够同意用户隐私协议: $isAgreeUserPrivacy")

        AMapPrivacyCenter.verifyAMapPrivacyAgreement(
            context = RelicApplication.getApplicationContext(),
            isShowUserAgreement = isShowUserAgreement,
            isAgreeUserPrivacy = isAgreeUserPrivacy
        )
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setContent {
            // Setup immersive mode.
            rememberSystemUiController().enableImmersiveMode()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Unspecified)
                        .imePadding()
                        .navigationBarsPadding()
                ) {
                    MainScreen(
                        savedInstanceState = savedInstanceState,
                        windowSizeClass = calculateWindowSizeClass(activity = this),
                        networkMonitor = networkMonitor
                    )
                }
            }
        }
    }
}