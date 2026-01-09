package com.anticbyte.imanbytes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.AsmaAlHusnaRepo
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PrayerTimeRepo,
    private val asmaRepo: AsmaAlHusnaRepo,
    private val quranRepo: QuranRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow().onStart {
        fetchHomeData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _uiState.value
    )

    fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = runCatching {
                coroutineScope {
                    val randomVerseDeferred = async {
                        quranRepo.getRandomVerse(
                            Random.nextInt(1, 6236).toString()
                        ).getOrThrow()
                    }

                    val asmaDeferred = async {
                        asmaRepo.getSingleAsma(
                            Random.nextInt(1, 99).toString()
                        ).getOrThrow()
                    }

                    val prayerTimeDeferred = async {
                        val today = SimpleDateFormat("dd-MM-yyyy")
                            .format(Calendar.getInstance().time)
                        repo.getPrayerTimes(today).getOrThrow()
                    }

                    Triple(
                        randomVerseDeferred.await(),
                        asmaDeferred.await(),
                        prayerTimeDeferred.await()
                    )
                }
            }

            result.onSuccess { (verse, asma, prayerTimes) ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        randomVerse = verse,
                        asma = asma,
                        prayerTimes = prayerTimes
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.localizedMessage
                    )
                }
            }
        }
    }

}