import com.android.build.api.dsl.ApplicationExtension
import module.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Convention plugin for Jetpack Compose.
 * This plugin handles the enabling of Compose and adds the standard UI dependencies.
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Ensure the Kotlin Compose plugin is applied.
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<ApplicationExtension> {
                // Enable Compose in build features.
                buildFeatures {
                    compose = true
                }
            }

            // Add standard Compose dependencies from the version catalog.
            dependencies {
                val bom = libs.findLibrary("androidx-compose-bom").get()
                add("implementation", platform(bom))
                add("androidTestImplementation", platform(bom))

                add("implementation", libs.findLibrary("androidx-ui").get())
                add("implementation", libs.findLibrary("androidx-ui-graphics").get())
                add("implementation", libs.findLibrary("androidx-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-material3").get())
                add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("androidx-ui-test-manifest").get())
            }
        }
    }
}
