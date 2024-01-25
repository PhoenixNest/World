import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

// Dev Key
private val localProperties: Properties = gradleLocalProperties(rootDir)
private val spoonacularDevKey: String = localProperties.getProperty("SPOONACULAR_DEV_KEY")
private val newsDevKey: String = localProperties.getProperty("NEWS_DEV_KEY")
private val pixabaySecretKey: String = localProperties.getProperty("PIXABAY_SECRET_KEY")

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
    namespace = "io.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        resValue("string", "spoonacular_dev_key", spoonacularDevKey)
        resValue("string", "news_dev_key", newsDevKey)
        resValue("string", "pixabay_secret_key", pixabaySecretKey)
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