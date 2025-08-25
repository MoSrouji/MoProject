plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.8.22"
    id("com.google.dagger.hilt.android") version "2.51.1"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"
    kotlin("kapt")
    alias(libs.plugins.google.gms.google.services)
    // alias(libs.plugins.google.gms.google.services)

}


android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kapt {
        correctErrorTypes = true

    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.kotlinx.serialization.json)

    // Hilt Dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


    // Navigation Dependencies
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    // Hilt Navigation
    implementation(libs.androidx.hilt.navigation.compose)

    // Coil Dependency
    implementation(libs.coil.compose)


    // Retrofit Dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)


    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.material.icons.extended)
    implementation(libs.androidx.ui.util)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.accompanist.navigation.animation)
    implementation(libs.androidx.animation.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}


