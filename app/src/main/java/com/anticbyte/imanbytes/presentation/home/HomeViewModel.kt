package com.anticbyte.imanbytes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.domain.repo.AsmaAlHusnaRepo
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PrayerTimeRepo,
    private val asmaRepo: AsmaAlHusnaRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPrayerTime()
        fetchAsmaAlHusna()
    }

    private fun fetchAsmaAlHusna() {
        viewModelScope.launch {
            asmaRepo.getSingleAsma(Random.nextInt(1,99).toString()).onSuccess { asma ->
                _uiState.update {
                    it.copy(asma = asma)
                }
            }
        }
    }

    private fun fetchPrayerTime() {
        val date = Calendar.getInstance()
        val today = SimpleDateFormat("dd-MM-yyyy").format(date.time)
        viewModelScope.launch {
            repo.getPrayerTimes(today).onSuccess { response ->
                _uiState.update {
                    it.copy(prayerTimes = response)
                }
            }.onFailure {
                _uiState.value = _uiState.value.copy(error = it.localizedMessage)
            }
        }
    }
}