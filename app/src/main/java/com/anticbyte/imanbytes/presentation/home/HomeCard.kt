package com.anticbyte.imanbytes.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.theme.ImanBytesTheme

fun generateWord(words: Int = 20): String {
    return LoremIpsum(words).values.first()
}

@Composable
fun HomeCard(modifier: Modifier = Modifier) {
    SegmentedListItem(
        onClick = {}, shapes = ListItemDefaults.shapes(),
        overlineContent = {
            Column {
                Text(generateWord(2), style = typography.titleLarge)
                Text("Surha: 233", style = typography.labelSmall)
            }
        }) {
        Text(generateWord(20), style = typography.bodyMedium)
    }
}


/*{
    ListItem(
        headlineContent = {
            Text(generateWord(2), style = typography.titleLarge)
        },
        supportingContent = {
            Text("Surha: 233")
        },
    )
    ListItem(
        headlineContent = {
            Text(generateWord(), style = typography.bodyMedium)
        })
    ListItem(
        overlineContent = {
            Text(generateWord(5))

        },
        headlineContent = {},
        trailingContent = {
            FilledTonalIconButton(onClick = {}) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill), null)
            }
        }
    )
}*/

@PreviewParameter(LoremIpsum::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeCardPreview() {
    ImanBytesTheme(dynamicColor = false) {
        HomeCard(modifier = Modifier.padding(16.dp))
    }
}