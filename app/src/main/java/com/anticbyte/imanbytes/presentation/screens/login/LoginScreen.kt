@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onLogin: () -> Unit = {}) {
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val buttonModifier = Modifier.fillMaxWidth()

    //draw the ui
    Scaffold(topBar = {
        AppTopBar(
            title = "Login"
        )
    }) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AppRegularTextField(label = "Email", state = emailState)
                AppSecureTextField(
                    state = passwordState, label = "Password",
                    isPasswordVisible = isPasswordVisible,
                    onPassWordVisibilityChange = { isPasswordVisible = !isPasswordVisible }
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = onLogin,
                    modifier = buttonModifier,
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text("Login")
                }
                TextButton(
                    onClick = { TODO() },
                    modifier = buttonModifier,
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text("Forget Password")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Don't have an account?")
                TextButton(
                    onClick = { TODO() },
                    modifier = buttonModifier,
                    shapes = ButtonDefaults.shapes()
                ) {
                    Text(text = "Signup")
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    ImanBytesTheme {
        LoginScreen()
    }
}