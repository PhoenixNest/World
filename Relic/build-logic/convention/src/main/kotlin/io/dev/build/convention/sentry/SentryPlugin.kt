package io.dev.build.convention.sentry

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import org.gradle.api.Plugin
import org.gradle.api.Project
import io.dev.build.convention.sentry.track.TrackClassVisitorFactory
import io.dev.build.convention.sentry.util.SentryConfig.IS_ENABLE_TRACK

class SentryPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(target: Project) {
        target.extensions.getByType(AndroidComponentsExtension::class.java).apply {
            onVariants { variant ->
                variant.apply {
                    registerTrackVisitor()
                }
            }
        }
    }

    private fun Variant.registerTrackVisitor() {
        transformClassesWith(
            classVisitorFactoryImplClass = TrackClassVisitorFactory::class.java,
            scope = InstrumentationScope.ALL,
            instrumentationParamsConfig = {
                it.isEnable.set(IS_ENABLE_TRACK)
            }
        )
        setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
    }
}