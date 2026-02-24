import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android) // dagger hilt android plugin
    alias(libs.plugins.kotlin.ksp) // kotlin ksp plugin
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = "com.anticbyte.imanbytes"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.anticbyte.imanbytes"
        minSdk = 24
        targetSdk = 36
        versionCode = 11
        versionName = "1.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("anticbyte.jks")
            storePassword = project.property("key_store_pass") as String
            keyAlias = project.property("key_alias") as String
            keyPassword = project.property("key_alias_pass") as String
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("Long", "REMOTE_CONFIG_INTERVAL", "0L")
            buildConfigField(
                "String",
                "AUDIO_BASE_URL",
                "\"https://cdn.islamic.network/quran/audio-surah/128/%s/%s.mp3\""
            )
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            buildConfigField("Long", "REMOTE_CONFIG_INTERVAL", "3600L")
            buildConfigField(
                "String",
                "AUDIO_BASE_URL",
                "\"https://cdn.islamic.network/quran/audio-surah/128/%s/%s.mp3\""
            )
        }
        create("staging") {
            initWith(getByName("release"))
            isMinifyEnabled = false
            versionNameSuffix = "-staging"
            buildConfigField("Long", "REMOTE_CONFIG_INTERVAL", "60L")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin.compilerOptions {
        optIn.addAll(
            "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
            "androidx.compose.material3.ExperimentalMaterial3Api",
            "kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.addAll(
            "-Xannotation-default-target=param-property",
            "-XXLanguage:+ExplicitBackingFields",
            "-opt-in=androidx.media3.common.util.UnstableApi"
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
    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.config)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    //pref datastore
    implementation(libs.pref.datastore)

    //play in app updates
    implementation(libs.play.ktx)
    implementation(libs.play.app.updates)

    //google trusted time
    implementation(libs.google.trusted.time)

    //androidx media3
    implementation(libs.bundles.media3)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}