package com.felisreader.manga.presentation.manga_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.manga_search.components.FilterField
import com.felisreader.manga.presentation.manga_search.components.MangaCard
import com.felisreader.manga.presentation.manga_search.components.SearchField
import com.felisreader.manga.presentation.manga_search.components.WelcomeDialog

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToInfo: (mangaId: String) -> Unit
) {

    AnimatedVisibility(viewModel.state.value.welcomeDialogVisible) {
        WelcomeDialog(onClose = { viewModel.onEvent(SearchEvent.CloseWelcomeDialog(it)) })
    }

    
    Column {
        SearchField(
            state = viewModel.state.value,
            searchText = viewModel.titleSearchState.collectAsState().value,
            history = viewModel.historyState.collectAsState().value,
            onEvent = viewModel::onEvent
        )
        SearchContent(
            state = viewModel.state.value,
            onEvent = viewModel::onEvent,
            navigateToInfo = navigateToInfo
        )
    }
}

@Composable
fun SearchContent(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigateToInfo: (mangaId: String) -> Unit
) {
    when {
        state.loading && state.mangaList == null -> {
            Loading(
                modifier = Modifier.fillMaxSize(),
                size = 64
            )
        }

        !state.loading && state.mangaList != null -> {
            when {
                state.mangaList.data.isEmpty() -> {
                    // TODO: Empty list error
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        state = state.lazyListState
                    ) {
                        item {
                            FilterField(
                                expanded = state.expandedFilter,
                                onEvent = onEvent,
                                query = state.query
                            )
                        }
                        items(
                            items = state.mangaList.data,
                            key = { it.id }
                        ) { manga ->
                            MangaCard(
                                manga = manga,
                                onCardClick = { navigateToInfo(manga.id) }
                            )
                        }
                        if (state.canLoadMore) {
                            item {
                                Loading(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp),
                                    size = 32
                                )
                                LaunchedEffect(true) {
                                    onEvent(SearchEvent.LoadMore)
                                }
                            }
                        }
                    }
                }
            }
        }

        else -> {
            // TODO: Handle 4xx and 5xx errors
        }
    }
}