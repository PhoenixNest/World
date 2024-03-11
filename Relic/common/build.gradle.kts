plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.kotlinSymbolProcessingAndroid)

    // Parcelize Models
    id("kotlin-parcelize")

    // SafeArgs
    id("androidx.navigation.safeargs.kotlin")

    // Hilt
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "io.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                "proguard-rules.pro",
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
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

    api(libs.android.ktx)
    api(libs.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Legacy-Navigation
    api("androidx.navigation:navigation-fragment-ktx:2.7.7")
    api("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Optional - Feature module Support
    api("androidx.navigation:navigation-dynamic-features-fragment:2.7.7")
    // Optional - Jetpack Compose Integration
    api("androidx.navigation:navigation-compose:2.7.7")

    // Navigation
    api(libs.navigation.compose)
    api(libs.hilt.navigation.compose)

    // Lifecycle
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.runtime.compose)
    api(libs.lifecycle.extensions)

    // WorkManager
    api(libs.androidx.work.runtime)
    // Kotlin + coroutines
    api(libs.androidx.work.runtime.ktx)
    // Optional - GCMNetworkManager support
    api(libs.androidx.work.gcm)
    // Optional - Test helpers
    androidTestApi(libs.androidx.work.testing)
    // Optional - Multiprocess support
    api(libs.androidx.work.multiprocess)
    // Optional - Integration with WorkManager
    api(libs.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    // Coroutines
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
    testApi(libs.kotlinx.coroutines.test)

    // Paging
    api(libs.androidx.paging.runtime.ktx)
    // alternatively - without Android dependencies for tests
    testApi(libs.androidx.paging.common.ktx)
    // Optional - Jetpack Compose integration
    api(libs.androidx.paging.compose)

    // Accompanist Components
    api(libs.accompanist.adaptive)
    api(libs.accompanist.navigation.animation)
    api(libs.accompanist.navigation.material)
    api(libs.accompanist.placeholder.material)
    api(libs.accompanist.permissions)
    api(libs.accompanist.systemuicontroller)
}