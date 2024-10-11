package io.module.media.camera

import android.content.ContentValues
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import io.module.media.utils.MediaLogUtil
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

/**
 * Reference Docs:
 *
 * - [CameraX overview](https://developer.android.google.cn/media/camera/camerax?hl=en)
 * - [Getting Started with CameraX](https://developer.android.google.cn/codelabs/camerax-getting-started#0)
 * */
object CameraUtil {

    private const val TAG = "CameraUtil"
    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    fun setupCameraExecutor(executor: ExecutorService) {
        cameraExecutor = executor
    }

    fun getCameraExecutor(): ExecutorService {
        return cameraExecutor
    }

    fun startCamera(
        activity: ComponentActivity,
        previewView: PreviewView
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener(
            /* listener = */ object : Runnable {
                override fun run() {
                    // Used to bind the lifecycle of cameras to the lifecycle owner
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                    // Feature: Preview
                    val preview = Preview.Builder()
                        .build()
                        .also { preview ->
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                        }

                    // Feature: Capture image
                    imageCapture = ImageCapture.Builder().build()

                    // Feature: Analyze
                    val imageAnalyzer = ImageAnalysis.Builder()
                        .build()
                        .also {
                            it.setAnalyzer(
                                /* executor = */ cameraExecutor,
                                /* analyzer = */ LuminosityAnalyzer { luma ->
                                    MediaLogUtil.d(TAG, "[Average luminosity: $luma]")
                                }
                            )
                        }

                    // Select back camera as a default
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        // Bind use cases to camera
                        cameraProvider.bindToLifecycle(
                            /* lifecycleOwner = */ activity,
                            /* cameraSelector = */ cameraSelector,
                            /* ...useCases = */ preview, imageCapture, imageAnalyzer
                        )
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            },
            /* executor = */ ContextCompat.getMainExecutor(activity)
        )
    }

    fun takePhoto(context: Context) {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(
            /* pattern = */ FILENAME_FORMAT,
            /* locale = */ Locale.getDefault()
        ).format(Calendar.getInstance().timeInMillis)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                /* contentResolver = */ context.contentResolver,
                /* saveCollection = */ MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                /* contentValues = */ contentValues
            ).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            /* outputFileOptions = */ outputOptions,
            /* executor = */ ContextCompat.getMainExecutor(context),
            /* imageSavedCallback = */ object : ImageCapture.OnImageSavedCallback {
                /** Called when an image has been successfully saved. */
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    outputFileResults.savedUri
                }

                /**
                 * Called when an error occurs while attempting to save an image.
                 *
                 * @param exception An {@link ImageCaptureException} that contains the type of error, the
                 *                  error message and the throwable that caused it.
                 */
                override fun onError(exception: ImageCaptureException) {
                    MediaLogUtil.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }
            }
        )
    }
}