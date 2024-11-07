package io.dev.build.convention.configure

import com.android.build.api.dsl.CommonExtension
import io.dev.build.convention.ext.DependencyHandlerScopeExt.androidTestImplementation
import io.dev.build.convention.ext.DependencyHandlerScopeExt.implementation
import io.dev.build.convention.ext.DependencyHandlerScopeExt.testImplementation
import io.dev.build.convention.ext.ProjectExt.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidUnitTest(extension: CommonExtension<*, *, *, *, *, *>) {
    extension.apply {
        dependencies {
            implementation(libs.findLibrary("junit").get())
            androidTestImplementation(libs.findLibrary("ext.junit").get())
            androidTestImplementation(libs.findLibrary("espresso.core").get())
            // Optional -- Robolectric environment
            testImplementation(libs.findLibrary("androidx.test.core").get())
            // Optional -- Mockito framework
            testImplementation(libs.findLibrary("mockito.core").get())
            // Optional -- Mockito android
            androidTestImplementation(libs.findLibrary("mockito.android").get())
            // Optional -- mockito-kotlin
            androidTestImplementation(libs.findLibrary("mockito.kotlin").get())
            // Optional -- Mockk framework
            testImplementation(libs.findLibrary("mockk").get())
        }
    }
}