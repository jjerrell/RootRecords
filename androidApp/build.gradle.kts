plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "app.jjerrell.root.records.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "app.jjerrell.root.records.android"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures { compose = true }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(libs.androidx.activity.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3.compose)
    implementation(libs.androidx.navigation.common)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime)

    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.datastore.preferences.android)

    // Transitive dependencies per build-health plugin
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.foundation.core)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui.core)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.text)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.viewmodel)

    implementation(project(":androidApp:ui:theme"))
    implementation(project(":androidApp:ui:navigation"))

    implementation(project(":androidApp:feature-category"))
    implementation(project(":androidApp:feature-settings"))
    implementation(project(":androidApp:feature-task"))

    debugImplementation(libs.compose.ui.tooling)
}
