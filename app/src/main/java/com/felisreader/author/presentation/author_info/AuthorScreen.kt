package com.felisreader.author.presentation.author_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
    setTopBarTitle: (@Composable () -> Unit) -> Unit,
) {
    setTopBarTitle { }

    LaunchedEffect(true) {
        if (viewModel.state.value.loading) {
            viewModel.onEvent(AuthorEvent.LoadAuthor(authorId))
        }
    }

    AuthorContent(
        state = viewModel.state.value,
        navigateToManga = navigateToManga,
        setTopBarTitle = setTopBarTitle,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AuthorContent(
    state: AuthorState,
    navigateToManga: (mangaId: String) -> Unit,
    setTopBarTitle: (@Composable () -> Unit) -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    when {
        state.loading && state.author == null && state.titles == null -> {
            Loading(modifier = Modifier.fillMaxSize(), size = 64)
        }

        !state.loading && state.author != null && state.titles != null -> {
            setTopBarTitle {
                Text(
                    text = state.author.attributes.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

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
                            text = stringResource(id = R.string.author_social),
                            fontSize = 6.em
                        )
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalArrangement = Arrangement.Top,
                            maxItemsInEachRow = 3
                        ) {
                            if (state.author.attributes.twitter != null) {
                                AssistChip(
                                    label = {
                                        Text(
                                            text = "X / Twitter",
                                            fontSize = 4.em
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.x_twitter_icon),
                                            contentDescription = "X icon",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(state.author.attributes.twitter)
                                    }
                                )
                            }
                            if (state.author.attributes.pixiv != null) {
                                AssistChip(
                                    label = {
                                        Text(
                                            text = "Pixiv",
                                            fontSize = 4.em
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.pixiv_icon),
                                            contentDescription = "Pixiv icon",
                                            tint = Color(0, 150, 219)
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(state.author.attributes.pixiv)
                                    }
                                )
                            }
                            if (state.author.attributes.nicoVideo != null) {
                                AssistChip(
                                    label = {
                                        Text(
                                            text = "Nico video",
                                            fontSize = 4.em
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.nico_video_icon),
                                            contentDescription = "Nico video icon",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(state.author.attributes.nicoVideo)
                                    }
                                )
                            }
                            if (state.author.attributes.tumblr != null) {
                                AssistChip(
                                    label = {
                                        Text(
                                            text = "Tumblr",
                                            fontSize = 4.em
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.tumblr_icon),
                                            contentDescription = "Tumblr icon",
                                            tint = Color(42, 68, 95)
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(state.author.attributes.tumblr)
                                    }
                                )
                            }
                            if (state.author.attributes.youtube != null) {
                                AssistChip(
                                    label = {
                                        Text(
                                            text = "Youtube",
                                            fontSize = 4.em
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.youtube_icon),
                                            contentDescription = "YouTube icon",
                                            tint = Color(219, 37, 50)
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(state.author.attributes.youtube)
                                    }
                                )
                            }
                            if (state.author.attributes.website != null) {
                                AssistChip(
                                    label = {
                                        Text(
                                            text = "Website",
                                            fontSize = 4.em
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            imageVector = Icons.Outlined.Language,
                                            contentDescription = "Internet logo",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(state.author.attributes.website)
                                    }
                                )
                            }
                        }
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
