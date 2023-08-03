package com.felisreader.manga.presentation.manga_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.manga.presentation.manga_info.components.Loading
import com.felisreader.manga.presentation.manga_search.components.FilterField
import com.felisreader.manga.presentation.manga_search.components.MangaCard
import com.felisreader.manga.presentation.manga_search.components.SearchField
import com.felisreader.manga.presentation.manga_search.components.WelcomeDialog

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToInfo: (id: String) -> Unit
) {

    AnimatedVisibility(viewModel.state.value.welcomeDialogVisible) {
        WelcomeDialog(onClose = { viewModel.onEvent(SearchEvent.CloseWelcomeDialog(it)) })
    }

    
    Column {
        SearchField(
            state = viewModel.state.value,
            onEvent = viewModel::onEvent
        )
        SearchContent(
            state = viewModel.state.value,
            onEvent = viewModel::onEvent,
            onNavigateToInfo = onNavigateToInfo
        )
    }
}

@Composable
fun SearchContent(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    onNavigateToInfo: (id: String) -> Unit
) {
    if (state.loading && state.mangaList == null) {
        Loading(
            modifier = Modifier.fillMaxSize(),
            size = 64
        )
    } else {
        if (state.mangaList != null) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = state.lazyListState
            ) {
                item {
                    Column {
                        FilterField(
                            expanded = state.expandedFilter,
                            onEvent = onEvent,
                            query = state.query
                        )
                        Text(
                            text = "Showing ${state.mangaList.data.size} of ${state.mangaList.total} results"
                        )
                    }
                }
                items(
                    items = state.mangaList.data,
                    key = { it.id }
                ) {
                    MangaCard(
                        manga = it,
                        onCardClick = { onNavigateToInfo(it.id) }
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
        } else {
            Text(text = "Something went wrong...")
        }
    }
}