package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppSnackBar(snackBarData: SnackbarData, isError: Boolean) {
    val snackBarVisual = (snackBarData.visuals as SnackBarVisualsWithError)
    Snackbar(
        modifier = Modifier.padding(16.dp),
        action = {
            TextButton(onClick = {
                if (isError) snackBarData.performAction()
                else snackBarData.dismiss()
            }) {
                val labelColor = if (isError)
                    colorScheme.error
                else
                    colorScheme.onPrimaryContainer
                Text(
                    snackBarVisual.actionLabel,
                    color = labelColor
                )
            }
        }
    ) {
        Text(text = snackBarVisual.message)
    }
}

class SnackBarVisualsWithError(
    override val message: String,
    val isError: Boolean
) : SnackbarVisuals {
    override val actionLabel: String
        get() = if (isError) "Retry" else "Okay"

    override val withDismissAction: Boolean
        get() = false

    override val duration: SnackbarDuration
        get() = SnackbarDuration.Indefinite
}