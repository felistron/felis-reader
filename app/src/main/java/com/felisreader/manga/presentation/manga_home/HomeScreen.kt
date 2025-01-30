package com.felisreader.manga.presentation.manga_home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.presentation.components.MangaCarrousel
import com.felisreader.manga.presentation.manga_search.components.WelcomeDialog
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToManga: (mangaId: String) -> Unit,
) {
    LaunchedEffect(true) {
        if (viewModel.state.value.popularManga == null) {
            viewModel.onEvent(HomeEvent.LoadManga())
        }
    }

    AnimatedVisibility(viewModel.state.value.welcomeDialogVisible) {
        WelcomeDialog(
            onClose = { showAgain ->
                viewModel.onEvent(HomeEvent.CloseWelcomeDialog(showAgain))
            }
        )
    }

    HomeContent(
        state = viewModel.state.value,
        navigateToManga = navigateToManga,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    navigateToManga: (mangaId: String) -> Unit,
) {
    val scrollState by remember { mutableStateOf(ScrollState(initial = 0)) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            onEvent(HomeEvent.LoadManga {
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
        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            AnimatedVisibility(state.loggedUser != null) {
                if (state.loggedUser != null) {
                    val greeting: AnnotatedString = buildAnnotatedString {
                        append(stringResource(id = R.string.ohayo))
                        append(" ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(state.loggedUser.attributes.username)
                        }
                        append("!")
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = greeting,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.ui_home_popular),
                style = MaterialTheme.typography.headlineMedium
            )
            if (state.popularManga == null) {
                Loading(modifier = Modifier.fillMaxWidth(), size = 32)
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
                Loading(modifier = Modifier.fillMaxWidth(), size = 32)
            } else {
                MangaCarrousel(
                    mangas = state.recentManga.data,
                    navigateToManga = { mangaId -> navigateToManga(mangaId) }
                )
            }
        }
    }
}