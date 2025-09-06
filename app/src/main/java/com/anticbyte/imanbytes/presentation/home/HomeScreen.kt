package com.anticbyte.imanbytes.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.navigation.NavigationBarItem
import com.anticbyte.imanbytes.presentation.component.AppBottomBar
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.AudioPlayerSmall
import com.anticbyte.imanbytes.presentation.component.SectionTitle
import com.anticbyte.imanbytes.presentation.profile.ProfileViewModel
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val imageList = listOf(R.drawable.img_quran_verse, R.drawable.img_hadith)
    Scaffold(
        topBar = {
            AppTopBar(title = "Home")
        }
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            //todo get next prayer time
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Next Prayer is Dhur at 1:30 PM",
                style = typography.headlineLarge,
            )
            Column {
                SectionTitle(sectionTitle = "Daily Hadith")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(2) { image ->
                        Image(
                            bitmap = ImageBitmap.imageResource(id = imageList[image]),
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .clip(shape = shapes.extraLarge)
                                .clickable(onClick = {
                                    // todo apply on click
                                })
                                .aspectRatio(1 / 1f),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Column {
                SectionTitle(sectionTitle = "continue where you've left of")
                AudioPlayerSmall(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    ImanBytesTheme(darkTheme = true, dynamicColor = false) {
        HomeScreen()
    }
}