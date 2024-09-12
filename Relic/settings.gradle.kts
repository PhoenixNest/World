rootProject.name = "Relic"

/**
 * The pluginManagement.repositories block configures the
 * repositories Gradle uses to search or download the Gradle plugins and
 * their transitive dependencies. Gradle pre-configures support for remote
 * repositories such as JCenter, Maven Central, and Ivy. You can also use
 * local repositories or define your own remote repositories. The code below
 * defines the Gradle Plugin Portal, Google's Maven repository,
 * and the Maven Central Repository as the repositories Gradle should use to look for its
 * dependencies.
 */
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {

    /**
     * The dependencyResolutionManagement.repositories block
     * is where you configure the repositories and dependencies used by
     * all modules in your project, such as libraries that you are using to
     * create your application. However, you should configure module-specific
     * dependencies in each module-level build.gradle file. For new projects,
     * Android Studio includes Google's Maven repository and the Maven Central
     * Repository by default, but it does not configure any dependencies (unless
     * you select a template that requires some).
     */
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
include(
    ":module:ad",
    ":module:map",
    ":module:location",
    ":agent:gemini"
)

// Feature layer
include(":app")

// Domain layer
include(
    ":domain",
    ":common"
)

// Core layer
include(
    ":core:database",
    ":core:datastore",
    ":core:network",
    ":core:data",
    ":core:ui"
)