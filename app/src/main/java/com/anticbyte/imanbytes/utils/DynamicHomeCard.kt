package com.anticbyte.imanbytes.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.theme.ExtendedColorScheme
import com.anticbyte.imanbytes.theme.ImanBytesTheme
import com.anticbyte.imanbytes.theme.extendedLight

@Composable
fun DynamicHomeCard(modifier: Modifier = Modifier) {
    TitleLayout(title = { Text("Prayer times".uppercase(), style = typography.labelSmall) }) {
        repeat(5){
            ListItem(headlineContent = {Text("Title $it")})
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

val PaddingValues.miniPlayerPadding get() = PaddingValues(
    start = this.calculateStartPadding(LayoutDirection.Ltr),
    end = this.calculateEndPadding(LayoutDirection.Ltr),
    top = this.calculateTopPadding(),
    bottom = this.calculateBottomPadding().plus(72.dp)
)

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