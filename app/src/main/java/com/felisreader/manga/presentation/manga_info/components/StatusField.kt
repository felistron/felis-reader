package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.felisreader.manga.domain.model.Manga

@Composable
fun StatusField(manga: Manga) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        AssistChip(
            onClick = { /*TODO*/ },
            label = {
                Text(text = if (manga.statistics == null) "N/A" else String.format("%.2f", manga.statistics.rating.bayesian))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.StarBorder, contentDescription = "Rating")
            },
            border = null
        )
        AssistChip(
            onClick = { },
            enabled = false,
            label = {
                Text(text = "${manga.year ?: "-"}")
            },
            leadingIcon = {
                Text(text = "Year:")
            },
            border = null,
            colors = AssistChipDefaults.assistChipColors(
                disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledLeadingIconContentColor = MaterialTheme.colorScheme.primary,
            )
        )
        AssistChip(
            onClick = { },
            enabled = false,
            label = {
                Text(text = manga.status.value)
            },
            leadingIcon = {
                Text(text = "Status:")
            },
            border = null,
            colors = AssistChipDefaults.assistChipColors(
                disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledLeadingIconContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}