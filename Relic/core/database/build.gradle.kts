plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)

    // KSP
    alias(libs.plugins.kotlin.symbol.processing)

    // Parcelize Models
    id("kotlin-parcelize")

    // Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "io.core.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        // Specify the schemas saved location of Room database.
        ksp {
            arg("room.schemaLocation", "${projectDir}/room_database_schemas")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    /* ======================== Module ======================== */

    // Core Module
    implementation(project(":core:data"))

    // Common Module
    implementation(project(":common"))

    /* ======================== Google Official Extension ======================== */

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    androidTestImplementation(libs.room.testing)
    ksp(libs.room.compiler)

    /* ======================== Third-party Extension ======================== */

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
}