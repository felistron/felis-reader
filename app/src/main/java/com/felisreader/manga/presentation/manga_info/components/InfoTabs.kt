package com.felisreader.manga.presentation.manga_info.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.felisreader.R
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.presentation.manga_info.MangaEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoTabs(
    manga: Manga,
    onEvent: (MangaEvent) -> Unit,
    isDescriptionCollapsed: Boolean
) {
    var state by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.description),
        stringResource(id = R.string.links)
    )
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
                        it?.let { linkType ->
                            AssistChip(
                                label = { Text(text = linkType.relatedSite) },
                                onClick = {
                                    uriHandler.openUri(linkType.url)
                                },
                                leadingIcon = {
                                    when (linkType.key) {
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
