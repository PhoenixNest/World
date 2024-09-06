package sentry.track

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor

abstract class TrackClassVisitorFactory : AsmClassVisitorFactory<TrackClassVisitorFactory.SwitchParam> {

    interface SwitchParam : InstrumentationParameters {
        @get:Input
        val isEnable: Property<Boolean>
    }

    /**
     * Whether the factory wants to instrument the class with the given [classData].
     *
     * If returned true, [createClassVisitor] will be called and the returned class visitor will
     * visit the class.
     *
     * This method must handle asynchronous calls.
     */
    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }

    /**
     * Creates a class visitor object that will visit a class with the given [classContext]. The
     * returned class visitor must delegate its calls to [nextClassVisitor]. If at this point the
     * factory is not interested in instrumenting the class with given [classContext], then return
     * [nextClassVisitor].
     *
     * The given [classContext] contains static information about the classes before starting the
     * instrumentation process. Any changes in interfaces or superclasses for the class with the
     * given [classContext] or for any other class in its classpath by a previous visitor will
     * not be reflected in the [classContext] object.
     *
     * [classContext] can also be used to get the data for classes that are in the runtime classpath
     * of the class being visited.
     *
     * This method must handle asynchronous calls.
     *
     * @param classContext contains information about the class that will be instrumented by the
     *                     returned class visitor.
     * @param nextClassVisitor the [ClassVisitor] to which the created [ClassVisitor] must delegate
     *                         method calls.
     */
    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        val isEnable = parameters.orNull?.isEnable?.get() ?: false
        val apiVersion = instrumentationContext.apiVersion.get()
        return TrackClassVisitor(
            isEnableTrack = isEnable,
            apiVersion = apiVersion,
            classVisitor = nextClassVisitor
        )
    }
}