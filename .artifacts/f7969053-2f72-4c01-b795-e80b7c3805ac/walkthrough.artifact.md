# Walkthrough - Build Logic Refactoring

I have successfully refactored the build configuration of the `Iman Bytes` project to use custom convention plugins. This migration reduces boilerplate in the `:app` module and provides a scalable, modular structure that is easy for junior developers to follow.

## Changes Made

### 1. Build Logic Infrastructure
- **[NEW] [build-logic/convention/build.gradle.kts](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/build.gradle.kts)**: The engine of our convention plugins. It registers the new plugins and manages their dependencies.
- **[NEW] [module/Extensions.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/module/Extensions.kt)**: A utility to allow plugins to easily access the `libs` version catalog.

### 2. Core Android Configuration
- **[MODIFY] [module/Android.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/module/Android.kt)**: Centralized SDK versions (compileSdk 36, minSdk 24), Java 21 toolchain, and Kotlin compiler options (opt-ins and freeCompilerArgs).

### 3. Modular Convention Plugins
- **[NEW] [AndroidApplicationConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt)**:
    - Applies `com.android.application` and `org.jetbrains.kotlin.android`.
    - Configures `signingConfigs` (release).
    - Configures `buildTypes` (`debug`, `release`, `staging`) with specific `buildConfig` fields like `AUDIO_BASE_URL`.
- **[NEW] [AndroidComposeConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidComposeConventionPlugin.kt)**: Enables Compose and adds standard UI dependencies from the BOM.
- **[NEW] [AndroidHiltConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidHiltConventionPlugin.kt)**: Simplifies Hilt and KSP setup.
- **[NEW] [AndroidFirebaseConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidFirebaseConventionPlugin.kt)**: Handles Google Services, Crashlytics, and core Firebase dependencies.

### 4. Simplified App Module
- **[MODIFY] [app/build.gradle.kts](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/app/build.gradle.kts)**:
    - Removed ~100 lines of boilerplate.
    - Now uses a clean, declarative plugin block:
    ```kotlin
    plugins {
        id("imanbytes.android.application")
        id("imanbytes.android.compose")
        id("imanbytes.android.hilt")
        id("imanbytes.android.firebase")
        alias(libs.plugins.jetbrains.kotlin.serialization)
    }
    ```

## Verification Results

### Build Success
- **Gradle Sync**: Completed successfully.
- **Build Types**: Verified that `staging` variant correctly inherits from `release`.
- **BuildConfig**: Verified that dynamic fields (`AUDIO_BASE_URL`, `REMOTE_CONFIG_INTERVAL`) are correctly distributed across build types via the Application plugin.

## Notes for Future Development
> [!TIP]
> To create a new module (e.g., a library), you can create an `AndroidLibraryConventionPlugin` and call `configureAndroid(this)` to ensure it stays consistent with the app's SDK and Kotlin settings.

> [!IMPORTANT]
> Some dependencies in `build-logic/convention/build.gradle.kts` are currently using string literals to avoid sync issues with the version catalog during the migration. It is recommended to update these to use the `libs` catalog once the IDE has fully indexed the new entries.
