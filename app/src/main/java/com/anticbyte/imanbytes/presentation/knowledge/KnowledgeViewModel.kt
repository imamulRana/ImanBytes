package com.anticbyte.imanbytes.presentation.knowledge

import androidx.lifecycle.ViewModel
import com.anticbyte.imanbytes.BuildConfig
import com.anticbyte.imanbytes.R
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KnowledgeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(KnowledgeScreenState())
    val uiState = _uiState.asStateFlow()

    private val remoteConfig = Firebase.remoteConfig

    init {
        setupRemoteConfig()
        loadKnowledgeItems()
    }

    private fun setupRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds =
                if (BuildConfig.DEBUG) 0 else 0
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    private fun loadKnowledgeItems() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            knowledgeItems = buildKnowledgeItems(),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = task.exception?.message ?: "Failed to load configuration"
                        )
                    }
                }
            }
    }

    private fun buildKnowledgeItems(): List<KnowledgeItem> =
        buildList {
            add(KnowledgeItem.ASMA)
            add(KnowledgeItem.SEARCH)

            if (remoteConfig.getBoolean("is_ramadan")) {
                add(KnowledgeItem.RAMADAN)
            }
        }
}

