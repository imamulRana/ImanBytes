@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.anticbyte.imaanbytes.presentation.screens.profile

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imaanbytes.R
import com.anticbyte.imaanbytes.presentation.component.AppTopBar
import com.anticbyte.imaanbytes.presentation.theme.ImanBytesTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    val profileHorizontalPadding = Modifier.padding(horizontal = 16.dp)

    Scaffold(topBar = {
        AppTopBar(
            title = "Profile", subtitle = "Customize your profile",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.arrow_forward),
                        modifier = Modifier.rotate(180f),
                        contentDescription = null
                    )
                }
            })
    }) {
        Column(
            modifier = modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //User Info Section
            ListItem(
                headlineContent = {
                    Text("Homme Puree")
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
                modifier = profileHorizontalPadding,
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
                modifier = profileHorizontalPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("More")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ListItem(
                        headlineContent = {
                            Text("Favorites")
                        },
                        supportingContent = {
                            Text("Find all your favorites")
                        },
                        colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainer)
                    )
                    ListItem(
                        headlineContent = {
                            Text("Completed")
                        },
                        supportingContent = {
                            Text("Your completed and visited cards")
                        },
                        colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainer)
                    )
                    ListItem(
                        headlineContent = {
                            Text("Topics")
                        },
                        supportingContent = {
                            Text("Select topics to get your desired knowledge ")
                        },
                        colors = ListItemDefaults.colors(containerColor = colorScheme.surfaceContainer)
                    )
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