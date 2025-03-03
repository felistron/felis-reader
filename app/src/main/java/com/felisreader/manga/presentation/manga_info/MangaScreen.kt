package com.felisreader.manga.presentation.manga_info

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.LibraryAddCheck
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.manga.presentation.components.TagChipGroup
import com.felisreader.manga.presentation.manga_info.components.CoverField
import com.felisreader.manga.presentation.manga_info.components.InfoTabs
import com.felisreader.manga.presentation.manga_info.components.RatingDialog
import com.felisreader.manga.presentation.manga_info.components.ReadingStatusDialog
import com.felisreader.manga.presentation.manga_info.components.StatusField
import com.felisreader.manga.presentation.manga_info.components.TitleField
import com.felisreader.user.presentation.signin.SignInDialog


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
    val context = LocalContext.current

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

            if (state.manga.statistics != null) {
                RatingDialog(
                    visible = state.ratingDialogVisible,
                    rating = state.manga.statistics.rating,
                    onDismiss = { onEvent(MangaEvent.SetRatingDialogVisible(false)) },
                    loggedIn = state.loggedIn,
                    onSignInClick = { onEvent(MangaEvent.SetSignInDialogVisible(true)) },
                    selectedRating = state.userRating,
                    onRatingSelected = { onEvent(MangaEvent.SubmitRating(it)) }
                )
            }

            if(state.signInDialogVisible) {
                SignInDialog(
                    onSuccess = {
                        onEvent(MangaEvent.SignInSuccess)
                        onEvent(MangaEvent.SetSignInDialogVisible(false))
                    },
                    onCancel = {
                        onEvent(MangaEvent.SetSignInDialogVisible(false))
                    },
                )
            }

            if (state.readingStatusDialogVisible) {
                ReadingStatusDialog(
                    defaultOption = state.readingStatus,
                    onDismiss = { onEvent(MangaEvent.SetReadingStatusDialogVisible(false)) },
                    onConfirm = { onEvent(MangaEvent.SubmitReadingStatus(it)) }
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = state.scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CoverField(
                    modifier = Modifier.fillMaxWidth(),
                    coverUrl = state.manga.coverUrl,
                )
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
                StatusField(
                    manga = state.manga,
                    onRatingClick = {
                        onEvent(MangaEvent.SetRatingDialogVisible(true))
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    Button(onClick = navigateToFeed) {
                        Text(text = stringResource(id = R.string.see_chapters))
                    }
                    IconButton(
                        onClick = { onEvent(MangaEvent.SetReadingStatusDialogVisible(true)) },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(),
                    ) {
                        if (state.readingStatus == null) {
                            Icon(Icons.Default.LibraryAdd, contentDescription = null)
                        } else {
                            Icon(Icons.Default.LibraryAddCheck, contentDescription = null)
                        }
                    }
                    IconButton(
                        onClick = {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "https://mangadex.org/title/${state.manga.id}")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            startActivity(context, shareIntent, null)
                        },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(),
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null)
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
