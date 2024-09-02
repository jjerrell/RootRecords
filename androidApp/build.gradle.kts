plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
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
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.navigation)

    implementation(projects.shared)
    implementation(project(":androidApp:ui:theme"))
    implementation(project(":androidApp:ui:navigation"))

    implementation(project(":androidApp:feature-category"))
    implementation(project(":androidApp:feature-settings"))

    debugImplementation(libs.compose.ui.tooling)
}