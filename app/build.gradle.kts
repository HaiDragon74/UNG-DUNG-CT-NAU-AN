plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") //them
}

android {
    namespace = "com.example.btungdungmonan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.btungdungmonan"
        minSdk = 24
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
    buildFeatures {
        viewBinding=true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // them navgation compoment
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")// them
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")// them
    // them retrofil
    implementation("com.google.android.material:material:1.11.0") // them
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")// them
    //Glide
    implementation("com.github.bumptech.glide:glide:4.12.0") // them
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0") // them
    //videomodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    //AppBarLayout
    implementation("com.google.android.material:material:1.11.0") //them
    //room database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    //Snackbar
    implementation ("com.google.android.material:material:1.5.0")



}