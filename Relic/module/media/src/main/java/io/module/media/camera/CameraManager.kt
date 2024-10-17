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
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import io.module.media.camera.util.CameraUsageType
import io.module.media.camera.util.CameraUsageType.*
import io.module.media.camera.analysis.LuminosityAnalyzer
import io.module.media.utils.MediaLogUtil
import io.module.media.utils.MediaPermissionDetector
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

/**
 * Reference Docs:
 *
 * - [CameraX overview](https://developer.android.google.cn/media/camera/camerax?hl=en)
 * - [Getting Started with CameraX](https://developer.android.google.cn/codelabs/camerax-getting-started#0)
 * */
object CameraManager {

    private const val TAG = "CameraManager"
    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService

    fun setupCameraExecutor(executor: ExecutorService) {
        cameraExecutor = executor
    }

    fun getCameraExecutor(): ExecutorService {
        return cameraExecutor
    }

    fun startCamera(
        activity: ComponentActivity,
        previewView: PreviewView,
        type: CameraUsageType
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

                    // Select back camera as a default
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        when (type) {
                            TAKE_PHOTO -> {
                                // Feature: Capture image
                                imageCapture = ImageCapture.Builder().build()

                                // Feature: Analyze
                                imageAnalyzer = ImageAnalysis.Builder()
                                    .build()
                                    .also {
                                        it.setAnalyzer(
                                            /* executor = */ cameraExecutor,
                                            /* analyzer = */ LuminosityAnalyzer { luma ->
                                                MediaLogUtil.d(TAG, "[Average luminosity: $luma]")
                                            }
                                        )
                                    }

                                // Bind use cases to camera
                                cameraProvider.bindToLifecycle(
                                    /* lifecycleOwner = */ activity,
                                    /* cameraSelector = */ cameraSelector,
                                    /* ...useCases = */preview, imageCapture
                                )
                            }

                            RECORD_VIDEO -> {
                                // Feature: Capture video with audio
                                val quality = Quality.HIGHEST
                                val fallbackQuality = Quality.SD
                                val fallbackStrategy = FallbackStrategy.higherQualityOrLowerThan(fallbackQuality)
                                val qualitySelector = QualitySelector.from(quality, fallbackStrategy)
                                val recorder = Recorder.Builder()
                                    .setQualitySelector(qualitySelector)
                                    .build()
                                videoCapture = VideoCapture.withOutput(recorder)

                                // Bind use cases to camera
                                cameraProvider.bindToLifecycle(
                                    /* lifecycleOwner = */ activity,
                                    /* cameraSelector = */ cameraSelector,
                                    /* ...useCases = */preview, videoCapture
                                )
                            }
                        }
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

    fun captureVideo(
        context: Context,
        listener: VideoCaptureListener
    ) {
        val videoCapture = videoCapture ?: return
        val curRecording = recording
        if (curRecording != null) {
            // Stop the current recording session.
            curRecording.stop()
            recording = null
            return
        }

        // create and start a new recording session
        val name = SimpleDateFormat(
            FILENAME_FORMAT,
            Locale.getDefault()
        ).format(Calendar.getInstance().timeInMillis)

        val contentValue = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(
                /* contentResolver = */ context.contentResolver,
                /* collectionUri = */ MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            ).setContentValues(contentValue)
            .build()

        recording = videoCapture.output.prepareRecording(
            /* context = */ context,
            /* mediaStoreOutputOptions = */ mediaStoreOutputOptions
        ).apply {
            val recordAudioPermission = MediaPermissionDetector.CameraPermissionArray.first { permission ->
                permission == android.Manifest.permission.RECORD_AUDIO
            }
            if (MediaPermissionDetector.checkPermission(context, recordAudioPermission)) {
                withAudioEnabled()
            }
        }.start(
            /* listenerExecutor = */ ContextCompat.getMainExecutor(context),
            /* listener = */ object : Consumer<VideoRecordEvent> {
                override fun accept(recordEvent: VideoRecordEvent) {
                    when (recordEvent) {
                        is VideoRecordEvent.Start -> {
                            listener.onRecordStart()
                        }

                        is VideoRecordEvent.Resume -> {
                            listener.onRecordResume()
                        }

                        is VideoRecordEvent.Pause -> {
                            listener.onRecordPause()
                        }

                        is VideoRecordEvent.Finalize -> {
                            if (recordEvent.hasError()) {
                                recording?.close()
                                recording == null
                                MediaLogUtil.e(TAG, "[Capture Video] Error, message: ${recordEvent.error}")
                                listener.onRecordError()
                            } else {
                                val outputUri = recordEvent.outputResults.outputUri
                                MediaLogUtil.d(TAG, "[Capture Video] Succeed, output uri: $outputUri")
                                listener.onRecordFinalize()
                            }
                        }
                    }
                }
            }
        )
    }

    interface VideoCaptureListener {
        fun onRecordStart()
        fun onRecordResume()
        fun onRecordPause()
        fun onRecordError()
        fun onRecordFinalize()
    }
}