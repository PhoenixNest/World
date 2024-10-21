package io.module.media.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

/**
 * [Permissions on Android](https://developer.android.com/guide/topics/permissions/overview)
 *
 * <img src="https://developer.android.com/static/images/training/permissions/workflow-runtime.svg">
 * */
object MediaPermissionDetector {

    private const val TAG = "MediaPermissionDetector"

    /**
     * Permission array for Album, such as image the video.
     * */
    val PERMISSION_ARRAY_ALBUM
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

    /**
     * Permission array for Audio.
     * */
    val PERMISSION_ARRAY_AUDIO
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

    /**
     * Permission array of Camera, such as front or rear camera.
     * */
    val PERMISSION_ARRAY_CAMERA
        get() = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

    interface MediaPermissionListener {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    /**
     * Check is the request permission is already granted.
     *
     * @param context
     * @param requestPermission
     * */
    fun checkPermission(
        context: Context,
        requestPermission: String
    ): Boolean {
        return ContextCompat.checkSelfPermission(
            /* context = */ context,
            /* permission = */ requestPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * [Request runtime permissions](https://developer.android.com/training/permissions/requesting)
     *
     * @param activity              Host activity
     * @param requestPermission     Such as: android.Manifest.permission.CAMERA
     * @param permissionListener    Callback listener
     *
     *  @see ActivityResultLauncher
     * */
    fun requestRuntimePermission(
        activity: ComponentActivity,
        requestPermission: String,
        permissionListener: MediaPermissionListener
    ) {
        val isGranted = checkPermission(
            context = activity.applicationContext,
            requestPermission = requestPermission
        )

        if (isGranted) {
            MediaLogUtil.d(TAG, "$requestPermission Permission has already granted.")
            permissionListener.onPermissionGranted()
            return
        } else {
            // Register the permissions callback, which handles the user's response to the
            // system permissions dialog. Save the return value, an instance of
            // ActivityResultLauncher. You can use either a val, as shown in this snippet,
            // or a lateinit var in your onAttach() or onCreate() method.
            val requestPermissionLauncher = activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isSuccess ->
                if (isSuccess) {
                    // Permission is granted. Continue the action or workflow in your app.
                    MediaLogUtil.d(TAG, "$requestPermission Permission Granted.")
                    permissionListener.onPermissionGranted()
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    MediaLogUtil.d(TAG, "$requestPermission Permission Denied.")
                    permissionListener.onPermissionDenied()
                }
            }

            requestPermissionLauncher.launch(requestPermission)
        }
    }
}