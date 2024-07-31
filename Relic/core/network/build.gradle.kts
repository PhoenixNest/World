import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev Key
private val localProperties = gradleLocalProperties(rootDir, project.providers)
private val hitokotoDevKey = localProperties.getProperty("HITOKOTO_DEV_KEY") ?: "-1"
private val newsDevKey = localProperties.getProperty("NEWS_DEV_KEY") ?: "-1"
private val pixabayDevKey = localProperties.getProperty("PIXABAY_DEV_KEY") ?: "-1"
private val spoonacularDevKey = localProperties.getProperty("SPOONACULAR_DEV_KEY") ?: "-1"

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Parcelize Models
    id("kotlin-parcelize")

    // Hilt
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "io.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        resValue("string", "hitokoto_dev_key", hitokotoDevKey)
        resValue("string", "news_dev_key", newsDevKey)
        resValue("string", "pixabay_dev_key", pixabayDevKey)
        resValue("string", "spoonacular_dev_key", spoonacularDevKey)
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

    // Core Module
    implementation(project(":core:data"))

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    /* ======================== Third-party Extension ======================== */

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.moshi)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // LoggingInterceptor - Interceptor for OkHttp3 with pretty logger
    implementation(libs.prettyLoggingInterceptor) {
        exclude(group = "org.json", module = "json")
    }
}