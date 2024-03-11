import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir)
private val isDebugMode = localProperties.getProperty("DEBUG_MODE")
private val isNoAds = localProperties.getProperty("NO_ADS")

// Dev Key
private val admobDevKey = localProperties.getProperty("ADMOB_DEV_KEY")

// Library config
private val composeCompilerVersion = "1.5.10"

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Parcelize Models
    id("kotlin-parcelize")

    // Hilt
    alias(libs.plugins.hiltAndroid)

    // Firebase
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
    // Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
    // Add the Performance Monitoring Gradle plugin
    id("com.google.firebase.firebase-perf")
}

android {
    namespace = "io.dev.relic"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.dev.relic"
        minSdk = 26
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("boolean", "DEBUG_MODE", isDebugMode)

        resValue("string", "admob_dev_key", admobDevKey)

        vectorDrawables {
            useSupportLibrary = true
        }

        // Ndk .so library support
        ndk {
            abiFilters.addAll(
                listOf(
                    "armeabi", "armeabi-v7a", "arm64-v8a",
                    "x86", "x86_64"
                )
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                "proguard-rules.pro",
                "proguard-rules/coroutines.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
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

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }

    packaging {
        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")
    }
}

dependencies {

    /* ======================== Module ======================== */

    // Domain Layer
    implementation(project(":domain"))

    // Ad Module
    implementation(project(":module:ad"))

    // Subscribe Module
    implementation(project(":module:subscribe"))

    // Debug Module
    implementation(project(":module:debug"))

    // Agent Module
    implementation(project(":agent:gemini"))

    // Map Module
    implementation(project(":module:map"))

    /* ======================== Google Official Extension ======================== */

    // Firebase BOM
    val firebaseBom: Dependency = platform("com.google.firebase:firebase-bom:32.7.4")
    implementation(firebaseBom)
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    // Add the dependency for the Performance Monitoring library
    implementation(libs.firebase.perf.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Google sign-in
    implementation(libs.play.services.auth)

    /* ======================== Third-party Extension ======================== */

    // LeakCanary
    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(libs.leakcanary.android)
}