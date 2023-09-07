import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev config
private val admobDevKey: String = gradleLocalProperties(rootDir).getProperty("admob_dev_key")
private val amapDevKay: String = gradleLocalProperties(rootDir).getProperty("amap_dev_key")

// App config
private val isNoAds: String = extra["NO_ADS"].toString()

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // KSP
    id("com.google.devtools.ksp")

    // Parcelize Models
    id("kotlin-parcelize")

    // Hilt
    id("com.google.dagger.hilt.android")

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

        buildConfigField("boolean", "NO_ADS", isNoAds)

        resValue("string", "admob_dev_key", admobDevKey)
        resValue("string", "amap_dev_key", amapDevKay)

        vectorDrawables {
            useSupportLibrary = true
        }

        // Specify the schemas saved location of Room database.
        ksp {
            arg("room.schemaLocation", "${projectDir}/room_database_schemas")
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
        kotlinCompilerExtensionVersion = "1.4.2"
    }
}

dependencies {

    /* ======================== Google Official Extension ======================== */

    implementation(libs.android.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Legacy
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)

    // Compose Ui
    val composeBom: Dependency = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    // Material Design
    implementation("androidx.compose.material:material")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementation("androidx.compose.material3:material3-window-size-class")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with activities
    implementation(libs.androidx.activity.compose)
    // Optional - Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Accompanist Components
    implementation(libs.accompanist.adaptive)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.placeholder.material)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)

    // Firebase BOM
    val firebaseBom: Dependency = platform("com.google.firebase:firebase-bom:32.0.0")
    implementation(firebaseBom)
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    // Add the dependency for the Performance Monitoring library
    implementation("com.google.firebase:firebase-perf-ktx")

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.extensions)

    // Datastore
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.room.testing)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // WorkManager
    implementation(libs.androidx.work.runtime)
    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    // optional - GCMNetworkManager support
    implementation(libs.androidx.work.gcm)
    // optional - Test helpers
    androidTestImplementation(libs.androidx.work.testing)
    // optional - Multiprocess support
    implementation(libs.androidx.work.multiprocess)
    // optional - Integration with WorkManager
    implementation(libs.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    // alternatively - without Android dependencies for tests
    testImplementation(libs.androidx.paging.common.ktx)
    // optional - Jetpack Compose integration
    implementation(libs.androidx.paging.compose)

    // Location Services
    implementation(libs.play.services.location)

    // Gson
    implementation(libs.gson)

    // Admob
    implementation(libs.play.services.ads)

    // Google sign-in
    implementation(libs.play.services.auth)

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
    implementation("com.github.ihsanbal:LoggingInterceptor:3.1.0") {
        exclude(group = "org.json", module = "json")
    }

    // Coil
    implementation(libs.coil.compose)

    // Lottie
    implementation(libs.lottie.compose)

    // Amap
    implementation("com.amap.api:navi-3dmap:latest.integration")
    implementation("com.amap.api:search:latest.integration")
}