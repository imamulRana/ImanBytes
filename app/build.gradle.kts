import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android) // dagger hilt android plugin
    alias(libs.plugins.kotlin.ksp) // kotlin ksp plugin
}

android {
    namespace = "com.anticbyte.imanbytes"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.anticbyte.imanbytes"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField(type = "Boolean", name = "LOGGING", value = "true")
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://jsonplaceholder.typicode.com/\""
            )
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(type = "Boolean", name = "LOGGING", value = "false")
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://jsonplaceholder.typicode.com/\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin.compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
            "-Xannotation-default-target=param-property",
            "-XXLanguage:+ExplicitBackingFields"
        )
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //splash
    implementation(libs.androidx.core.splashscreen)

    //navigation compose
    implementation(libs.androidx.navigation.compose)

    //ktor
    implementation(libs.bundles.ktor)

    //dagger hilt android
    implementation(libs.dagger.hilt.android)
    implementation(libs.dagger.hilt.navigation.compose)
    ksp(libs.dagger.hilt.compiler)

    //pref datastore
    implementation(libs.pref.datastore)

    //play in app updates
    implementation(libs.play.ktx)
    implementation(libs.play.app.updates)

    //google trusted time
    implementation(libs.google.trusted.time)

    //androidx media3
    implementation(libs.bundles.media3)

    //parse string from html jsoup
    implementation(libs.jsoup)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}