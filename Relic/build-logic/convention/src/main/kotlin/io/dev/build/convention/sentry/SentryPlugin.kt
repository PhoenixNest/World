package io.dev.build.convention.sentry

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import io.dev.build.convention.sentry.track.TrackClassVisitorFactory
import io.dev.build.convention.sentry.util.SentryConfig.IS_ENABLE_TRACK
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Reference docs
 *
 * – [刚学会Transform，你告诉我就要被移除了](https://juejin.cn/post/7114863832954044446)
 * */
class SentryPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(target: Project) {
        // Queries for the extension set by the Android Application plugin.
        // This is the second of two entry points into the Android Gradle plugin
        val androidComponents = target.extensions.getByType(AndroidComponentsExtension::class.java)
        // Registers a callback to be called, when a new variant is configured
        androidComponents.onVariants { variant ->
            variant.apply {
                print("[Variant] Current variant is: ${variant.name}")
                registerTrackVisitor()
            }
        }
    }

    private fun Variant.registerTrackVisitor() {
        // Call the "transformClassesWith" API: 
        // supply the class visitor factory, and specify the scope and parameters
        instrumentation.transformClassesWith(
            classVisitorFactoryImplClass = TrackClassVisitorFactory::class.java,
            scope = InstrumentationScope.ALL,
            instrumentationParamsConfig = { params ->
                params.isEnable.set(IS_ENABLE_TRACK)
            }
        )
        instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
    }
}