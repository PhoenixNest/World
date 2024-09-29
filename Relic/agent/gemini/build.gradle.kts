import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir, project.providers)
private val geminiDevKey = localProperties.getProperty("AGENT_GEMINI_DEV_KEY") ?: "-1"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.agent.gemini"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        resValue("string", "agent_gemini_dev_key", geminiDevKey)
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

    // Coroutines
    val coroutinesVersion = "1.8.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    // Google AI client SDK for Android
    api("com.google.ai.client.generativeai:generativeai:0.9.0")

}