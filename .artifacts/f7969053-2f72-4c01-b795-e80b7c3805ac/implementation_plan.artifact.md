# Implementation Plan - Refactoring to Convention Plugins

This plan details the refactoring of the `:app` module's build configuration into a set of reusable convention plugins within the `build-logic` module. This approach promotes modularity, reduces boilerplate, and provides a clear structure for junior developers to learn from.

## User Review Required

> [!IMPORTANT]
> The `buildTypes` and `signingConfigs` contain project-specific properties like `key_store_pass`, `key_alias`, and `key_alias_pass`. These are assumed to be defined in your `gradle.properties` or environment variables.

## Proposed Changes

### Build-Logic Module

#### [MODIFY] [Android.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/module/Android.kt)
Update this file to handle the core Android configuration (SDK versions, Java 21 toolchain, Kotlin options). This will be a common utility used by other plugins.

#### [NEW] [AndroidApplicationConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt)
A new plugin for Android Applications (`com.android.application`). It will:
- Apply common Android configuration.
- Set `defaultConfig` (applicationId, versioning).
- Configure `signingConfigs` and `buildTypes` (debug, release, staging).
- Add the `AUDIO_BASE_URL` and `REMOTE_CONFIG_INTERVAL` build config fields.

#### [NEW] [AndroidComposeConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidComposeConventionPlugin.kt)
A new plugin for Jetpack Compose. It will:
- Configure `buildFeatures.compose`.
- Set the Compose compiler options.
- Add standard Compose dependencies (BOM, UI, Material3, etc.).

#### [NEW] [AndroidHiltConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidHiltConventionPlugin.kt)
A new plugin for Hilt. It will:
- Apply the Hilt and KSP plugins.
- Add Hilt dependencies.

#### [NEW] [AndroidFirebaseConventionPlugin.kt](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/convention/src/main/kotlin/AndroidFirebaseConventionPlugin.kt)
A new plugin for Firebase. It will:
- Apply Google Services and Crashlytics plugins.
- Add Firebase dependencies.

#### [MODIFY] [build.gradle.kts](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/build-logic/build.gradle.kts)
Register the new plugins in the `gradlePlugin` block so they can be discovered by the `:app` module.

### App Module

#### [MODIFY] [build.gradle.kts](file:///C:/Users/539174/AndroidStudioProjects/ImanBytes/app/build.gradle.kts)
- Replace the long list of standard plugins with the new convention plugins.
- Remove redundant configuration blocks (SDKs, build types, common dependencies) as they are now handled by the plugins.
- Keep only `:app`-specific dependencies.

## Teaching Material Strategy
- **Detailed KDoc**: Each plugin and utility function will have comments explaining *why* certain configurations are used.
- **Clear Naming**: Using descriptive names for plugins and helper functions.
- **Separation of Concerns**: Demonstrating how to group related build logic (e.g., separating Compose from Firebase).

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify the build still succeeds.
- Run `./gradlew help` to ensure the plugins are registered correctly.

### Manual Verification
- Check the generated `BuildConfig` in the `debug` and `release` variants to ensure the fields are correctly populated.
- Verify that `staging` variant exists and is configured as expected.
