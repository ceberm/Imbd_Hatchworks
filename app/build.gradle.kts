plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.cbermudezg.imbd_hatchworks"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cbermudezg.imbd_hatchworks"
        minSdk = 23
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // JSON Converter
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")

    // Logger Library
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
}