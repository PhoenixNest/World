plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // Compose
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "io.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
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
}

dependencies {

    /* ======================== Module ======================== */

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Legacy
    api(libs.material)
    api(libs.androidx.recyclerview)
    api(libs.constraintlayout)
    api(libs.androidx.cardview)

    // Compose Ui
    val composeBom: Dependency = platform("androidx.compose:compose-bom:2024.08.00")
    api(composeBom)
    androidTestApi(composeBom)
    // Material Design 3
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive.navigation.suite)
    // Android Studio Preview support
    api(libs.androidx.compose.ui.tooling.preview)
    debugApi(libs.androidx.compose.ui.tooling)
    // UI Tests
    androidTestApi(libs.androidx.compose.ui.test.junit4)
    debugApi(libs.androidx.compose.ui.test.manifest)
    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    api(libs.androidx.compose.material.icons.core)
    // Optional - Add full set of material icons
    api(libs.androidx.compose.material.icons.extended)
    // Optional - Add window size utils
    api(libs.androidx.compose.material3.window.size)
    // Optional - Integration with LiveData
    api(libs.androidx.compose.runtime.livedata)
    // Optional - Integration with activities
    api(libs.androidx.compose.activity)
    // Optional - Integration with ViewModels
    api(libs.androidx.compose.viewmodel)
    // Optional - Integration with View Binding
    api(libs.androidx.compose.ui.viewbinding)

    // Google fonts
    implementation(libs.androidx.ui.text.google.fonts)

    /* ======================== Third-party Extension ======================== */

    // Coil
    api(libs.coil.compose)

    // Lottie
    api(libs.lottie)
    api(libs.lottie.compose)
}