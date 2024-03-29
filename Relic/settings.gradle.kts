rootProject.name = "Relic"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")

        // TomTom Map
        maven("https://repositories.tomtom.com/artifactory/maven") {
            content { includeGroupByRegex("com\\.tomtom\\..+") }
        }

        // Lottie
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

// Extension module
include(":module:ad")
include(":module:map")
include(":module:location")
include(":agent:gemini")

// Feature layer
include(":app")

// Domain layer
include(":domain")
include(":common")

// Core layer
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:data")
include(":core:ui")
