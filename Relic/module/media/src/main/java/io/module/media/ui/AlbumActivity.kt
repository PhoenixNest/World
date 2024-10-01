package io.module.media.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.module.media.MediaType.IMAGE
import io.module.media.ui.screen.MediaDeniedScreen
import io.module.media.ui.screen.MediaScreen
import io.module.media.ui.viewmodel.MediaViewModel

class AlbumActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MediaViewModel::class]
    }

    companion object {
        private const val TAG = "AlbumActivity"

        fun start(context: Context) {
            context.startActivity(
                Intent(
                    /* packageContext = */ context,
                    /* cls = */ AlbumActivity::class.java
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

    /* ======================== Logical ======================== */

    private fun initialization() {
        requestMediaPermission()
    }

    private fun requestMediaPermission() {
        viewModel.checkAndRequestAlbumPermission(
            activity = this@AlbumActivity,
            type = IMAGE
        )
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
                    MediaScreen(
                        type = IMAGE,
                        infoModelList = viewModel.getMediaList(IMAGE)
                    )
                } else {
                    MediaDeniedScreen(
                        onBackClick = { finish() },
                        onRetryClick = { requestMediaPermission() }
                    )
                }
            }
        }
    }
}