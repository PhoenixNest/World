import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev Key
private val localProperties = gradleLocalProperties(rootDir)
private val aMapDevKay = localProperties.getProperty("AMAP_DEV_KEY")
private val tomtomDevKey = localProperties.getProperty("TOMTOM_DEV_KEY")

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
    namespace = "io.module.map"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        resValue("string", "amap_dev_key", aMapDevKay)
        resValue("string", "tomtom_dev_key", tomtomDevKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                "proguard-rules/amap.pro",
                "proguard-rules/tomtom-map.pro",
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

    // Location Services
    api(libs.play.services.location)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    /* ======================== Third-party Extension ======================== */

    // AMap
    api(libs.amap.navi)
    api(libs.amap.search)

    // TomTom
    api(libs.tomtom.map.display) {
        exclude(group = "androidx.compose", module = "androidx")
    }
    api(libs.tomtom.map.provider.android)
}