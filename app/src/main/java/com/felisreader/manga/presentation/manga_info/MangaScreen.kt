package com.felisreader.manga.presentation.manga_info

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.core.domain.model.ContentRating
import com.felisreader.core.presentation.TagChipGroup
import com.felisreader.manga.domain.model.Manga
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
                    horizontalArrangement = Arrangement.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    manga.links.forEach {
                        if (it != null) {
                            AssistChip(
                                modifier = Modifier.padding(5.dp),
                                label = { Text(text = it.relatedSite) },
                                onClick = {
                                    uriHandler.openUri(it.url)
                                },
                                trailingIcon = {
                                    Icon(
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
