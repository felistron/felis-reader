package com.felisreader.chapter.presentation.chapter_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.chapter.presentation.chapter_list.components.ChapterCard

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
        onEvent = viewModel::onEvent,
        navigateToLector = navigateToLector
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChapterListContent(
    state: ChapterListState,
    onEvent: (ChapterListEvent) -> Unit,
    navigateToLector: (chapterId: String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        state.chapterList.forEach {(volume, volumes) ->
            stickyHeader(
                key = volume
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        text = "Volume $volume",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            volumes.forEach { (chapter, chapters) ->
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        text = "Chapter $chapter",
                        style = MaterialTheme.typography.titleMedium
                    )
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