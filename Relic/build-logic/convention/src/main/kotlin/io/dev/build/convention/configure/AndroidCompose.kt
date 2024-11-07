package io.dev.build.convention.configure

import com.android.build.api.dsl.CommonExtension
import io.dev.build.convention.ext.DependencyHandlerScopeExt.androidTestImplementation
import io.dev.build.convention.ext.DependencyHandlerScopeExt.debugImplementation
import io.dev.build.convention.ext.DependencyHandlerScopeExt.implementation
import io.dev.build.convention.ext.ProjectExt.libs
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(extension: CommonExtension<*, *, *, *, *, *>) {
    extension.apply {

        buildFeatures {
            compose = true
        }

        dependencies {
            val composeBom: Dependency = platform("androidx.compose:compose-bom:2024.08.00")
            implementation(composeBom)
            androidTestImplementation(composeBom)
            // Material Design 3
            implementation(libs.findLibrary("androidx.compose.material3:material3").get())
            // Material Design
            implementation(libs.findLibrary("androidx.compose.material").get())
            // Android Studio Preview support
            implementation(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            debugImplementation(libs.findLibrary("androidx.compose.ui.tooling").get())
            // UI Tests
            androidTestImplementation(libs.findLibrary("androidx.compose.ui.test.junit4").get())
            debugImplementation(libs.findLibrary("androidx.compose.ui.test.manifest").get())
            // Optional - Included automatically by material, only add when you need
            // the icons but not the material library (e.g. when using Material3 or a
            // custom design system based on Foundation)
            implementation(libs.findLibrary("androidx.compose.material.icons.core").get())
            // Optional - Add full set of material icons
            implementation(libs.findLibrary("androidx.compose.material.icons.extended").get())
            // Optional - Add window size utils
            implementation(libs.findLibrary("androidx.compose.material3.window.size").get())
            // Optional - Integration with LiveData
            implementation(libs.findLibrary("androidx.compose.runtime.livedata").get())
            // Optional - Integration with activities
            implementation(libs.findLibrary("androidx.compose.activity").get())
            // Optional - Integration with ViewModels
            implementation(libs.findLibrary("androidx.compose.viewmodel").get())
            // Optional - Integration with View Binding
            implementation(libs.findLibrary("androidx.compose.ui.viewbinding").get())
        }

        testOptions {
            unitTests {
                // For Robolectric
                isIncludeAndroidResources = true
            }
        }
    }
}