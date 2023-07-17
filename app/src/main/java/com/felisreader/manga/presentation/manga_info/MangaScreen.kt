package com.felisreader.manga.presentation.manga_info

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.presentation.TagChipGroup
import com.felisreader.manga.presentation.manga_info.components.*

@Composable
fun MangaScreen(
    viewModel: MangaViewModel = hiltViewModel(),
    id: String
) {
    LaunchedEffect(true) {
        viewModel.onEvent(MangaEvent.LoadManga(id = id))
    }

    MangaContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun MangaContent(
    state: MangaState,
    onEvent: (MangaEvent) -> Unit
) {
    if (state.loading) {
        Loading(
            modifier = Modifier.fillMaxSize(),
            size = 64
        )
    } else  {
        if (state.manga != null) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = state.scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CoverField(state.manga.coverUrl)
                TitleField(state.manga)
                TagChipGroup(
                    tags = state.manga.tags,
                    onTagClick = { },
                    contentRating = if (state.manga.contentRating == ContentRating.SAFE) null else state.manga.contentRating,
                    onContentRatingClick = { },
                )
                StatusField(state.manga)
                Text(
                    text = state.manga.description,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    maxLines = if (state.descriptionIsCollapsed) 10 else Int.MAX_VALUE,
                    modifier = Modifier
                        .animateContentSize()
                        .clickable(onClick = {onEvent(MangaEvent.ToggleDescription)})
                )
            }
        } else {
            Text(text = "error fetching data")
        }
    }
}