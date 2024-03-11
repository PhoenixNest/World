// Library config
private val composeCompilerVersion = "1.5.10"

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Hilt
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "io.module.subscribe"
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
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {

    /* ======================== Module ======================== */

    implementation(project(":core:datastore"))
    implementation(project(":core:ui"))

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}