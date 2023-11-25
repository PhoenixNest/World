package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.dev.relic.R
import io.dev.relic.databinding.ActivityDebugBinding
import io.dev.relic.global.RelicApplication
import io.common.util.LogUtil
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_AGREE_USER_PRIVACY
import io.core.datastore.preference_keys.UserPreferenceKeys.KEY_IS_SHOW_USER_AGREEMENT
import io.module.map.amap.AMapPrivacyCenter

@AndroidEntryPoint
class DebugActivity : AppCompatActivity() {

    private val binding: ActivityDebugBinding by lazy {
        ActivityDebugBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    companion object {
        private const val TAG = "DebugActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ DebugActivity::class.java
                ).apply {
                    action = "[Activity] Debug"
                }
            )
        }
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialization(savedInstanceState)
        initUi(savedInstanceState)
    }

    /* ======================== Logical ======================== */

    fun initialization(savedInstanceState: Bundle?) {
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

    fun initUi(savedInstanceState: Bundle?) {
        setupDebugView()
    }

    private fun setupDebugView() {
        navController = findNavController(R.id.navHostFragment)

        // Binding the navController with bottomNavigationView
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}