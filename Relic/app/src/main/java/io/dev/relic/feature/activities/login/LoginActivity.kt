package io.dev.relic.feature.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.dev.relic.feature.activities.AbsBaseActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.screens.login.LoginScreen
import io.core.ui.ext.SystemUiControllerExt.enableImmersiveMode
import io.core.ui.theme.RelicAppTheme

class LoginActivity : AbsBaseActivity() {

    companion object {
        private const val TAG = "LoginActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ LoginActivity::class.java
                ).apply {
                    action = "[Activity] Login"
                }
            )
        }
    }

    /* ======================== Logical ======================== */

    override fun initialization(savedInstanceState: Bundle?) {
        //
    }

    private fun loginWithGoogle() {
        //
    }

    private fun navigateToMainActivity() {
        MainActivity.start(this)
    }

    /* ======================== Ui ======================== */

    override fun initUi(savedInstanceState: Bundle?) {
        setupUi()
    }

    private fun setupUi() {
        setContent {
            // Setup immersive mode.
            rememberSystemUiController().enableImmersiveMode()

            // A surface container using the 'background' color from the theme
            RelicAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(onLoginWithGoogle = this::loginWithGoogle)
                }
            }
        }
    }

}