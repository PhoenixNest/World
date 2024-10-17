plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    // Compose
    id("org.jetbrains.kotlin.plugin.compose")

    // KSP
    id("com.google.devtools.ksp")

    // Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "io.module.media"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    /* ======================== Google Official Extension ======================== */

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")

    // Compose Ui
    val composeBom = platform("androidx.compose:compose-bom:2024.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    // Material Design 3
    implementation("androidx.compose.material3:material3")
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
    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.9.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-compiler:2.52")

    // Media info
    implementation("androidx.exifinterface:exifinterface:1.3.6")

    // Activity
    implementation("androidx.activity:activity:1.9.2")

    // Activity-KTX
    implementation("androidx.activity:activity-ktx:1.9.2")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.5")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    /* ======================== Media Library Extension ======================== */

    // CameraX
    val cameraxVersion = "1.2.2"
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:${cameraxVersion}")
    implementation("androidx.camera:camera-video:${cameraxVersion}")

    implementation("androidx.camera:camera-view:${cameraxVersion}")
    implementation("androidx.camera:camera-extensions:${cameraxVersion}")

    // Media3
    val media3Version = "1.4.1"
    implementation("androidx.media3:media3-exoplayer:${media3Version}")
    implementation("androidx.media3:media3-exoplayer-dash:${media3Version}")
    implementation("androidx.media3:media3-ui:${media3Version}")

    /* ======================== Third-party Extension ======================== */

    // Lottie
    val lottieVersion = "6.5.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")
}