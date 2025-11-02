package com.anticbyte.imanbytes.presentation.screens.selfRecitation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationSelfListItem
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.customInnerPadding
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.utils.loadingItem

@Composable
fun RecitationSelfRoute(
    modifier: Modifier = Modifier,
    viewModel: RecitationSelfViewModel,
    navigateBack: () -> Unit,
    navigateToSurah: (String) -> Unit
) {
    val uiState by viewModel.selfRecitationUiState.collectAsStateWithLifecycle()
    RecitationSelfScreen(
        uiState = uiState,
        onNavigateBack = navigateBack,
        onNavigateToSurah = navigateToSurah
    )
}

@Composable
fun RecitationSelfScreen(
    modifier: Modifier = Modifier,
    uiState: RecitationSelfUiState,
    onNavigateBack: () -> Unit,
    onNavigateToSurah: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Recite Quran",
                onNavigationIconClick = onNavigateBack,
                isBackVisible = true,
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.play_arrow_24px),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding.customInnerPadding(),
        ) {
            if (uiState.isLoading) loadingItem()
            else {
                txtRecitationItemDesc(descriptionRes = R.string.recitation_description_arabic)
                recitationItemsSelf(surahList = uiState.surahList, onSurahClick = onNavigateToSurah)
            }
        }
    }
}

fun LazyListScope.recitationItemsSelf(
    surahList: List<Surah>,
    onSurahClick: (String) -> Unit
) {
    itemsIndexed(surahList) { index, surah ->
        RecitationSelfListItem(
            modifier = Modifier,
            surah = surah,
            onItemClick = onSurahClick
        )
        if (index != surahList.lastIndex)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}


@Preview
@Composable
private fun DefPrev() {
    ImanBytesTheme {
//        SurahRecitationItem()
    }
}