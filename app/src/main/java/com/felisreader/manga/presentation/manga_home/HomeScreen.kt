package com.felisreader.manga.presentation.manga_home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.components.MangaCarrousel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToManga: (mangaId: String) -> Unit,
) {
    LaunchedEffect(true) {
        if (viewModel.state.value.popularManga == null) {
            viewModel.onEvent(HomeEvent.LoadPopular)
        }
    }

    LaunchedEffect(true) {
        if (viewModel.state.value.recentManga == null) {
            viewModel.onEvent(HomeEvent.LoadRecent)
        }
    }

    HomeContent(
        state = viewModel.state.value,
        navigateToManga = navigateToManga,
    )
}

@Composable
fun HomeContent(
    state: HomeState,
    navigateToManga: (mangaId: String) -> Unit,
) {
    val scrollState by remember { mutableStateOf(ScrollState(initial = 0)) }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.ui_home_popular),
            style = MaterialTheme.typography.headlineMedium
        )
        if (state.popularManga == null) {
            Loading(modifier = Modifier, size = 32)
        } else {
            MangaCarrousel(
                mangas = state.popularManga.data,
                navigateToManga = { mangaId -> navigateToManga(mangaId) }
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.ui_home_recent),
            style = MaterialTheme.typography.headlineMedium
        )
        if (state.recentManga == null) {
            Loading(modifier = Modifier, size = 32)
        } else {
            MangaCarrousel(
                mangas = state.recentManga.data,
                navigateToManga = { mangaId -> navigateToManga(mangaId) }
            )
        }
    }
}