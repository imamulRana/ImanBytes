package com.anticbyte.imanbytes.feature.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AppBarRow
import androidx.compose.material3.AppBarRowScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.anticbyte.imanbytes.presentation.component.AppBottomBar
import com.anticbyte.imanbytes.presentation.component.AppIconButton
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun AuthScreenRoute(modifier: Modifier = Modifier) {

}

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        AuthScreen()
    }
}

@Preview(device = "spec:parent=pixel_tablet,orientation=portrait")
@Composable
private fun DefBestBuy() {
    ImanBytesTheme {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text("Welcome")
            })
        }, bottomBar = {
            AppBarRow() {

            }
        }) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {

            }
        }
    }
}