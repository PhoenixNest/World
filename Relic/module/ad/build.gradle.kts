import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

// App config
private val localProperties: Properties = gradleLocalProperties(rootDir)
private val isNoAds: String = localProperties.getProperty("NO_ADS")

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "io.module.ad"
    compileSdk = 34

    defaultConfig {
        buildConfigField("boolean", "NO_ADS", isNoAds)
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

    // Admob
    implementation(libs.play.services.ads)
}