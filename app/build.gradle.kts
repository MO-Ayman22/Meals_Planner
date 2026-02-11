plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.mealsplanner"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.mealsplanner"
        minSdk = 24
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.cardview)
    implementation(libs.firebase.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(platform(libs.firebase.bom))
    implementation(libs.fragment)
    implementation(libs.firebase.firestore)
    implementation(libs.core.splashscreen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.navigation.fragment.v297)
    implementation(libs.navigation.ui)
    implementation(libs.lottie)
    implementation(libs.circleimageview)
    implementation(libs.rxjava3.retrofit.adapter)
    implementation(libs.room.rxjava3)
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.glide)
    implementation(libs.core)
}