package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppOutlineButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonLabel: String
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = ButtonDefaults.MediumContentPadding,
        colors = ButtonDefaults.outlinedButtonColors(),
        shapes = ButtonDefaults.shapes()
    ) {
        Text(text = buttonLabel)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    buttonLabel: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentPadding = ButtonDefaults.MediumContentPadding,
        shapes = ButtonDefaults.shapes()
    ) {
        Text(text = buttonLabel)
    }
}