plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // KSP
    alias(libs.plugins.kotlin.symbol.processing)

    // Parcelize Models
    id("kotlin-parcelize")

    // Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "io.domain"
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

    // Core Module
    api(project(":core:data"))
    api(project(":core:database"))
    api(project(":core:datastore"))
    api(project(":core:network"))
    api(project(":core:ui"))

    // Common Module
    api(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

}