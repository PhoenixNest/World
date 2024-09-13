package io.dev.build.convention.configure

import com.android.build.api.dsl.CommonExtension
import io.dev.build.convention.ext.DependencyHandlerScopeExt.androidTestApi
import io.dev.build.convention.ext.DependencyHandlerScopeExt.api
import io.dev.build.convention.ext.DependencyHandlerScopeExt.debugApi
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
            api(composeBom)
            androidTestApi(composeBom)
            // Material Design
            api(libs.findLibrary("androidx.compose.material").get())
            // Android Studio Preview support
            api(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            debugApi(libs.findLibrary("androidx.compose.ui.tooling").get())
            // UI Tests
            androidTestApi(libs.findLibrary("androidx.compose.ui.test.junit4").get())
            debugApi(libs.findLibrary("androidx.compose.ui.test.manifest").get())
            // Optional - Included automatically by material, only add when you need
            // the icons but not the material library (e.g. when using Material3 or a
            // custom design system based on Foundation)
            api(libs.findLibrary("androidx.compose.material.icons.core").get())
            // Optional - Add full set of material icons
            api(libs.findLibrary("androidx.compose.material.icons.extended").get())
            // Optional - Add window size utils
            api(libs.findLibrary("androidx.compose.material3.window.size").get())
            // Optional - Integration with LiveData
            api(libs.findLibrary("androidx.compose.runtime.livedata").get())
            // Optional - Integration with activities
            api(libs.findLibrary("androidx.compose.activity").get())
            // Optional - Integration with ViewModels
            api(libs.findLibrary("androidx.compose.viewmodel").get())
            // Optional - Integration with View Binding
            api(libs.findLibrary("androidx.compose.ui.viewbinding").get())
        }
    }
}