@file:OptIn(ExperimentalMaterial3Api::class)

package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R

@Composable
fun AppRegularTextField(
    modifier: Modifier = Modifier,
    label: String,
    state: TextFieldState
) {
    TextField(
        state = state,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        colors = textFieldColors(
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent,
            focusedIndicatorColor = colorScheme.primary
        ),
        label = { Text(text = label) }
    )
}

@Composable
fun AppSecureTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    label: String,
    isPasswordVisible: Boolean,
    onPassWordVisibilityChange: () -> Unit
) {
    val trailingIcon =
        ImageVector.vectorResource(
            id = if (isPasswordVisible) R.drawable.ic_check else R.drawable.ic_check
        )

    SecureTextField(
        state = state,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = textFieldColors(
            focusedContainerColor = Transparent,
            unfocusedContainerColor = Transparent,
            focusedIndicatorColor = colorScheme.primary
        ),
        label = { Text(text = label) },
        trailingIcon = {
            IconButton(onClick = onPassWordVisibilityChange) {
                Icon(trailingIcon, "")
            }
        },
        textObfuscationMode = if (isPasswordVisible) {
            TextObfuscationMode.Visible
        } else TextObfuscationMode.RevealLastTyped
    )
}