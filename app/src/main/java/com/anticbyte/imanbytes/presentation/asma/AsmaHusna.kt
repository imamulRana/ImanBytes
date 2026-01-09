package com.anticbyte.imanbytes.presentation.asma

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.presentation.component.AppErrorScreen
import com.anticbyte.imanbytes.presentation.component.AppLoader
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun AsmaHusnaRoute(
    viewModel: AsmaHusnaViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AsmaHusnaScreen(
        uiState = uiState,
        onNavigateUp = navigateUp
    )
}

@Composable
fun AsmaHusnaScreen(
    uiState: AsmaHusnaScreenState,
    onNavigateUp: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(
                title = "Asma Al Husna",
                isBackVisible = true,
                onNavigationIconClick = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        }, contentWindowInsets = WindowInsets(
            bottom = 88.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (uiState.isLoading) AppLoader()
            else if (uiState.errorMessage != null) AppErrorScreen(
                errorMessage = uiState.errorMessage,
                onRetry = {})
            else
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)
                ) {
                    asmaAlHusnaList(uiState.asmaList)
                }
        }
    }
}

fun LazyListScope.asmaAlHusnaList(asmaList: List<Asma>) {
    itemsIndexed(asmaList) { index, asma ->
        SegmentedListItem(
            onClick = {},
            shapes = ListItemDefaults.segmentedShapes(index, asmaList.size),
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = colorScheme.secondaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        asma.number.toString(),
                        color = colorScheme.onSecondaryContainer
                    )
                }
            },
            supportingContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(asma.englishMeaning, style = typography.titleMedium)
                    Text(asma.transliteration, style = typography.bodySmall)
                }
            },
            colors = ListItemDefaults.segmentedColors(containerColor = colorScheme.surfaceContainerLow)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = asma.name,
                textAlign = TextAlign.Right,
                style = typography.headlineLargeEmphasized.copy(fontFamily = FontFamily(Font(R.font.lateef)))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AsmaHusnaScreenPreview() {
    ImanBytesTheme(dynamicColor = false) {
        AsmaHusnaScreen(
            uiState = AsmaHusnaScreenState(
                asmaList = listOf(
                    Asma(
                        number = 1,
                        name = "Arabic text",
                        englishMeaning = "The most merciful",
                        transliteration = "Ar rahman"
                    )
                )
            )
        )
    }
}
