package com.felisreader.author.presentation.author_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.manga_search.components.MangaCard

@Composable
fun AuthorScreen(
    viewModel: AuthorViewModel = hiltViewModel(),
    authorId: String,
    navigateToManga: (mangaId: String) -> Unit,
) {
    LaunchedEffect(true) {
        if (viewModel.state.value.loading) {
            viewModel.onEvent(AuthorEvent.LoadAuthor(authorId))
        }
    }

    AuthorContent(
        state = viewModel.state.value,
        navigateToManga = navigateToManga,
    )
}

@Composable
fun AuthorContent(
    state: AuthorState,
    navigateToManga: (mangaId: String) -> Unit,
) {
    when {
        state.loading && state.author == null && state.titles == null -> {
            Loading(modifier = Modifier.fillMaxWidth(), size = 64)
        }

        !state.loading && state.author != null && state.titles != null -> {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            text = state.author.attributes.name,
                            fontSize = 8.em
                        )
                        Text(
                            text = stringResource(id = R.string.author_biography),
                            fontSize = 6.em
                        )
                        Text(
                            text = if (state.author.attributes.biography.keys.isNotEmpty())
                            state.author.attributes.biography[state.author.attributes.biography.keys.first()] ?: ""
                            else stringResource(id = R.string.author_no_biography),
                        )
                        Text(
                            text = stringResource(id = R.string.author_titles),
                            fontSize = 6.em
                        )
                    }
                }
                items(
                    items = state.titles.data,
                    key = { it.id }
                ) { manga ->
                    MangaCard(
                        manga = manga,
                        onCardClick = { navigateToManga(manga.id) }
                    )
                }
            }
        }
    }
}
