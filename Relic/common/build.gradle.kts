// Utils Config
private val logMode = (project.property("LOG_MODE") ?: "false").toString()

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // KSP
    alias(libs.plugins.kotlin.symbol.processing)

    // Parcelize Models
    id("kotlin-parcelize")

    // SafeArgs
    id("androidx.navigation.safeargs.kotlin")

    // Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "io.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        buildConfigField("boolean", "LOG_MODE", logMode)
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

    api(libs.android.ktx)
    api(libs.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Legacy-Navigation
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    // Optional - Feature module Support
    api(libs.androidx.navigation.dynamic.features.fragment)
    // Optional - Jetpack Compose Integration
    api(libs.navigation.compose)

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