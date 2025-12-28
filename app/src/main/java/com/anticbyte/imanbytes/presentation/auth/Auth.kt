package com.anticbyte.imanbytes.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun AuthScreenRoute(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Get the Activity context explicitly
    val activity = context as? androidx.activity.ComponentActivity
        ?: throw IllegalStateException("Context is not an Activity")

    val credentialManager = remember {
        CredentialManager.create(context)
    }
    AuthScreen(
        state = state,
        onSignInClick = {
            viewModel.launchCredentials(credentialManager, activity)
        }
    )
}

@Composable
fun AuthScreen(
    state: AuthState,
    onSignInClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome ${state.userName}")
        Text(text = "Email: ${state.userEmail}")
        Text(text = "Id: ${state.userId}")
        Button(onClick = onSignInClick) {
            Text("SignIn")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ImanBytesTheme {
        AuthScreen(
            state = AuthState()
        )
    }
}