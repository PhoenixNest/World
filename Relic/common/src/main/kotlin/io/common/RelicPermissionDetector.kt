package io.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import io.common.util.LogUtil

object RelicPermissionDetector {

    private const val TAG = "RelicPermissionDetector"

    interface RelicPermissionListener {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    /**
     * [Permissions on Android](https://developer.android.com/guide/topics/permissions/overview)
     *
     * <img src="https://developer.android.com/static/images/training/permissions/workflow-runtime.svg">
     * */
    object Native {

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
            permissionListener: RelicPermissionListener
        ) {
            val isGranted = checkPermission(
                context = activity.applicationContext,
                requestPermission = requestPermission
            )

            if (isGranted) {
                LogUtil.d(TAG, "$requestPermission Permission has already granted.")
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
                        LogUtil.d(TAG, "$requestPermission Permission Granted.")
                        permissionListener.onPermissionGranted()
                    } else {
                        // Explain to the user that the feature is unavailable because the
                        // feature requires a permission that the user has denied. At the
                        // same time, respect the user's decision. Don't link to system
                        // settings in an effort to convince the user to change their
                        // decision.
                        LogUtil.d(TAG, "$requestPermission Permission Denied.")
                        permissionListener.onPermissionDenied()
                    }
                }

                requestPermissionLauncher.launch(requestPermission)
            }
        }
    }

    /**
     * [Accompanist • Permissions](https://google.github.io/accompanist/permissions/)
     * */
    @OptIn(ExperimentalPermissionsApi::class)
    object Accompanist {

        /**
         * [Request runtime permission](https://google.github.io/accompanist/permissions/#rememberpermissionstate-and-remembermultiplepermissionsstate-apis)
         *
         * @param requestPermission     Such as: android.Manifest.permission.CAMERA
         * */
        @SuppressLint("ComposableNaming")
        @Composable
        fun permissionState(requestPermission: String): PermissionState {
            return rememberPermissionState(permission = requestPermission)
        }
    }

}