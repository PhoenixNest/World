import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val isDebugMode: String = gradleLocalProperties(rootDir).getProperty("DEBUG_MODE")
private val isNoAds: String = gradleLocalProperties(rootDir).getProperty("NO_ADS")

// Dev Key
private val admobDevKey: String = gradleLocalProperties(rootDir).getProperty("ADMOB_DEV_KEY")

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Parcelize Models
    id("kotlin-parcelize")

    // SafeArgs
    id("androidx.navigation.safeargs.kotlin")

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
        minSdk = 24
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
                    "armeabi",
                    "armeabi-v7a",
                    "arm64-v8a",
                    "x86",
                    "x86_64"
                )
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                "proguard-rules.pro",
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
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")
    }
}

dependencies {

    /* ======================== Module ======================== */

    // Domain Layer
    implementation(project(":domain"))

    // Map Module
    implementation(project(":module:map"))

    // Ad Module
    implementation(project(":module:ad"))

    /* ======================== Google Official Extension ======================== */

    // Firebase BOM
    val firebaseBom: Dependency = platform("com.google.firebase:firebase-bom:32.0.0")
    implementation(firebaseBom)
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    // Add the dependency for the Performance Monitoring library
    implementation("com.google.firebase:firebase-perf-ktx")

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