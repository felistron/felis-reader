package com.felisreader.manga.presentation.manga_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.presentation.Loading
import com.felisreader.core.presentation.TagChipGroup
import com.felisreader.manga.presentation.manga_info.components.*

@Composable
fun MangaScreen(
    viewModel: MangaViewModel = hiltViewModel(),
    mangaId: String,
    navigateToFeed: () -> Unit
) {
    LaunchedEffect(true) {
        if (viewModel.state.value.loading) {
            viewModel.onEvent(MangaEvent.LoadManga(mangaId = mangaId))
        }
    }

    MangaContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent,
        navigateToFeed = navigateToFeed
    )
}

@Composable
fun MangaContent(
    state: MangaState,
    onEvent: (MangaEvent) -> Unit,
    navigateToFeed: () -> Unit
) {
    when {
        state.loading && state.manga == null -> {
            Loading(modifier = Modifier.fillMaxSize(), size = 64)
        }

        !state.loading && state.manga != null -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = state.scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                CoverField(state.manga.coverUrl)
                TitleField(state.manga)
                TagChipGroup(
                    tags = state.manga.tags,
                    onTagClick = { },
                    contentRating = if (state.manga.contentRating == ContentRating.SAFE) null else state.manga.contentRating,
                    onContentRatingClick = { },
                    demography = state.manga.demography,
                    onDemographyClick = { }
                )
                StatusField(state.manga)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = navigateToFeed) {
                        Text(text = stringResource(id = R.string.see_chapters))
                    }
                }
                InfoTabs(
                    manga = state.manga,
                    onEvent = onEvent,
                    isDescriptionCollapsed = state.isDescriptionCollapsed
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        else -> {
            // TODO: Handle 4xx & 5xx errors
        }
    }
}
