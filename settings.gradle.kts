// ---------------------------------------------------
// Standard repositories setup
// ---------------------------------------------------
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
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

allprojects {
    group = "com.hex.arch"
}

include("api", "domain", "data", "client", "server")
