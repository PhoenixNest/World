package io.module.media.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
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
        checkAlbumPermission()
    }

    private fun checkAlbumPermission() {
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
            //
        }
    }

    private fun updateDeniedUi() {

    }
}