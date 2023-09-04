// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        // Lottie
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }

    dependencies {

        // Hilt
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")

        // Google services
        // Add the dependency for the Google services Gradle plugin
        classpath("com.google.gms:google-services:4.3.15")
        // Add the dependency for the Crashlytics Gradle plugin
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        // Add the dependency for the Performance Monitoring Gradle plugin
        classpath("com.google.firebase:perf-plugin:1.4.2")
    }
}

plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.46.1" apply false

    // [Migrate from kapt to KSP](https://developer.android.com/build/migrate-to-ksp#groovy)
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}