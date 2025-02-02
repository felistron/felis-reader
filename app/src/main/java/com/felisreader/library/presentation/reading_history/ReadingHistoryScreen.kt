package com.felisreader.library.presentation.reading_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.library.presentation.reading_history.components.ReadingHistoryCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
fun ReadingHistoryScreen(
    viewModel: ReadingHistoryViewModel = hiltViewModel(),
    navigateToLector: (chapterId: String) -> Unit,
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent

    LaunchedEffect(true) {
        if (viewModel.state.value.history == null) {
            viewModel.onEvent(ReadingHistoryEvent.LoadHistory())
        }
    }

    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            onEvent(ReadingHistoryEvent.LoadHistory {
                // delay to trick user into thinking that the refresh process takes more time
                // bc sometimes refresh is too fast and the user may think that nothing happen
                delay(500)
                refreshing = false
            })
        }
    }

    SwipeRefresh(
        modifier = Modifier,
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true }
    ) {
        ReadingHistoryContent(
            state = state,
            onEvent = onEvent,
            navigateToLector = navigateToLector,
        )
    }
}

@Composable
fun ReadingHistoryContent(
    state: ReadingHistoryState,
    onEvent: (ReadingHistoryEvent) -> Unit,
    navigateToLector: (chapterId: String) -> Unit,
) {
    if (state.history == null) {
        Loading(modifier = Modifier.fillMaxSize(), size = 64)
    } else {
        if (state.history.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(id = R.string.ui_empty_result))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = state.history,
                    key = { it.second.id }
                ) { chapter ->
                    ReadingHistoryCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        chapter = chapter.second,
                        manga = chapter.first,
                        onClick = navigateToLector,
                        onDelete = { onEvent(ReadingHistoryEvent.OnDeleteChapter(it)) }
                    )
                }

                if (state.canLoadMore) {
                    item {
                        Loading(modifier = Modifier.fillMaxWidth(), size = 32)
                        LaunchedEffect(true) {
                            onEvent(ReadingHistoryEvent.LoadMore)
                        }
                    }
                }
            }
        }
    }
}
