plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "app.jjerrell.root.records.android.ui.theme"
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

    // Transitive dependencies per build-health plugin
    api(libs.compose.runtime)
    implementation(libs.compose.foundation.core)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.text)
    implementation(libs.compose.ui.unit)
    androidTestImplementation(libs.androidx.test.monitor)
    androidTestImplementation(libs.junit.core)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.ext)
}
