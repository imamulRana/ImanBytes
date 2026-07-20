import module.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Convention plugin for Firebase integration.
 * It applies the necessary Google and Firebase plugins and adds core dependencies.
 */
class AndroidFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")
            }

            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findLibrary("firebase-analytics").get())
                add("implementation", libs.findLibrary("firebase-crashlytics").get())
                add("implementation", libs.findLibrary("firebase-config").get())
            }
        }
    }
}
