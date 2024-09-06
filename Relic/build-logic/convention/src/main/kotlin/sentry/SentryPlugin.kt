package sentry

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationParameters
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.util.TraceClassVisitor
import java.io.File
import java.io.PrintWriter

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
                    transformClassesWith(
                        classVisitorFactoryImplClass = ExampleClassVisitorFactory::class.java,
                        scope = InstrumentationScope.ALL,
                        instrumentationParamsConfig = { params ->
                            params.writeToStdout.set(true)
                        }
                    )
                    setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
                }
            }
        }
    }

    interface ExampleParams : InstrumentationParameters {
        @get:Input
        val writeToStdout: Property<Boolean>
    }

    abstract class ExampleClassVisitorFactory : AsmClassVisitorFactory<ExampleParams> {

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
            return if (parameters.get().writeToStdout.get()) {
                TraceClassVisitor(nextClassVisitor, PrintWriter(System.out))
            } else {
                TraceClassVisitor(nextClassVisitor, PrintWriter(File("trace_out")))
            }
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
            return classData.className.startsWith("com.example")
        }
    }
}