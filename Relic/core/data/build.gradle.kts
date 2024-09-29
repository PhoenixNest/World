plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // KSP
    alias(libs.plugins.kotlin.symbol.processing)

    // Parcelize Models
    id("kotlin-parcelize")
}

android {
    namespace = "io.data"
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

    /* ======================== Module ======================== */

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Gson
    implementation(libs.gson)

    /* ======================== Third-party Extension ======================== */

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
}