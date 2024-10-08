package io.dev.build.convention.android

import com.android.build.gradle.LibraryExtension
import io.dev.build.convention.configure.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    /**
     * Apply this plugin to the given target object.
     *
     * @param target The target object
     */
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            val libraryExtension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(libraryExtension)
        }
    }

}