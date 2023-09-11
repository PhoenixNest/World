package io.dev.relic.feature.activities.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.dev.relic.R
import io.dev.relic.databinding.ActivityDebugBinding
import io.dev.relic.domain.map.amap.AMapPrivacyCenter
import io.dev.relic.global.RelicApplication

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
        AMapPrivacyCenter.verifyAMapPrivacyAgreement(RelicApplication.getApplicationContext())
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