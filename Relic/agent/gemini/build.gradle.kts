import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val geminiDevKey: String =
    gradleLocalProperties(rootDir).getProperty("AGENT_GEMINI_DEV_KEY")

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
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        resValue("string", "agent_gemini_dev_key", geminiDevKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    implementation("com.google.ai.client.generativeai:generativeai:0.1.1")

}