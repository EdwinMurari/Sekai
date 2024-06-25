plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.apollographql.apollo3").version("3.8.4")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.edwin.network.anilist"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "ANILIST_GRAPHQL_URL", "\"https://graphql.anilist.co/\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test)

    // Apollo GraphQL
    implementation(libs.apollo.runtime)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

apollo {
    service("service") {
        packageName.set("com.edwin.network.anilist")
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}