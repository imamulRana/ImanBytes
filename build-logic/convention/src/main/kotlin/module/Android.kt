package module

import com.android.build.api.dsl.CommonExtension

internal fun configureAndroid(commonExtension: CommonExtension) {
    commonExtension.apply {

        buildFeatures.buildConfig = true
    }
}