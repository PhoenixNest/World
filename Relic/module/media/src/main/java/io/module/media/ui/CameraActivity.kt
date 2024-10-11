package io.module.media.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.module.media.camera.CameraUtil
import io.module.media.ui.screen.MediaDeniedScreen
import io.module.media.ui.viewmodel.CameraViewModel
import java.util.concurrent.Executors

class CameraActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CameraViewModel::class.java]
    }

    companion object {
        private const val TAG = "CameraActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ CameraActivity::class.java
                ).apply {
                    //
                }
            )
        }
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initialization()
        initUi()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Avoid OOM
        CameraUtil.getCameraExecutor().shutdown()
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        initParams()
        requestMediaPermission()
    }

    private fun initParams() {
        val executors = Executors.newSingleThreadExecutor()
        CameraUtil.setupCameraExecutor(executors)
    }

    private fun requestMediaPermission() {
        viewModel.checkAndRequestCameraPermission(this)
    }

    /* ======================== Ui ======================== */

    private fun initUi() {
        setContent {
            MaterialTheme {
                val isPermissionGranted = viewModel.permissionFlow
                    .collectAsStateWithLifecycle().value

                BackHandler {
                    // Disabled back button.
                }

                if (isPermissionGranted) {
                    //
                } else {
                    MediaDeniedScreen(
                        onBackClick = { finish() },
                        onRetryClick = { requestMediaPermission() }
                    )
                }

                AndroidView(
                    factory = { context ->
                        PreviewView(context)
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { view ->
                        CameraUtil.startCamera(
                            activity = this,
                            previewView = view
                        )
                    }
                )
            }
        }
    }
}