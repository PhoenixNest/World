import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev Key
private val spoonacularDevKey: String = gradleLocalProperties(rootDir).getProperty("SPOONACULAR_DEV_KEY")

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
    namespace = "io.module.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        resValue("string", "spoonacular_dev_key", spoonacularDevKey)
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
}

dependencies {

    /* ======================== Module ======================== */

    implementation(project(":core:common"))
    implementation(project(":core:data"))

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