package io.dev.build.convention.output

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import io.dev.build.convention.output.task.CopyApkTask
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.register

/**
 * This custom plugin will register a callback that is applied to all variants.
 */
class OutputPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(target: Project) {
        with(target) {

            // Registers a callback on the application of the Android Application plugin.
            // This allows the CustomPlugin to work whether it's applied before or after
            // the Android Application plugin.
            plugins.withType(AppPlugin::class.java) {
                // Queries for the extension set by the Android Application plugin.
                // This is the second of two entry points into the Android Gradle plugin
                val androidComponents = extensions.getByType(ApplicationAndroidComponentsExtension::class.java)
                // Registers a callback to be called, when a new variant is configured
                androidComponents.onVariants { variant ->
                    // create a task that will be responsible for copying the APKs
                    val copyApkTask = tasks.register<CopyApkTask>("copyApksFor${variant.name}") {
                        // set the output only. the input will be automatically provided via the
                        // wiring mechanism
                        output.set(project.layout.buildDirectory.dir("outputs/renamed_apks/${variant.name}"))

                        // provide an instance of the artifact loader. This is necessary for
                        // some artifacts. See Artifact.ContainsMany
                        buildArtifactsLoader.set(variant.artifacts.getBuiltArtifactsLoader())
                    }

                    // Wire the task to respond to artifact creation
                    variant.artifacts.use(copyApkTask).wiredWith { task ->
                        task.input
                    }.toListenTo(SingleArtifact.APK)

                    // -- Verification --
                    // the following is just to validate the recipe and is not actually
                    // part of the recipe itself
                    project.tasks.register<ValidationTask>("validate${variant.name.capitalized()}") {
                        // The input of the validation task should be the output of the copy task.
                        // The normal way to do this would be:
                        //     input.set(copyTask.flatMap { it.output }
                        // However, doing this will force running the task when we want it to run
                        // automatically when the normal APK packaging task run.
                        // So we set the input manually, and the validation task will have to be called
                        // separately (in a separate Gradle execution or Gradle will detect the
                        // lack of dependency between the 2 tasks and complain).
                        input.set(project.layout.buildDirectory.dir("outputs/renamed_apks/${variant.name}"))
                        variantName.set(variant.name)
                    }
                }
            }
        }
    }
}

/**
 * Validation task to verify the behavior of the recipe
 */
abstract class ValidationTask : DefaultTask() {
    @get:InputDirectory
    abstract val input: DirectoryProperty

    @get:Input
    abstract val variantName: Property<String>

    @TaskAction
    fun taskAction() {
        // manually look for the content of the folder
        checkFile("${variantName.get()}.apk")
        checkFile("output-metadata.json")
    }

    private fun checkFile(name: String) {
        val file = input.get().file(name).asFile
        if (file.isFile.not()) {
            throw RuntimeException("Expected file missing: $file")
        }
    }
}