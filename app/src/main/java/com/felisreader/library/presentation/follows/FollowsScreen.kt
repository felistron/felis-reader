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
                                mangas = state.reading,
                                navigateToInfo = navigateToInfo,
                                loadMangaStatus = { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.READING)) },
                                onRefresh = {
                                    onEvent(FollowsEvent.LoadAllReadingStatus {
                                        onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.READING))
                                        it()
                                    })
                                }
                            )
                        }

                        1 -> {
                            StatusItem(
                                mangas = state.onHold,
                                navigateToInfo = navigateToInfo,
                                loadMangaStatus = { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.ON_HOLD)) },
                                onRefresh = {
                                    onEvent(FollowsEvent.LoadAllReadingStatus {
                                        onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.ON_HOLD))
                                        it()
                                    })
                                }
                            )
                        }

                        2 -> {
                            StatusItem(
                                mangas = state.planToRead,
                                navigateToInfo = navigateToInfo,
                                loadMangaStatus = { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.PLAN_TO_READ)) },
                                onRefresh = {
                                    onEvent(FollowsEvent.LoadAllReadingStatus {
                                        onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.PLAN_TO_READ))
                                        it()
                                    })
                                }
                            )
                        }

                        3 -> {
                            StatusItem(
                                mangas = state.dropped,
                                navigateToInfo = navigateToInfo,
                                loadMangaStatus = { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.DROPPED)) },
                                onRefresh = {
                                    onEvent(FollowsEvent.LoadAllReadingStatus {
                                        onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.DROPPED))
                                        it()
                                    })
                                }
                            )
                        }

                        4 -> {
                            StatusItem(
                                mangas = state.reReading,
                                navigateToInfo = navigateToInfo,
                                loadMangaStatus = { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.RE_READING)) },
                                onRefresh = {
                                    onEvent(FollowsEvent.LoadAllReadingStatus {
                                        onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.RE_READING))
                                        it()
                                    })
                                }
                            )
                        }

                        5 -> {
                            StatusItem(
                                mangas = state.completed,
                                navigateToInfo = navigateToInfo,
                                loadMangaStatus = { onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.COMPLETED)) },
                                onRefresh = {
                                    onEvent(FollowsEvent.LoadAllReadingStatus {
                                        onEvent(FollowsEvent.LoadReadingStatus(ReadingStatus.COMPLETED))
                                        it()
                                    })
                                }
                            )
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
    onRefresh: (callback: suspend () -> Unit) -> Unit,
) {
    if (mangas != null) {
        MangaLazyList(
            modifier = Modifier.fillMaxSize(),
            items = mangas,
            onItemClick = navigateToInfo,
            onRefresh = onRefresh,
        )
    } else {
        LaunchedEffect(true) {
            loadMangaStatus()
        }
        Loading(modifier = Modifier.fillMaxSize(), 64)
    }
}