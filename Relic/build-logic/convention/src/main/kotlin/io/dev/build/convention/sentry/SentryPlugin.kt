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