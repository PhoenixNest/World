package io.dev.build.convention.output.task

import com.android.build.api.variant.BuiltArtifactsLoader
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.com.google.common.io.Files
import java.io.File

/**
 * This task will receive a folder of APK to copy and a folder to copy them to.
 *
 * This will also receive a [BuiltArtifactsLoader]. This is specific to some artifacts that
 * have metadata associated with them (in this case [SingleArtifact.APK]). This will allow to
 * find all the apks and load their metadata, instead of just going through the folder.
 */
abstract class CopyApkTask : DefaultTask() {

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val output: DirectoryProperty

    @get:OutputDirectory
    abstract val input: DirectoryProperty

    @get:Internal
    abstract val buildArtifactsLoader: Property<BuiltArtifactsLoader>

    @TaskAction
    fun taskAction() {
        // delete the previous content. This task does not support incremental mode but could
        // be modified to do so
        val outputDirectory = output.get()
        val outputFile = outputDirectory.asFile

        outputFile.deleteRecursively()
        outputFile.mkdirs()

        // this will load the content of the folder and give access to all items representing
        // the artifact with their metadata
        val buildArtifact = buildArtifactsLoader.get().load(input.get())
            ?: throw RuntimeException("Cannot load APKs")

        buildArtifact.elements.forEach { artifact ->
            val name = buildString {
                append(buildArtifact.variantName)
                artifact.versionName?.let { versionName ->
                    if (versionName.isNotBlank()) {
                        append("-$versionName")
                    }
                }
                artifact.versionCode?.let { versionCode ->
                    append("-$versionCode")
                }
                append(".apk")
            }

            Files.copy(
                /* from = */ File(artifact.outputFile),
                /* to = */ outputDirectory.file(name).asFile
            )
        }

        // The above will only save the artifact themselves. It will not save the
        // metadata associated with them. Depending on our needs we may need to copy it.
        // This is required when transforming such an artifact. We'll do it here for demonstration
        // purpose.
        buildArtifact.save(outputDirectory)
    }
}