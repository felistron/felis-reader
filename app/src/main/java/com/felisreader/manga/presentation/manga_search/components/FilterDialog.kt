package com.felisreader.manga.presentation.manga_search.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.model.api.TagEntity
import com.felisreader.manga.presentation.manga_search.SearchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    expanded: Boolean,
    onEvent: (SearchEvent) -> Unit,
    query: MangaListQuery,
    supportedTags: List<TagEntity>,
) {
    AlertDialog(
        onDismissRequest = { onEvent(SearchEvent.ToggleFilter) }
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = MaterialTheme.shapes.large,
        ) {
            FilterField(
                expanded = expanded,
                onEvent = onEvent,
                query = query,
                supportedTags = supportedTags,
            )
        }
    }
}