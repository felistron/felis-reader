package com.felisreader.library.presentation.manga_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.manga_search.components.MangaCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun MangaHistoryScreen(
    viewModel: MangaHistoryViewModel = hiltViewModel(),
    navigateToManga: (mangaId: String) -> Unit,
) {
    LaunchedEffect(true) {
        if (viewModel.state.value.history == null) {
            viewModel.onEvent(MangaHistoryEvent.LoadHistory())
        }
    }

    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            viewModel.onEvent(MangaHistoryEvent.LoadHistory {
                // delay to trick user into thinking that the refresh process takes more time
                // bc sometimes refresh is too fast and the user may think that nothing happen
                delay(500)
                refreshing = false
            })
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true }
    ) {
        MangaHistoryContent(
            state = viewModel.state.value,
            onEvent = viewModel::onEvent,
            navigateToManga = navigateToManga,
        )
    }
}

@Composable
fun MangaHistoryContent(
    state: MangaHistoryState,
    onEvent: (MangaHistoryEvent) -> Unit,
    navigateToManga: (mangaId: String) -> Unit
) {
    val scrollState by remember { mutableStateOf(LazyListState()) }

    when {

        state.history == null -> {
            Loading(modifier = Modifier.fillMaxSize(), size = 64)
        }

        state.history.isEmpty() && !state.canLoadMore  -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(stringResource(id = R.string.ui_empty_result))
            }
        }

        state.history.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = scrollState
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(
                    items = state.history,
                    key = { it.id }
                ) { manga ->
                    Box(
                        contentAlignment = Alignment.TopEnd
                    ) {
                        MangaCard(
                            manga = manga,
                            onCardClick = { navigateToManga(manga.id) },
                        )
                        IconButton(
                            onClick = { onEvent(MangaHistoryEvent.DeleteHistoryItem(manga.id)) },
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                if (state.canLoadMore) {
                    item {
                        Loading(
                            modifier = Modifier.fillMaxWidth(),
                            size = 32
                        )
                        LaunchedEffect(true) {
                            onEvent(MangaHistoryEvent.LoadMore)
                        }
                    }
                }
            }
        }
    }
}