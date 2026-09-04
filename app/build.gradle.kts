import org.gradle.kotlin.dsl.test
import java.util.Properties
import java.io.FileInputStream
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
}
/*
val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("keystore.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

 */
android {
    namespace = "com.example.vaani"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.vaani"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging{
        jniLibs{
            useLegacyPackaging = true
        }
    }
    /*
        signingConfigs{
            create("release") {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }

     */

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            //proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //signingConfig= signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {

    // -------------------------
    // Android Core
    // -------------------------
    implementation("androidx.core:core-ktx:1.15.0")

    // -------------------------
    // Activity
    // -------------------------
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.activity:activity-compose:1.10.1")

    // -------------------------
    // Lifecycle
    // -------------------------
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")


    implementation("com.google.mlkit:translate:17.0.3")

    val roomVersion = "2.6.1"

    // Room Runtime & Kotlin Extensions (Coroutines support)
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // KSP annotation processor (generates Room implementation code)
    ksp("androidx.room:room-compiler:$roomVersion")

    // -------------------------
    // Jetpack Compose
    // -------------------------
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // -------------------------
    // Navigation
    // -------------------------
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // -------------------------
    // DataStore
    // -------------------------
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // -------------------------
    // Vosk Offline Speech Recognition
    // -------------------------
    implementation("com.alphacephei:vosk-android:0.3.75")

    // -------------------------
    // Google Gemini / GenAI
    // -------------------------

    // -------------------------
    // Retrofit
    // -------------------------
    //implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // -------------------------
    // Unit Tests
    // -------------------------
    testImplementation("junit:junit:4.13.2")

    // For video preview/playback, use AndroidX Media3.
    implementation("androidx.media3:media3-exoplayer:1.8.0")
    implementation("androidx.media3:media3-ui:1.8.0")

    implementation("com.google.mlkit:face-detection:16.1.7")

    // -------------------------
    // Android Tests
    // -------------------------
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    androidTestImplementation(platform("androidx.compose:compose-bom:2024.12.01"))

    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // -------------------------
    // Debug
    // -------------------------
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("com.google.mlkit:translate:17.0.3")
}