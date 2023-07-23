package com.felisreader.chapter.presentation.chapter_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.chapter.presentation.chapter_list.components.ChapterCard
import com.felisreader.manga.presentation.manga_info.components.Loading

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChapterListScreen(
    viewModel: ChapterListViewModel = hiltViewModel(),
    mangaId: String,
    navigateToLector: (chapterId: String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.onEvent(ChapterListEvent.FeedChapters(mangaId))
    }

    ChapterListContent(
        state = viewModel.state.value,
        navigateToLector = navigateToLector
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChapterListContent(
    state: ChapterListState,
    navigateToLector: (chapterId: String) -> Unit
) {
    if (state.loading) {
        Loading(modifier = Modifier.fillMaxSize(), size = 64)
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            state.chapterList.forEach { (volume, volumes) ->
                stickyHeader(
                    key = volume
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "Volume $volume",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                volumes.forEach { (chapter, chapters) ->

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        ) {
                            Text(
                                text = "Chapter $chapter",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Divider()
                        }
                    }

                    items(
                        items = chapters,
                        key = { it.id }
                    ) {
                        ChapterCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            chapter = it,
                            onButtonClick = navigateToLector
                        )
                    }
                }
            }
        }
    }
}