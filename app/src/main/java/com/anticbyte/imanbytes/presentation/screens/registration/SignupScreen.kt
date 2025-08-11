package com.anticbyte.imanbytes.presentation.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.presentation.component.AppRegularTextField
import com.anticbyte.imanbytes.presentation.component.AppSecureTextField
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    val fullNameState = rememberTextFieldState()
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val confirmPasswordState = rememberTextFieldState()
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val buttonModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)

    //draw the ui
    Scaffold(topBar = {
        AppTopBar(title = "Signup", subtitle = "Enter your credentials")
    }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AppRegularTextField(label = "Full Name", state = fullNameState)
                AppRegularTextField(label = "Email", state = emailState)
                AppSecureTextField(
                    state = passwordState, label = "Password",
                    isPasswordVisible = isPasswordVisible,
                    onPassWordVisibilityChange = { isPasswordVisible = !isPasswordVisible }
                )
                AppSecureTextField(
                    state = confirmPasswordState, label = "Confirm Password",
                    isPasswordVisible = isPasswordVisible,
                    onPassWordVisibilityChange = { isPasswordVisible = !isPasswordVisible }
                )
            }
            Button(
                onClick = { TODO() },
                modifier = buttonModifier.padding(top = 24.dp),
            ) {
                Text(text = "Signup")
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Already have an account?")
                TextButton(
                    onClick = { TODO() },
                    modifier = buttonModifier,
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignupScreenPreview() {
    ImanBytesTheme {
        SignupScreen()

    }
}