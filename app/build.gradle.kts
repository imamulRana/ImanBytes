plugins {
    id("imanbytes.android.application")
    id("imanbytes.android.compose")
    id("imanbytes.android.hilt")
    id("imanbytes.android.firebase")
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.anticbyte.imanbytes"
    
    // Most of the configuration (SDKs, buildTypes, signingConfigs) 
    // is now handled by imanbytes.android.application plugin.
    
    // We can still override or add specific configuration here if needed.
    defaultConfig {
        applicationId = "com.anticbyte.imanbytes"
    }
}

dependencies {
    // AndroidX Core & Lifecycle (Module specific)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Ktor (Network)
    implementation(libs.bundles.ktor)

    // DataStore (Persistence)
    implementation(libs.pref.datastore)

    // Play In-App Updates
    implementation(libs.play.ktx)
    implementation(libs.play.app.updates)

    // Google Trusted Time
    implementation(libs.google.trusted.time)

    // Media3 (Audio/Video)
    implementation(libs.bundles.media3)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
