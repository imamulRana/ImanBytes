
package com.anticbyte.imanbytes.presentation.quran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.AudioPlayerInfoItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TafsirScreen(
    modifier: Modifier = Modifier,
) {
    val horizontalPadding = Modifier.padding(horizontal = 16.dp)
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(title = "Quran", isBackVisible = true)
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
            ,
        ) {
            Text(
                modifier = horizontalPadding,
                text = "Surah Az-Zumar | 39",
                style = typography.headlineLarge
            )
            AudioPlayerInfoItem(
                infoTitle = "Hello", infoDescription = "Description"
            )
            Column(
                modifier = horizontalPadding, verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "The Surah derives its name from verse 71 and 73 in which the word zumar has occurred.",
                    style = typography.titleLarge
                )
                Text(
                    text = "In verse 10 (wa ardullah-i-wasi atun : and Allah's earth is vast) there is abundant evidence that this Surah was sent down before the migration to Habash. Some traditions provide the explanation that this verse was sent down in respect of Hadrat Ja'far bin Abi Talib and his companions when they made up their mind to emigrate to Habash.(Ruh al-Maani, vol. XXII, p. 226).",
                    style = typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = horizontalPadding,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Verse Tafsir",
                    style = typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.test_verse_tafsir),
                    style = typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerseSummeryScreenPrev() {
    ImanBytesTheme(darkTheme = true) {
        TafsirScreen()
    }
}

@Composable
fun ProvideNavigationIcon(
    navigationIcon: @Composable RowScope.() -> Unit = {}
) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { entry ->
        val viewModel: QuranViewModel = hiltViewModel(viewModelStoreOwner = entry)
        viewModel.navigationIcon = navigationIcon
    }
}