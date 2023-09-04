plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // KSP
    id("com.google.devtools.ksp")

    // Parcelize Models
    id("kotlin-parcelize")

    // Room, Hilt
    id("kotlin-kapt")

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
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    /* ======================== Google Official Extension ======================== */

    // dependencies

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Compose Ui
    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
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
    implementation("androidx.activity:activity-compose:1.7.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // Accompanist Components
    implementation("com.google.accompanist:accompanist-permissions:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-navigation-material:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-adaptive:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.31.2-alpha")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

    // Firebase BOM
    val firebaseBom = platform("com.google.firebase:firebase-bom:32.0.0")
    implementation(firebaseBom)
    // Add the dependencies for the Crashlytics and Analytics libraries
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    // Add the dependency for the Performance Monitoring library
    implementation("com.google.firebase:firebase-perf-ktx")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Datastore
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    androidTestImplementation("androidx.room:room-testing:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    // WorkManager
    implementation("androidx.work:work-runtime:2.8.1")
    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    // optional - GCMNetworkManager support
    implementation("androidx.work:work-gcm:2.8.1")
    // optional - Test helpers
    androidTestImplementation("androidx.work:work-testing:2.8.1")
    // optional - Multiprocess support
    implementation("androidx.work:work-multiprocess:2.8.1")
    // optional - Integration with WorkManager
    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
    // alternatively - without Android dependencies for tests
    testImplementation("androidx.paging:paging-common-ktx:3.2.0")
    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:3.2.0")

    // Location Services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Admob
    implementation("com.google.android.gms:play-services-ads:22.3.0")

    // Google sign-in
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    /* ======================== Third-party Extension ======================== */

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.moshi:moshi-adapters:1.15.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // LoggingInterceptor - Interceptor for OkHttp3 with pretty logger
    implementation("com.github.ihsanbal:LoggingInterceptor:3.1.0") {
        exclude(group = "org.json", module = "json")
    }

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:6.0.0")

    // Amap
    implementation("com.amap.api:navi-3dmap:latest.integration")
    implementation("com.amap.api:search:latest.integration")
}