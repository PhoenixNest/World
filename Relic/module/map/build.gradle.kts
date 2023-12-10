import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev Key
private val aMapDevKay: String = gradleLocalProperties(rootDir).getProperty("AMAP_DEV_KEY")
private val tomtomDevKey: String = gradleLocalProperties(rootDir).getProperty("TOMTOM_DEV_KEY")

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
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        resValue("string", "amap_dev_key", aMapDevKay)
        resValue("string", "tomtom_dev_key", tomtomDevKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
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
    implementation("com.tomtom.sdk.location:provider-gms:0.40.0")
    implementation("com.tomtom.sdk.location:provider-proxy:0.40.0")
    implementation("com.tomtom.sdk.location:provider-api:0.40.0")
}