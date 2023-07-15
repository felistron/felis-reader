package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.felisreader.manga.domain.model.Manga

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusField(manga: Manga) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        AssistChip(
            onClick = { /*TODO*/ },
            label = {
                Text(text = "${manga.year ?: "unknown"}")
            },
            leadingIcon = {
                Text(text = "Year:")
            },
            border = null
        )
        AssistChip(
            onClick = { /*TODO*/ },
            label = {
                Text(text = manga.status.value)
            },
            leadingIcon = {
                Text(text = "Status:")
            },
            border = null
        )
    }
}