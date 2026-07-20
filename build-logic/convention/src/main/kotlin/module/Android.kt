package module

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Common Android configuration that should be applied to all Android modules (App and Library).
 * This function serves as teaching material for Junior Developers to understand how to share
 * build logic across different modules.
 */
internal fun Project.configureAndroid(
    commonExtension: ApplicationExtension,
) {
    commonExtension.apply {
        // We target SDK 36 as per the app's current configuration.
        compileSdk = 36

        defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        // We use Java 21 for both source and target compatibility.
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        // Enable BuildConfig generation as it's used for constants like AUDIO_BASE_URL.
        buildFeatures {
            buildConfig = true
        }
    }

    // Configure Kotlin-specific settings for the Android module.
    configureKotlin()
}

/**
 * Configures Kotlin options, including JVM target and experimental features.
 */
private fun Project.configureKotlin() {
    // Use the Kotlin extension to set compiler options.
    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)

            // Adding common opt-ins used across the project.
            optIn.addAll(
                "androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
                "androidx.compose.material3.ExperimentalMaterial3Api",
                "kotlinx.coroutines.ExperimentalCoroutinesApi",
                "androidx.media3.common.util.UnstableApi"
            )

            // Advanced compiler arguments for specific language features.
            freeCompilerArgs.addAll(
                "-Xannotation-default-target=param-property",
                "-XXLanguage:+ExplicitBackingFields"
            )
        }
    }
}
