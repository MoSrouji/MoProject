// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android)  apply false
    alias(libs.plugins.kotlin.compose) apply false

    kotlin("plugin.serialization") version "1.8.22" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false

    kotlin("kapt") version "1.8.21" apply false



}
