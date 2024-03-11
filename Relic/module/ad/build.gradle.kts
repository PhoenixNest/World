import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir)
private val isNoAds = localProperties.getProperty("NO_ADS")

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "io.module.ad"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        buildConfigField("boolean", "NO_ADS", isNoAds)
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
        buildConfig = true
    }
}

dependencies {

    /* ======================== Module ======================== */

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Admob
    implementation(libs.play.services.ads)
}