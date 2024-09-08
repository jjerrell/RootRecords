plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "app.jjerrell.root.records.android.feature.settings"
    compileSdk = 34

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures { compose = true }

    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(libs.compose.ui.core)
    implementation(libs.androidx.material3.compose)

    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation(projects.shared)
    api(project(":androidApp:ui:core"))
    implementation(project(":androidApp:ui:navigation"))

    // Transitive dependencies per build-health plugin
    api(libs.compose.animation)
    api(libs.compose.foundation.layout)
    api(libs.compose.foundation.core)
    api(libs.compose.runtime)
    api(libs.androidx.navigation.common)
    api(libs.androidx.navigation.runtime)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.text)
    implementation(libs.compose.ui.unit)
    implementation(libs.androidx.viewmodel.lifecycle)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.core)
    androidTestImplementation(libs.androidx.test.monitor)
    androidTestImplementation(libs.junit.core)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.ext)
}
