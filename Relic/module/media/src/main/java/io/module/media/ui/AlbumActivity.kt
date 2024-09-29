package io.module.media.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import io.module.media.ui.screen.MediaDeniedScreen
import io.module.media.ui.theme.mainThemeColor
import io.module.media.ui.theme.mainThemeColorLight
import io.module.media.ui.viewmodel.AlbumViewModel
import io.module.media.utils.MediaPermissionDetector

class AlbumActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[AlbumViewModel::class]
    }

    /* ======================== Lifecycle ======================== */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        initUi()
    }

    /* ======================== Logical ======================== */

    private fun initialization() {
        checkAndRequestAlbumPermission()
    }

    private fun checkAndRequestAlbumPermission() {
        MediaPermissionDetector.AlbumPermissionArray
            .filter { permissionString ->
                // Filter the denied permission first.
                !MediaPermissionDetector.checkPermission(
                    context = this@AlbumActivity,
                    requestPermission = permissionString
                )
            }.forEach { permissionString ->
                // Then, request the runtime permission one by one.
                MediaPermissionDetector.requestRuntimePermission(
                    activity = this@AlbumActivity,
                    requestPermission = permissionString,
                    permissionListener = object : MediaPermissionDetector.MediaPermissionListener {
                        override fun onPermissionGranted() {
                            loadAlbumContent()
                        }

                        override fun onPermissionDenied() {
                            updateDeniedUi()
                        }
                    }
                )
            }
    }

    private fun loadAlbumContent() {
        viewModel.queryLocalPhotos()
    }

    /* ======================== Ui ======================== */

    private fun initUi() {
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = mainThemeColorLight.toArgb(),
                    darkScrim = mainThemeColor.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = mainThemeColorLight.toArgb(),
                    darkScrim = mainThemeColor.toArgb()
                )
            )
        }
    }

    private fun updateDeniedUi() {
        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = mainThemeColorLight.toArgb(),
                    darkScrim = mainThemeColor.toArgb()
                ),
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = mainThemeColorLight.toArgb(),
                    darkScrim = mainThemeColor.toArgb()
                )
            )

            MediaDeniedScreen(
                onBackClick = {
                    finish()
                },
                onRetryClick = {
                    checkAndRequestAlbumPermission()
                }
            )
        }
    }
}