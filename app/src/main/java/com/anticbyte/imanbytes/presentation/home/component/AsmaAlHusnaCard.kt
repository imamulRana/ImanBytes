package com.anticbyte.imanbytes.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.anticbyte.imanbytes.R
import com.anticbyte.imanbytes.domain.model.Asma
import com.anticbyte.imanbytes.utils.shareCardText

@Composable
fun AsmaAlHusnaCard(
    modifier: Modifier = Modifier,
    asma: Asma
) {
    val context = LocalContext.current
    ElevatedCard(modifier = modifier, shape = shapes.extraLarge) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = colorScheme.primaryContainer,
                            shape = CircleShape
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(asma.number.toString(), style = typography.titleMedium)
                }
                Text(
                    asma.name, style = typography.displayLarge.copy(
                        fontFamily = FontFamily(Font(R.font.lateef))
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    asma.transliteration,
                    style = typography.titleLarge
                )
                Text(asma.englishMeaning, style = typography.bodyMedium)
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("${asma.number} / 99 Names of Allah", style = typography.bodySmall)
                FilledTonalIconButton(
                    onClick = {
                        context.shareCardText("$asma")
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(IconButtonDefaults.extraSmallContainerSize()),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(IconButtonDefaults.extraSmallIconSize),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_share_fill),
                        contentDescription = null
                    )
                }
            }
        }
    }
}