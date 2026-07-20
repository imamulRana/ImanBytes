import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.hilt.gradle.plugin)
    // We use strings here to avoid sync issues with new catalog entries in build-logic
    compileOnly(libs.google.services.v450)
    compileOnly(libs.firebase.crashlytics.gradle.v307)
    compileOnly(libs.compose.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "imanbytes.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "imanbytes.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "imanbytes.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidFirebase") {
            id = "imanbytes.android.firebase"
            implementationClass = "AndroidFirebaseConventionPlugin"
        }
    }
}
