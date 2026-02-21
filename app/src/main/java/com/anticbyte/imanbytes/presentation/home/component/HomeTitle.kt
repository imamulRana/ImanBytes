package com.anticbyte.imanbytes.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun HomeTitle(
    modifier: Modifier = Modifier,
    label: String,
    action: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label, style = typography.titleSmall,
            color = colorScheme.onSurfaceVariant
        )
        if (action != null) {
            TextButton(
                onClick = action,
                modifier = Modifier
                    .height(ButtonDefaults.ExtraSmallContainerHeight),
                contentPadding = ButtonDefaults.ExtraSmallContentPadding,
                shapes = ButtonDefaults.shapes()
            ) {
                Text("See all")
            }
        }
    }
}

@Preview
@Composable
private fun HomeTilePrev() {
    ImanBytesTheme {
        HomeTitle(
            label = "Prayer times",
            action = {

            })
    }
}