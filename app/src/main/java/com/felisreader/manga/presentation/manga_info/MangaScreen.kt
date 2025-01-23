package com.felisreader.manga.presentation.manga_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.components.TagChipGroup
import com.felisreader.manga.presentation.manga_info.components.*

@Composable
fun MangaScreen(
    viewModel: MangaViewModel = hiltViewModel(),
    mangaId: String,
    navigateToFeed: () -> Unit,
    searchByTag: (tagId: String) -> Unit,
    navigateToAuthor: (authorId: String) -> Unit,
    setTopBarTitle: (@Composable () -> Unit) -> Unit,
) {
    setTopBarTitle { }

    LaunchedEffect(true) {
        if (viewModel.state.value.loading) {
            viewModel.onEvent(MangaEvent.LoadManga(mangaId = mangaId))
        }
    }

    MangaContent(
        state = viewModel.state.value,
        onEvent = viewModel::onEvent,
        navigateToFeed = navigateToFeed,
        searchByTag = searchByTag,
        navigateToAuthor = navigateToAuthor,
        setTopBarTitle = setTopBarTitle,
    )
}

@Composable
fun MangaContent(
    state: MangaState,
    onEvent: (MangaEvent) -> Unit,
    navigateToFeed: () -> Unit,
    searchByTag: (tagId: String) -> Unit,
    navigateToAuthor: (authorId: String) -> Unit,
    setTopBarTitle: (@Composable () -> Unit) -> Unit,
) {
    when {
        state.loading && state.manga == null -> {
            Loading(modifier = Modifier.fillMaxSize(), size = 64)
        }

        !state.loading && state.manga != null -> {
            setTopBarTitle {
                Text(
                    text = state.manga.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = state.scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                CoverField(state.manga.coverUrl)
                TitleField(
                    manga = state.manga,
                    onAuthorClick = { authorId ->
                        navigateToAuthor(authorId)
                    }
                )
                TagChipGroup(
                    tags = state.manga.tags,
                    onTagClick = { tagId ->
                        searchByTag(tagId)
                    },
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
