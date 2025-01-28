package com.felisreader.manga.presentation.manga_search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.manga_search.components.FilterDialog
import com.felisreader.manga.presentation.manga_search.components.MangaCard
import com.felisreader.manga.presentation.manga_search.components.SearchField
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToInfo: (mangaId: String) -> Unit,
    title: String?,
    tag: String?,
) {
    AnimatedVisibility(
        visible = viewModel.state.value.expandedFilter,
        enter = EnterTransition.None,
        exit = ExitTransition.None,
    ) {
        FilterDialog(
            expanded = viewModel.state.value.expandedFilter,
            onEvent = viewModel::onEvent,
            query = viewModel.state.value.query,
            supportedTags = viewModel.tagsState.collectAsState().value,
        )
    }

    LaunchedEffect(true) {
        if (viewModel.state.value.mangaList == null) {
            viewModel.onEvent(SearchEvent.LoadMangaList(title = title, tag = tag))
        }
    }

    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            viewModel.onEvent(
                SearchEvent.LoadMangaList(
                    title = viewModel.state.value.query.title,
                    tag = viewModel.state.value.query.includedTags?.firstOrNull(),
                ) {
                    // delay to trick user into thinking that the refresh process takes more time
                    // bc sometimes refresh is too fast and the user may think that nothing happened
                    delay(500)
                    refreshing = false
                }
            )
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true }
    ) {
        Column {
            SearchField(
                state = viewModel.state.value,
                searchText = viewModel.titleSearchState.collectAsState().value,
                history = viewModel.historyState.collectAsState().value,
                onEvent = viewModel::onEvent,
            )
            AssistChip(
                onClick = { viewModel.onEvent(SearchEvent.ToggleFilter) },
                label = {
                    Text(text = stringResource(id = R.string.filter))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.FilterList,
                        contentDescription = "Outlined filter list icon"
                    )
                },
                border = null,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SearchContent(
                state = viewModel.state.value,
                onEvent = viewModel::onEvent,
                navigateToInfo = navigateToInfo,
            )
        }
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
            Loading(modifier = Modifier.fillMaxSize(), size = 64)
        }

        state.mangaList != null -> {
            when {
                state.mangaList.data.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(stringResource(id = R.string.ui_empty_result))
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        state = state.lazyListState
                    ) {
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
    }
}