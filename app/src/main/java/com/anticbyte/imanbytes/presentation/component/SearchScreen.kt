package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchBarState: SearchBarState,
    textFieldState: TextFieldState,
    surahList: List<Surah> = emptyList()
) {
    ExpandedFullScreenSearchBar(state = searchBarState, inputField = {
        SearchBarDefaults.InputField(
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = {}
        )
    }) {
        LazyColumn(modifier = modifier) {
            items(surahList) { surah ->
                RecitationListItem(
                    surah = surah,
                    onPlaySurah = {},
                    currentSurahNumber = null,
                    shapes = ListItemDefaults.segmentedShapes(surahList.indexOf(surah), surahList.size)
                )
            }
        }
    }
}