package com.anticbyte.imanbytes.presentation.random_verse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.anticbyte.imanbytes.domain.model.RandomVerse
import com.anticbyte.imanbytes.domain.model.Tafsir
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.anticbyte.imanbytes.navigation.RandomVerseRoute
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.home.component.RandomVerseCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun RandomVerseScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: RandomVerseViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        AppTopBar(title = "Random Verse")
    }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            RandomVerseCard(modifier = Modifier, verse = state.verse)
        }
    }
}

@HiltViewModel
class RandomVerseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val quranRepo: QuranRepo
) : ViewModel() {
    val args = savedStateHandle.toRoute<RandomVerseRoute>()

    private val _state = MutableStateFlow(RandomVerseState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            quranRepo.getRandomVerse(args.verseId).onSuccess { response ->
                _state.update {
                    it.copy(
                        verse = response
                    )
                }
            }
            quranRepo.getTafsir(
                state.value.verse.surah.number,
                state.value.verse.numberInSurah.toString()
            ).onSuccess { data ->
                _state.update {
                    it.copy(
                        tafsir = data
                    )
                }

            }
        }
    }
}

data class RandomVerseState(
    val verse: RandomVerse = RandomVerse(),
    val tafsir: Tafsir = Tafsir()
)
