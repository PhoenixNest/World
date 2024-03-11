plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Parcelize Models
    id("kotlin-parcelize")

    // SafeArgs
    id("androidx.navigation.safeargs.kotlin")

    // Hilt
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "io.module.debug"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    /* ======================== Module ======================== */

    // Domain Layer
    implementation(project(":domain"))

    // Map Module
    implementation(project(":module:map"))

    /* ======================== Google Official Extension ======================== */

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}