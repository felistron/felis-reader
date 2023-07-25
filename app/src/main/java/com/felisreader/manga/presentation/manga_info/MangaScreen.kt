package com.felisreader.manga.presentation.manga_info

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.presentation.TagChipGroup
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.presentation.manga_info.components.*

@Composable
fun MangaScreen(
    viewModel: MangaViewModel = hiltViewModel(),
    mangaId: String,
    navigateToFeed: (mangaId: String) -> Unit
) {
    LaunchedEffect(true) {
        if (viewModel.state.value.loading) {
            viewModel.onEvent(MangaEvent.LoadManga(id = mangaId))
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
    navigateToFeed: (mangaId: String) -> Unit
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
                    demography = state.manga.demography,
                    onDemographyClick = { }
                )
                StatusField(state.manga)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { navigateToFeed(state.manga.id) }) {
                        Text(text = "See chapters")
                    }
                }
                InfoTabs(
                    manga = state.manga,
                    onEvent = onEvent,
                    isDescriptionCollapsed = state.isDescriptionCollapsed
                )
            }
        } else {
            Text(text = "error fetching data")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoTabs(
    manga: Manga,
    onEvent: (MangaEvent) -> Unit,
    isDescriptionCollapsed: Boolean
) {
    var state by remember { mutableStateOf(0) }
    val tabs = listOf("Description", "Links")
    val uriHandler = LocalUriHandler.current

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .animateContentSize()
    ) {
        TabRow(
            selectedTabIndex = state
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = state == index,
                    onClick = { state = index }
                )
            }
        }
        when(state) {
            0-> {
                Text(
                    text = manga.description,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    maxLines = if (isDescriptionCollapsed) 10 else Int.MAX_VALUE,
                    modifier = Modifier
                        .animateContentSize()
                        .clickable(onClick = { onEvent(MangaEvent.ToggleDescription) })
                )
            }
            1 -> {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.Top,
                    maxItemsInEachRow = 2
                ) {
                    manga.links.forEach {
                        if (it != null) {
                            AssistChip(
                                label = { Text(text = it.relatedSite) },
                                onClick = {
                                    uriHandler.openUri(it.url)
                                },
                                leadingIcon = {
                                    when (it.key) {
                                        "al" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.al_icon),
                                            contentDescription = "Anime List Icon",
                                            tint = Color.Unspecified
                                        )
                                        "ap" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.ap_icon),
                                            contentDescription = "Anime Planet Icon",
                                            tint = Color.Unspecified
                                        )
                                        "bw" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.bw_icon),
                                            contentDescription = "Book Walker Icon",
                                            tint = Color.Unspecified
                                        )
                                        "mu" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.mu_icon),
                                            contentDescription = "Manga Updates Icon",
                                            tint = Color.Unspecified
                                        )
                                        "nu" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.nu_icon),
                                            contentDescription = "Novel Updates Icon",
                                            tint = Color.Unspecified
                                        )
                                        "amz" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.amz_icon),
                                            contentDescription = "Amazon Icon",
                                            tint = Color.Unspecified
                                        )
                                        "ebj" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.ebj_icon),
                                            contentDescription = "eBook Japan Icon",
                                            tint = Color.Unspecified
                                        )
                                        "mal" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.mal_icon),
                                            contentDescription = "My Anime List Icon",
                                            tint = Color.Unspecified
                                        )
                                        "cdj" -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(id = R.drawable.cdj_icon),
                                            contentDescription = "CD Japan Icon",
                                            tint = Color.Unspecified
                                        )
                                        else -> Icon(
                                            modifier = Modifier.size(24.dp),
                                            imageVector = Icons.Outlined.Language,
                                            contentDescription = "Website Icon",
                                            tint = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.size(12.dp),
                                        imageVector = Icons.Outlined.OpenInNew,
                                        contentDescription = "Open in new icon"
                                    )
                                },
                                border = null
                            )
                        }
                    }
                }
            }
        }
    }
}
