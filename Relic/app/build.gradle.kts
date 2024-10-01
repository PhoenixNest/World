import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// App config
private val localProperties = gradleLocalProperties(rootDir, project.providers)
private val isDebugMode = localProperties.getProperty("DEBUG_MODE") ?: "false"
private val isNoAds = localProperties.getProperty("NO_ADS") ?: "true"

// Dev Key
private val admobDevKey = localProperties.getProperty("ADMOB_DEV_KEY") ?: "-1"

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // KSP
    alias(libs.plugins.kotlin.symbol.processing)

    // Hilt
    alias(libs.plugins.hilt.android)

    // Compose
    alias(libs.plugins.compose.compiler)

    // Parcelize Models
    id("kotlin-parcelize")

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
         * [Configure build types ](https://developer.android.com/build/build-variants#build-types)
         * */
        debug {
            isDebuggable = true
        }

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

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
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

    // Media Module
    implementation(project(":module:media"))

    /* ======================== Google Official Extension ======================== */

    // Firebase BOM
    val firebaseBom: Dependency = platform("com.google.firebase:firebase-bom:33.1.2")
    implementation(firebaseBom)
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
    // Add the dependency for the Performance Monitoring library
    implementation(libs.firebase.performance.ktx)

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