import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir)
private val geminiDevKey = localProperties.getProperty("AGENT_GEMINI_DEV_KEY")

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Parcelize Models
    id("kotlin-parcelize")


    // Hilt
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "io.agent.gemini"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        resValue("string", "agent_gemini_dev_key", geminiDevKey)
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
}

dependencies {

    /* ======================== Module ======================== */

    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:ui"))

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Google AI client SDK for Android
    api("com.google.ai.client.generativeai:generativeai:0.2.1")

}