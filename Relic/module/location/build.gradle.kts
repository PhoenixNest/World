plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    // KSP
    id("com.google.devtools.ksp")

    // Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "io.module.location"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    /* ======================== Google Official Extension ======================== */

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.2.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")
}