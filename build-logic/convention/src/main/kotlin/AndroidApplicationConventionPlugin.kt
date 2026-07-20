import com.android.build.api.dsl.ApplicationExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import module.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin for Android Applications.
 * It encapsulates the configuration for signing, build types, and common SDK settings.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                // Apply common Android configuration (SDKs, Kotlin, etc.)
                configureAndroid(this)

                defaultConfig {
                    targetSdk = 36
                    applicationId = "com.anticbyte.imanbytes"
                    versionCode = 11
                    versionName = "1.3.0"
                }

                signingConfigs {
                    // We define the release signing config.
                    // Note: Properties should ideally be in gradle.properties.
                    create("release") {
                        storeFile = file("anticbyte.jks")
                        storePassword = project.findProperty("key_store_pass") as? String ?: ""
                        keyAlias = project.findProperty("key_alias") as? String ?: ""
                        keyPassword = project.findProperty("key_alias_pass") as? String ?: ""
                    }
                }

                buildTypes {
                    debug {
                        isMinifyEnabled = false
                        isDebuggable = true
                        applicationIdSuffix = ".debug"
                        versionNameSuffix = "-debug"
                        
                        // Example of dynamic BuildConfig fields.
                        buildConfigField("Long", "REMOTE_CONFIG_INTERVAL", "0L")
                        buildConfigField(
                            "String",
                            "AUDIO_BASE_URL",
                            "\"https://cdn.islamic.network/quran/audio-surah/128/%s/%s.mp3\""
                        )
                    }
                    
                    release {
                        // Hook up the signing config defined above.
                        signingConfig = signingConfigs.getByName("release")
                        isMinifyEnabled = true
                        isDebuggable = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                        
                        // Firebase Crashlytics mapping upload.
                        // We use withPlugin to ensure the extension is available.
                        pluginManager.withPlugin("com.google.firebase.crashlytics") {
                            configure<CrashlyticsExtension> {
                                mappingFileUploadEnabled = true
                            }
                        }
                        
                        buildConfigField("Long", "REMOTE_CONFIG_INTERVAL", "3600L")
                        buildConfigField(
                            "String",
                            "AUDIO_BASE_URL",
                            "\"https://cdn.islamic.network/quran/audio-surah/128/%s/%s.mp3\""
                        )
                    }

                    // A staging environment that inherits from release but with minor changes.
                    create("staging") {
                        initWith(getByName("release"))
                        isMinifyEnabled = false
                        versionNameSuffix = "-staging"
                        buildConfigField("Long", "REMOTE_CONFIG_INTERVAL", "60L")
                    }
                }
            }
        }
    }
}
