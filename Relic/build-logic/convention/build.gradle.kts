import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    java
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "io.dev.relic.build_logic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.tools.build)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradle.plugin)
    compileOnly(libs.firebase.crashlytics.gradle.plugin)
    compileOnly(libs.firebase.performance.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlin.symbol.processing.gradle.plugin)

    // ASM
    implementation(libs.asm)
    implementation(libs.asm.util)
    implementation(libs.asm.commons)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidComposeLibrary") {
            id = "android.compose.library"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }
        register("androidFirebaseLibrary") {
            id = "android.firebase"
            implementationClass = "AndroidFirebaseConventionPlugin"
        }
        register("androidHiltPlugin") {
            id = "android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoomPlugin") {
            id = "android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("sentryPlugin") {
            id = "sentry.plugin"
            implementationClass = "SentryPlugin"
        }
        register("outputPlugin") {
            id = "output.plugin"
            implementationClass = "OutputPlugin"
        }
    }
}