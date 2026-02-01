package com.anticbyte.imanbytes.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Surah
import com.anticbyte.imanbytes.presentation.screens.audioRecitation.component.RecitationListItem
import com.anticbyte.imanbytes.utils.lzColCustomPadding
import kotlinx.coroutines.delay

@Composable
fun SearchDialog(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    textFieldState: TextFieldState,
    surahList: List<Surah> = emptyList(),
    onPlaySurah: (surahNumber: String) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchBarState = rememberSearchBarState()

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            delay(100)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    if (isExpanded)
        BasicAlertDialog(
            onDismissRequest = onBack,
            modifier = modifier, properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            Scaffold(
                topBar = {
                    AppBarWithSearch(
                        modifier = Modifier.padding(bottom = 4.dp), navigationIcon = {
                            FilledTonalIconButton(
                                onClick = onBack,
                                shapes = IconButtonDefaults.shapes()
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                                    contentDescription = null
                                )
                            }
                        }, state = searchBarState, inputField = {
                            SearchBarDefaults.InputField(
                                modifier = Modifier.focusRequester(focusRequester),
                                searchBarState = searchBarState,
                                textFieldState = textFieldState,
                                onSearch = {},
                                trailingIcon = {
                                    IconButton(onClick = {
                                        textFieldState.clearText()
                                    }, shapes = IconButtonDefaults.shapes()) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                                            contentDescription = null
                                        )
                                    }
                                },
                                colors = inputFieldColors(
                                    focusedContainerColor = colorScheme.surfaceContainer,
                                    unfocusedContainerColor = colorScheme.surfaceContainer,
                                ),
                                placeholder = {
                                    Text("Search surah (e.g. Luqman)")
                                }
                            )
                        }
                    )
                },
                contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.navigationBars)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(it)
                ) {
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = lzColCustomPadding
                    ) {
                        if (textFieldState.text.isEmpty())
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // TODO: add some meaningful placeholder here
                                    Text("Search results")
                                }
                            }
                        else if (surahList.isEmpty()) item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                // TODO: add some meaningful placeholder here
                                Text("No result")
                            }
                        }
                        else
                            itemsIndexed(surahList) { index, surah ->
                                RecitationListItem(
                                    surah = surah,
                                    onPlaySurah = onPlaySurah,
                                    nowPlayingItem = "",
                                    shapes = ListItemDefaults.segmentedShapes(
                                        index,
                                        count = surahList.size
                                    )
                                )
                            }
                    }
                }
            }
        }
}