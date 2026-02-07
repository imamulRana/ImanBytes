package com.anticbyte.imanbytes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.anticbyte.imanbytes.navigation.HomeBaseRoute
import com.anticbyte.imanbytes.navigation.NavigationScaffold
import com.anticbyte.imanbytes.navigation.Onboarding
import com.anticbyte.imanbytes.presentation.screens.onboard.OnboardingViewModel
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: OnboardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var startDestination: Any? by mutableStateOf(null)

        // 2. Keep the splash screen visible until the route is resolved (i.e., not null).
        splashScreen.setKeepOnScreenCondition { startDestination == null }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Collect the flow's first value.
                // We don't need collectLatest as we just need the final value.
                viewModel.isOnBoarded.collect { isOnboarded ->
                    // Set the resolved route, which will satisfy the splash screen condition.
                    startDestination = if (isOnboarded) HomeBaseRoute else Onboarding
                    // IMPORTANT: We only need to resolve the route once.
                    // To stop collecting/re-setting the state, use .first() or break/cancel.
                    // If viewModel.isOnBoarded is a StateFlow, it will always emit.
                }
            }
        }
        setContent {
            val navController = rememberNavController()
            ImanBytesTheme(dynamicColor = false) {
                startDestination?.let { destination ->
                    NavigationScaffold(
                        navController = navController,
                        startDestination = destination
                    )
                }
            }
        }
    }
}
