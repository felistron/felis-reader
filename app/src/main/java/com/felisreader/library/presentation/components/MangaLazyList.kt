package com.felisreader.library.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.felisreader.R
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.presentation.manga_search.components.MangaCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun MangaLazyList(
    modifier: Modifier = Modifier,
    items: List<Manga>,
    onItemClick: (mangaId: String) -> Unit,
    onRefresh: (callback: suspend () -> Unit) -> Unit,
) {
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            onRefresh {
                delay(500)
                refreshing = false
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true }
    ) {
        MangaLazyListContent(
            items = items,
            modifier = modifier,
            onItemClick = onItemClick,
        )
    }
}

@Composable
fun MangaLazyListContent(
    items: List<Manga>,
    modifier: Modifier = Modifier,
    onItemClick: (mangaId: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (items.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(id = R.string.ui_empty_result))
                }
            }
        }

        items(
            items = items,
            key = { it.id }
        ) { manga ->
            MangaCard(
                modifier = Modifier.animateItem(),
                manga = manga,
                onCardClick = { onItemClick(manga.id) },
            )
        }

    }
}