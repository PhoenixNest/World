import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

// Dev Key
private val localProperties = gradleLocalProperties(rootDir, project.providers)
private val tomtomDevKey = localProperties.getProperty("TOMTOM_DEV_KEY") ?: "-1"

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
    implementation("androidx.activity:activity-compose:1.9.1")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-compiler:2.52")

    /* ======================== Third-party Extension ======================== */

    // TomTom
    val tomtomMapVersion = "1.10.0"
    // Oribs Map
    implementation("com.tomtom.sdk:feature-toggle:$tomtomMapVersion")
    // The Search module provides easy-to-use APIs to add search functionality to your application.
    // It is built on top of TomTom Search APIs and has offline support.
    implementation("com.tomtom.sdk.search:search-online:$tomtomMapVersion")
    // The Location module provides an interface for obtaining and working with location updates.
    // It provides implementations for getting an actual user location with and without Google Services,
    // as well as one for simulating locations.
    implementation("com.tomtom.sdk.location:provider-android:$tomtomMapVersion")
    implementation("com.tomtom.sdk.location:provider-map-matched:$tomtomMapVersion")
    implementation("com.tomtom.sdk.location:provider-simulation:$tomtomMapVersion")
    // The Map Display module provides a set of tools that enable embedding the TomTom map
    // in Android applications. It makes it easy to add visual components to the map,
    // such as routes and markers.
    implementation("com.tomtom.sdk.maps:map-display:$tomtomMapVersion")
    // The Routing module provides the tools to integrate the TomTom Routing API into an
    // Android application. It is used to calculate a route between an origin and a destination,
    // using a range of options and taking traffic into account.
    implementation("com.tomtom.sdk.datamanagement:navigation-tile-store:$tomtomMapVersion")
    implementation("com.tomtom.sdk.navigation:navigation-online:$tomtomMapVersion")
    implementation("com.tomtom.sdk.navigation:ui:$tomtomMapVersion")
    implementation("com.tomtom.sdk.routing:route-planner-online:$tomtomMapVersion")

    // Lottie
    val lottieVersion = "6.5.0"
    implementation("com.airbnb.android:lottie:$lottieVersion")
    implementation("com.airbnb.android:lottie-compose:$lottieVersion")
}