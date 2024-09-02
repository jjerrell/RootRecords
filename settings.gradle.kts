enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RootRecords"
include(":androidApp")
include(":shared")
include(":androidApp:feature-category")
include(":androidApp:feature-settings")

include(":androidApp:ui:navigation")
include(":androidApp:ui:theme")
include(":androidApp:ui:core")
