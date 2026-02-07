package com.anticbyte.imanbytes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.repo.AsmaAlHusnaRepo
import com.anticbyte.imanbytes.domain.repo.PrayerTimeRepo
import com.anticbyte.imanbytes.domain.repo.QuranRepo
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PrayerTimeRepo,
    private val asmaRepo: AsmaAlHusnaRepo,
    private val quranRepo: QuranRepo
) : ViewModel() {
    private val remoteConfig = Firebase.remoteConfig
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchHomeData(isRefreshing = false)
        setupRemoteConfig()
    }

    private fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = BuildConfig.REMOTE_CONFIG_INTERVAL
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    fun refresh() = fetchHomeData(isRefreshing = true)

    private fun fetchHomeData(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = !isRefreshing,
                    isRefreshing = isRefreshing,
                    error = null
                )
            }

            val result = runCatching {
                coroutineScope {
                    val randomVerseDeferred = async {
                        quranRepo.getRandomVerse(
                            Random.nextInt(1, 6236).toString()
                        ).getOrThrow()
                    }

                    val asmaDeferred = async {
                        asmaRepo.getAsma(
                            Random.nextInt(1, 99).toString()
                        ).getOrThrow()
                    }

                    val prayerTimeDeferred = async {
                        val today = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
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
                val isRamadanEnabled = runCatching {
                    remoteConfig.fetchAndActivate().await()
                    remoteConfig.getBoolean("ramadan_overview")
                }.getOrElse {
                    false
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        randomVerse = verse,
                        asma = asma,
                        prayerTime = prayerTimes,
                        ramadanOverView = if (isRamadanEnabled) prayerTimes else null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = throwable.localizedMessage
                    )
                }
            }
        }
    }
}