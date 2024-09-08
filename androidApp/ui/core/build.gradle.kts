plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "app.jjerrell.root.records.android.ui.core"
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
    implementation(libs.androidx.material3.compose)

    api(libs.androidx.viewmodel.lifecycle)
    implementation(libs.androidx.viewmodel.compose)

    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.datastore.preferences.android)

    api(libs.compose.runtime)
    api(libs.compose.ui.graphics)
    implementation(libs.compose.animation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.foundation.core)
    implementation(libs.compose.ui.core)
    implementation(libs.compose.ui.text)
    implementation(libs.compose.ui.unit)

    implementation(libs.kotlinx.coroutines.core)

    api(projects.shared)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit.core)

    androidTestImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.androidx.test.monitor)
}
