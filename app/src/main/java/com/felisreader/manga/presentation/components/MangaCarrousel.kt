package com.felisreader.manga.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.presentation.manga_search.components.CoverThumbnail

@Composable
fun MangaCarrousel(
    mangas: List<Manga>,
    navigateToManga: (mangaId: String) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = mangas,
            key = { it.id }
        ) { manga ->
            MangaCarrouselItem(
                manga = manga,
                onClick = { navigateToManga(manga.id) }
            )
        }
    }
}

@Composable
fun MangaCarrouselItem(
    manga: Manga,
    onClick: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.width(200.dp)
        ) {
            Box(
                modifier = Modifier.height(300.dp),
                contentAlignment = Alignment.Center,
            ) {
                CoverThumbnail(coverUrl = manga.coverUrlSave)
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = manga.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}