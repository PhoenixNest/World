import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir)
private val isDebugMode = localProperties.getProperty("DEBUG_MODE") ?: "false"
private val isNoAds = localProperties.getProperty("NO_ADS") ?: "true"

// Dev Key
private val admobDevKey = localProperties.getProperty("ADMOB_DEV_KEY") ?: "-1"

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

        /**
         * [Shrink, obfuscate, and optimize your app](https://developer.android.com/build/shrink-code)
         * */
        release {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),

                // List additional ProGuard rules for the given build type here. By default,
                // Android Studio creates and includes an empty rules file for you (located
                // at the root directory of each module).
                "proguard-rules.pro",
                "proguard-rules/coroutines.pro",
                "proguard-rules/gson-android.pro",
                "proguard-rules/retrofit2-android.pro",
                "proguard-rules/okhttp3-android.pro",
                "proguard-rules/tomtom-map.pro"
            )
        }

        // Rename the output file
        applicationVariants.forEach { variant ->
            variant.outputs.all {
                if (outputFile != null && outputFile.endsWith("apk")) {
                    val outputFilePrefix = when (variant.buildType.name) {
                        "debug" -> "relic_debug"
                        "release" -> "relic_release"
                        else -> "unknown"
                    }

                    (this as BaseVariantOutputImpl).outputFileName = "${outputFilePrefix}.apk"
                }
            }
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

    configurations {
        implementation.configure {
            exclude(module = "protobuf-javalite")
            exclude(module = "protolite-well-known-types")
        }
    }
}

dependencies {

    /* ======================== Module ======================== */

    // Domain Layer
    implementation(project(":domain"))

    // Ad Module
    implementation(project(":module:ad"))

    // Agent Module
    implementation(project(":agent:gemini"))

    // Location Module
    implementation(project(":module:location"))

    // Map Module
    implementation(project(":module:map"))

    /* ======================== Google Official Extension ======================== */

    // Firebase BOM
    val firebaseBom: Dependency = platform("com.google.firebase:firebase-bom:32.8.0")
    implementation(firebaseBom)
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    // Add the dependency for the Performance Monitoring library
    implementation(libs.firebase.perf.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    /* ======================== Third-party Extension ======================== */

    // LeakCanary
    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(libs.leakcanary.android)

    // Compose Markdown
    implementation(libs.compose.markdown)
}