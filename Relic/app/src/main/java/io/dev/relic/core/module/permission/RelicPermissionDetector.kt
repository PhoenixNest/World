package io.dev.relic.core.module.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import io.dev.relic.global.utils.LogUtil

/**
 * Reference link: [Permissions on Android](https://developer.android.com/guide/topics/permissions/overview)
 * */
object RelicPermissionDetector {

    private const val TAG: String = "RelicPermissionDetector"

    interface RelicPermissionListener {

        fun onPermissionGrant()

        fun onPermissionDenied()

    }

    /**
     * Reference link: [Request runtime permissions](https://developer.android.com/training/permissions/requesting)
     *
     * @param activity
     * @param requestPermission
     * @param permissionListener
     *
     *  @see ActivityResultLauncher
     * */
    fun requestRuntimePermission(
        activity: ComponentActivity,
        requestPermission: String,
        permissionListener: RelicPermissionListener
    ) {
        val isGranted: Boolean = checkPermission(
            context = activity.applicationContext,
            requestPermission = requestPermission
        )

        if (isGranted) {
            LogUtil.debug(TAG, "$requestPermission Permission has already granted.")
            permissionListener.onPermissionGrant()
            return
        } else {
            val requestPermissionLauncher: ActivityResultLauncher<String> = activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { _isGranted: Boolean ->
                if (_isGranted) {
                    LogUtil.debug(TAG, "$requestPermission Permission Granted.")
                    permissionListener.onPermissionGrant()
                } else {
                    LogUtil.debug(TAG, "$requestPermission Permission Denied.")
                    permissionListener.onPermissionDenied()
                }
            }

            requestPermissionLauncher.launch(requestPermission)
        }
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
        return ContextCompat.checkSelfPermission(context, requestPermission) == PackageManager.PERMISSION_GRANTED
    }

}