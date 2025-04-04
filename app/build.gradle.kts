plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.smarthomecontrol"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.smarthomecontrol"
        minSdk = 29
        targetSdk = 34
        versionCode = 28
        versionName = "0.2.8"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Library dependencies:
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.material3:material3:1.1.2")
//    implementation("androidx.compose.material:material-icons-extended:1.6.0") // Or the latest version
//    implementation("androidx.navigation:navigation-compose:2.7.7") // Or the latest stable version
    // Home API SDK dependency:
    implementation(libs.play.services.home)
//    implementation("com.google.android.gms:play-services-location:21.3.0")
//    implementation("com.google.android.gms:play-services-home:16.0.0")
//    implementation("com.google.apis:google-api-services-homegraph:v1-rev20230720-2.0.0")
//    def camerax_version = "1.3.1"
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("androidx.camera:camera-extensions:1.3.1")
    implementation("com.google.mlkit:face-detection:16.1.5")

}