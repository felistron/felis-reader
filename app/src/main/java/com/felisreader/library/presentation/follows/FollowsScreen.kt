package com.felisreader.library.presentation.follows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.library.presentation.components.MangaLazyList
import com.felisreader.manga.domain.model.Manga
import com.felisreader.user.domain.model.api.ReadingStatus
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowsScreen(
    viewModel: FollowsViewModel = hiltViewModel(),
    navigateToInfo: (mangaId: String) -> Unit,
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent

    var selected by remember { mutableIntStateOf(state.selectedTab) }

    val tabs = listOf(
        stringResource(id = R.string.reading_status_reading),
        stringResource(id = R.string.reading_status_on_hold),
        stringResource(id = R.string.reading_status_plan_to_read),
        stringResource(id = R.string.reading_status_dropped),
        stringResource(id = R.string.reading_status_re_reading),
        stringResource(id = R.string.reading_status_completed),
    )

    val directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)

    val dismissState = rememberDismissState(
        positionalThreshold = { it * 0.5f },
        confirmValueChange = {
            when(it) {
                DismissValue.DismissedToStart -> {
                    selected = min(tabs.size - 1, selected + 1)
                    onEvent(FollowsEvent.SetSelectedTab(selected))
                    return@rememberDismissState false
                }
                DismissValue.DismissedToEnd -> {
                    selected = max(0, selected - 1)
                    onEvent(FollowsEvent.SetSelectedTab(selected))
                    return@rememberDismissState false
                }
                else -> return@rememberDismissState false
            }
        }
    )

    Column {
        ScrollableTabRow(
            selectedTabIndex = selected
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selected == index,
                    onClick = {
                        selected = index
                        onEvent(FollowsEvent.SetSelectedTab(selected))
                    }
                )
            }
        }
        if (state.statuses != null) {
            SwipeToDismiss(
                modifier = Modifier.padding(16.dp),
                state = dismissState,
                directions = directions,
                dismissContent = {
                    when (selected) {
                        0 -> {
                            StatusItem(
                                state.reading,
                                navigateToInfo
                            ) { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.READING)) }
                        }

                        1 -> {
                            StatusItem(
                                state.onHold,
                                navigateToInfo
                            ) { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.ON_HOLD)) }
                        }

                        2 -> {
                            StatusItem(
                                state.planToRead,
                                navigateToInfo
                            ) { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.PLAN_TO_READ)) }
                        }

                        3 -> {
                            StatusItem(
                                state.dropped,
                                navigateToInfo
                            ) { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.DROPPED)) }
                        }

                        4 -> {
                            StatusItem(
                                state.reReading,
                                navigateToInfo
                            ) { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.RE_READING)) }
                        }

                        5 -> {
                            StatusItem(
                                state.completed,
                                navigateToInfo
                            ) { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.COMPLETED)) }
                        }
                    }
                },
                background = {

                }
            )
        }
    }
}

@Composable
fun StatusItem(
    mangas: List<Manga>?,
    navigateToInfo: (mangaId: String) -> Unit,
    loadMangaStatus: () -> Unit,
) {
    if (mangas != null) {
        MangaLazyList(
            modifier = Modifier.fillMaxSize(),
            items = mangas,
            onItemClick = navigateToInfo,
        )
    } else {
        LaunchedEffect(true) {
            loadMangaStatus()
        }
        Loading(modifier = Modifier.fillMaxSize(), 64)
    }
}