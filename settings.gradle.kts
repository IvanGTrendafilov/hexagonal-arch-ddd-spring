// ---------------------------------------------------
// Standard repositories setup
// ---------------------------------------------------
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

// ---------------------------------------------------
// Project specific settings
// ---------------------------------------------------
rootProject.name = "greeting-service"

include("api", "domain", "data", "client", "server")
