package com.anticbyte.imanbytes

import com.anticbyte.imanbytes.data.repo.RecitationRepoImpl
import com.anticbyte.imanbytes.feature.QuranAudioManager
import com.anticbyte.imanbytes.presentation.screens.recitation.RecitationViewModel
import org.junit.Before
import org.junit.Test

class RecitationUnitTest {
    private lateinit var viewModel: RecitationViewModel
    private lateinit var repo: RecitationRepoImpl
    private lateinit var audioManager: QuranAudioManager

    @Before
    fun setup() {
        viewModel = RecitationViewModel(repo, audioManager)
    }

    @Test
    fun testGetAllSurah() {
        val x = audioManager.playQuranAudio("audioUrl")
    }
}