package com.anticbyte.imanbytes.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.theme.ExtendedColorScheme
import com.anticbyte.imanbytes.theme.ImanBytesTheme

@Composable
fun DynamicHomeCard(modifier: Modifier = Modifier) {
    TitleLayout(title = { Text("Prayer times".uppercase(), style = typography.labelSmall) }) {
        repeat(5) {
            ListItem(headlineContent = { Text("Title $it") })
        }
    }
}

//
@Composable
fun TitleLayout(
    modifier: Modifier = Modifier,
    title: @Composable (modifier: Modifier) -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column(verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap)) {
        title(modifier.padding(vertical = 8.dp, horizontal = 4.dp))
        content()
    }
}

val miniPlayerPadding = PaddingValues(
    start = 16.dp,
    end = 16.dp,
    top = 12.dp,
    bottom = 88.dp
)

val lzColCustomPadding: PaddingValues
    @Composable get() = WindowInsets.safeDrawing
        .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
        .add(WindowInsets(top = 12.dp, left = 16.dp, right = 16.dp, bottom = 88.dp))
        .asPaddingValues()

val LocalExtendedColors = staticCompositionLocalOf<ExtendedColorScheme> {
    error("No ExtendedColors provided")
}

@Preview()
@Composable
private fun DynamicHomeCardPreview() {
    ImanBytesTheme(dynamicColor = false) {
        DynamicHomeCard()
    }
}