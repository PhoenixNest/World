// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {

        // To add Safe Args to your project, include the following classpath in your top level build.gradle file:
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)

        // Hilt
        classpath(libs.hilt.android.gradle.plugin)

        // Google services
        // Add the dependency for the Google services Gradle plugin
        classpath(libs.google.services)
        // Add the dependency for the Crashlytics Gradle plugin
        classpath(libs.firebase.crashlytics.gradle)
        // Add the dependency for the Performance Monitoring Gradle plugin
        classpath(libs.firebase.perf.plugin)
    }
}

plugins {

    /**
     * Use `apply false` in the top-level build.gradle file to add a Gradle
     * plugin as a build dependency but not apply it to the current (root)
     * project. Don't use `apply false` in sub-projects. For more information,
     * see Applying external plugins with same version to subprojects.
     */

    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false

    // Hilt
    alias(libs.plugins.hiltAndroid) apply false

    // [Migrate from kapt to KSP](https://developer.android.com/build/migrate-to-ksp#groovy)
    alias(libs.plugins.kotlinSymbolProcessingAndroid) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false

    // Compose Compiler
    alias(libs.plugins.compose.compiler) apply false
}