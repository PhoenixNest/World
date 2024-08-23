import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir, project.providers)
private val isNoAds = localProperties.getProperty("NO_ADS") ?: "false"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.module.ad"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        buildConfigField("boolean", "NO_ADS", isNoAds)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    /* ======================== Google Official Extension ======================== */

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-process:2.8.4")

    // Admob
    implementation("com.google.android.gms:play-services-ads:23.3.0")
}