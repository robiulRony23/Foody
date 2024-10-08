plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-android")
    id("kotlin-kapt")
//    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.foody"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foody"
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

    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//repositories {
////    mavenCentral()
//    maven {
//        url = uri("https://jitpack.io")
//    }
//}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Coordinator Layout
//    implementation("androidx.coordinatorlayout:coordinatorlayout:1.1.0")
//
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.0")

    // Room components
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")
//
//    // Data binding
//    kapt "com.android.databinding:compiler:3.2.0-alpha10"
//    kapt "androidx.databinding:databinding-common:4.0.2"
//
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0-alpha01")

    // Recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.2")
//
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Dagger - Hilt
//    implementation("com.google.dagger:hilt-android:2.28-alpha")
//    kapt("com.google.dagger:hilt-android-compiler:2.28-alpha")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

//
//    // Coroutines
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9'
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.5")

    // Image Loading library Coil
    implementation("io.coil-kt:coil:0.13.0")
//
    // Gson
    implementation("com.google.code.gson:gson:2.9.0")

    // Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
//    implementation("com.todkars:shimmer-recyclerview:0.4.1")
    implementation("com.github.sharish:ShimmerRecyclerView:v1.3")

//    // Jsoup
//    implementation 'org.jsoup:jsoup:1.13.1'
}