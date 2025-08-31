@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imanbytes.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.presentation.component.AppTopBar
import com.anticbyte.imanbytes.presentation.component.KnowledgeSectionItem
import com.anticbyte.imanbytes.theme.ImanBytesTheme



@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
//    viewModel: ProfileViewModel = viewModel()
) {
//    val userData by viewModel.deviceID.collectAsStateWithLifecycle()

//    val captureController = remember { Capturecont}

    Scaffold(topBar = {
        AppTopBar(
            title = "Profile",
            onNavigationIconClick = navigateUp
        )
    }) {
        Column(
            modifier = modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //User Info Section
            ListItem(
                headlineContent = {
                    Text("userData")
                },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            "H",
                            modifier = Modifier.align(Alignment.Center),
                            style = typography.headlineSmall
                        )
                    }
                },
                supportingContent = {
                    Text("Change profile picture")
                }
            )

            //Progress Section
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Progress")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { .2f },
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "LV 20",
                        style = typography.labelSmall
                    )
                }
            }

            //More Section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("More")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    KnowledgeSectionItem()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ImanBytesTheme {
        ProfileScreen()
    }
}