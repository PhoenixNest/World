import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev Key
private val localProperties = gradleLocalProperties(rootDir)
private val tomtomDevKey = localProperties.getProperty("TOMTOM_DEV_KEY") ?: "-1"

// Library config
private val composeCompilerVersion = "1.5.10"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    // KSP
    id("com.google.devtools.ksp")

    // Hilt
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "io.module.map"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        resValue("string", "tomtom_dev_key", tomtomDevKey)
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

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {

    /* ======================== Google Official Extension ======================== */

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    // Compose Ui
    val composeBom = platform("androidx.compose:compose-bom:2024.03.00")
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
    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.8.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")

    /* ======================== Third-party Extension ======================== */

    // TomTom - Map display
    implementation("com.tomtom.sdk.maps:map-display:0.50.6"){
        exclude(group = "com.google.protobuf")
    }
    // TomTom - Location provider
    implementation("com.tomtom.sdk.location:provider-android:0.50.6")
    // TomTom - Map search online services
    implementation("com.tomtom.sdk.search:search-online:0.50.6")

    // Lottie
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("com.airbnb.android:lottie-compose:6.4.0")
}