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
        jcenter()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repositories.tomtom.com/artifactory/maven") }

        // Lottie
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}
rootProject.name = "Relic"
include(":app")
include(":common")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:data")
include(":core:ui")
include(":domain")
include(":module:map")
include(":module:ad")
include(":module:subscribe")
include(":module:debug")
