// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        // Lottie
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    dependencies {

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
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false

    // [Migrate from kapt to KSP](https://developer.android.com/build/migrate-to-ksp#groovy)
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}