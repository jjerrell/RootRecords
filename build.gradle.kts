plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinCompose).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.roomDb).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.spotless)

    // TODO: Deprecate
    alias(libs.plugins.sqlDelight).apply(false)
}

spotless {
    kotlin {
        ktlint("0.50.0")
            .userData(
                mapOf(
                    "trailing-comma" to "disabled"
                )
            )
        ratchetFrom("origin/main")
        target("src/*/kotlin/**/*.kt")
        targetExclude("${layout.buildDirectory}/**/*.kt")
    }
}
