plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.roomDb)
}

kotlin {
    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            // Adds generated files to sources so Room's `instantiateImpl()` can be located
            kotlin.srcDir("build/generated/ksp/metadata")
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.room.runtime)
                implementation(libs.room.sqlite.bundled)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.sql.delight.android)
        }
        iosMain.dependencies {
            implementation(libs.sql.delight.native)
        }
    }
}

android {
    namespace = "dev.jjerrell.root.records"
    compileSdk = 34
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata" ) {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

// TODO: Deprecate
sqldelight {
    databases {
        create("RootRecordsDb") {
            packageName.set("dev.jjerrell.root.records.db")
        }
    }
}