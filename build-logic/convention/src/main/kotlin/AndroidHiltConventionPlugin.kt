import module.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Convention plugin for Dagger Hilt.
 * It simplifies the application of Hilt and KSP plugins along with their dependencies.
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }

            dependencies {
                add("implementation", libs.findLibrary("dagger-hilt-android").get())
                add("ksp", libs.findLibrary("dagger-hilt-compiler").get())
                add("implementation", libs.findLibrary("dagger-hilt-navigation-compose").get())
            }
        }
    }
}
