plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "io.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
}

dependencies {

    /* ======================== Module ======================== */

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Legacy
    api(libs.material)
    api(libs.androidx.recyclerview)

    // Compose Ui
    val composeBom: Dependency = platform("androidx.compose:compose-bom:2023.10.00")
    api(composeBom)
    androidTestApi(composeBom)
    // Material Design
    api("androidx.compose.material:material")
    // Android Studio Preview support
    api("androidx.compose.ui:ui-tooling-preview")
    debugApi("androidx.compose.ui:ui-tooling")
    // UI Tests
    androidTestApi("androidx.compose.ui:ui-test-junit4")
    debugApi("androidx.compose.ui:ui-test-manifest")
    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    api("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    api("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    api("androidx.compose.material3:material3-window-size-class")
    // Optional - Integration with LiveData
    api("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with activities
    api(libs.androidx.activity.compose)
    // Optional - Integration with ViewModels
    api(libs.androidx.lifecycle.viewmodel.compose)

    // ConstraintLayout
    api(libs.constraintlayout)

    // CardView
    api(libs.androidx.cardview)

    /* ======================== Third-party Extension ======================== */

    // Coil
    api(libs.coil.compose)

    // Lottie
    api(libs.lottie.compose)
}