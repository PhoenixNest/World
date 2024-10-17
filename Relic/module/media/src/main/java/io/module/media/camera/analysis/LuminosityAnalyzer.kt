package io.module.media.camera.analysis

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

typealias LumaListener = (luma: Double) -> Unit

class LuminosityAnalyzer(
    private val listener: LumaListener
) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        // Rewind the buffer to zero.
        rewind()
        val data = ByteArray(remaining())
        // Copy the buffer into a byte array.
        get(data)
        // Return the byte array.
        return data
    }

    /**
     * Analyzes an image to produce a result.
     *
     * <p>This method is called once for each image from the camera, and called at the
     * frame rate of the camera. Each analyze call is executed sequentially.
     *
     * <p>It is the responsibility of the application to close the image once done with it.
     * If the images are not closed then it may block further images from being produced
     * (causing the preview to stall) or drop images as determined by the configured
     * backpressure strategy. The exact behavior is configurable via
     * {@link ImageAnalysis.Builder#setBackpressureStrategy(int)}.
     *
     * <p>Images produced here will no longer be valid after the {@link ImageAnalysis}
     * instance that produced it has been unbound from the camera.
     *
     * <p>The image provided has format {@link android.graphics.ImageFormat#YUV_420_888}.
     *
     * <p>The provided image is typically in the orientation of the sensor, meaning CameraX
     * does not perform an internal rotation of the data.  The rotationDegrees parameter allows
     * the analysis to understand the image orientation when processing or to apply a rotation.
     * For example, if the
     * {@linkplain ImageAnalysis#setTargetRotation(int) target rotation}) is natural
     * orientation, rotationDegrees would be the rotation which would align the buffer
     * data ordering to natural orientation.
     *
     * <p>Timestamps are in nanoseconds and monotonic and can be compared to timestamps from
     * images produced from UseCases bound to the same camera instance.  More detail is
     * available depending on the implementation.  For example with CameraX using a
     * {@link androidx.camera.camera2} implementation additional detail can be found in
     * {@link android.hardware.camera2.CameraDevice} documentation.
     *
     * @param image The image to analyze
     * @see android.media.Image#getTimestamp()
     * @see android.hardware.camera2.CaptureResult#SENSOR_TIMESTAMP
     */
    override fun analyze(image: ImageProxy) {
        val buffer = image.planes.first().buffer
        val data = buffer.toByteArray()
        val pixels = data.map {
            it.toInt() and 0xFF
        }
        val luma = pixels.average()
        listener(luma)
        image.close()
    }
}