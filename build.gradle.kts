plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinSerialization).apply(false)
    alias(libs.plugins.kotlinCompose).apply(false)
    alias(libs.plugins.roomDb).apply(false)
    alias(libs.plugins.ksp).apply(false)
    id("com.autonomousapps.dependency-analysis").version("2.0.1")
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = "com.autonomousapps.dependency-analysis")
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt", "build-support/**/*.kt")
            ktfmt()
                .kotlinlangStyle()
            licenseHeaderFile(rootProject.file("build-support/copyright.kt"))
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            targetExclude("${layout.buildDirectory}/**/*.gradle.kts", "build-support/**/*.gradle.kts")
            ktfmt()
                .kotlinlangStyle()
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml", "build-support/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootProject.file("build-support/copyright.xml"), "(<[^!?])")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}