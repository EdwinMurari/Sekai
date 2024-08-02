plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.edwin.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(project(":network:anilist"))
    implementation(project(":network:jikan"))
    implementation(project(":network:kitsu"))
    implementation(project(":network:extensions"))
    implementation(project(":network:userpref"))
    api(project(":network:extensions:aniyomi"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    api(libs.apollo.runtime)
    api(libs.protobuf.kotlin.lite)

    // Mockk framework
    testImplementation(libs.mockk)

    // Paging
    implementation(libs.androidx.paging.runtime)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}