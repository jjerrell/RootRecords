plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

android {
    namespace = "app.jjerrell.root.records.android.feature.category"
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
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.material3.compose)

    implementation(libs.androidx.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    api(projects.shared)
    api(project(":androidApp:ui:core"))
    implementation(project(":androidApp:ui:theme"))
    implementation(project(":androidApp:ui:navigation"))

    // Transitive dependencies per build-health plugin
    api("androidx.compose.foundation:foundation-layout:1.7.0")
    api(libs.compose.runtime)
    api(libs.compose.ui.graphics)
    api(libs.androidx.navigation.common)
    api("androidx.navigation:navigation-runtime:2.8.0")
    implementation("androidx.compose.animation:animation:1.7.0")
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.text)
    implementation(libs.compose.ui.unit)
    implementation(libs.androidx.viewmodel.lifecycle)
    implementation(libs.kotlinx.coroutines.core)
    androidTestImplementation(libs.androidx.test.monitor)
    androidTestImplementation(libs.junit.core)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.ext)
}
