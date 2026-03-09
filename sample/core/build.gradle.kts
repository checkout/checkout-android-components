import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.checkout.android.components.sample.core"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }.run {
            buildConfigField(
                "String",
                "SANDBOX_PUBLIC_KEY",
                this["sandbox.components.public_key"].toString(),
            )
            buildConfigField(
                "String",
                "SANDBOX_SECRET_KEY",
                this["sandbox.components.secret_key"].toString(),
            )
            buildConfigField(
                "String",
                "SANDBOX_PROCESSING_CHANNEL_ID",
                // Default to empty string if the property is not set
                getOrDefault("sandbox.components.processing_channel_id", "\"\"").toString(),
            )

            buildConfigField(
                "String",
                "PRODUCTION_PUBLIC_KEY",
                this["production.components.public_key"].toString(),
            )
            buildConfigField(
                "String",
                "PRODUCTION_SECRET_KEY",
                this["production.components.secret_key"].toString(),
            )
        }
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

    buildFeatures {
        buildConfig = true
    }
    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
        }
    }
}

dependencies {
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    implementation(libs.checkout.android.components.interfaces)


    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
}
