plugins {
    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.comera.gallery"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.comera.gallery"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    dataBinding {
        enable = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    testOptions {
        // Used for Unit testing Android dependent elements in /test folder
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.8.10")

    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    //compose navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    implementation("io.coil-kt:coil-compose:2.3.0")


    // Dagger hilt
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("androidx.test:core-ktx:1.5.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    implementation("com.google.android.material:material:1.11.0")

    //Compose
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //testing dependencies
    //Testing
    testImplementation("org.mockito:mockito-core:4.5.0")
    testImplementation("com.google.truth:truth:1.1")
    testImplementation("io.mockk:mockk:1.12.0")
    androidTestImplementation("io.mockk:mockk:1.12.0")

    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.28-alpha")
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.28-alpha")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.48.1")

    // Mockito
    testImplementation("org.mockito:mockito-inline:3.11.2")
    androidTestImplementation("org.mockito:mockito-android:4.5.0")

    // For LiveData testing
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    //compose
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
kapt {
    correctErrorTypes = true
}