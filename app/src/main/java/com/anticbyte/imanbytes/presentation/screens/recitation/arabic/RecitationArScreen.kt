package com.anticbyte.imanbytes.presentation.screens.recitation.arabic

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.recitation.PlayerState
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingBar
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingButton
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationFloatingToolbar
import com.anticbyte.imanbytes.presentation.screens.recitation.component.RecitationListItem
import com.anticbyte.imanbytes.presentation.screens.recitation.component.paddingWithoutTop
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationArRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationArViewModel,
    onNavigateBack: () -> Unit
) {
    val screenState by viewModel.recitationUiState.collectAsStateWithLifecycle()
    RecitationArScreen(
        modifier = modifier,
        screenState = screenState,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RecitationArScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    screenState: RecitationArScreenState = RecitationArScreenState()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    var showScrollToTop by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Arabic Recitation",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = innerPadding
            ) {
                if (screenState.isLoading) loadingItem()
                else {
                    recitationItemDescription(descriptionRes = R.string.recitation_description_arabic)
                    recitationItemsAr(surahList = screenState.surahList)
                }
            }
            RecitationFloatingButton(
                modifier = Modifier,
                innerPadding = innerPadding,
                listState = listState,
                showScrollToTop = showScrollToTop,
            )
            RecitationFloatingBar(modifier = Modifier.padding(innerPadding))
        }
    }
}

//todo implement player state and onclick properly
fun LazyListScope.recitationItemsAr(surahList: List<Surah>) {
    items(surahList) { surah ->
        RecitationListItem(
            modifier = Modifier,
            surah = surah,
            playerState = PlayerState.PlayerPaused,
            playSurah = {}
        )
    }
}

fun LazyListScope.recitationItemDescription(@StringRes descriptionRes: Int) {
    item {
        Text(
            text = stringResource(descriptionRes),
            textAlign = TextAlign.Justify,
            modifier = Modifier.paddingWithoutTop(16.dp)
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=pixel_5,navigation=buttons")
@Composable
private fun DefPrev() {
    ImanBytesTheme {
        RecitationArScreen()
    }
}