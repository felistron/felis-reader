package com.felisreader.library.presentation.reading_history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.felisreader.R
import com.felisreader.chapter.domain.model.api.Chapter
import com.felisreader.core.util.ChapterUtil
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.presentation.manga_search.components.CoverThumbnail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingHistoryCard(
    modifier: Modifier = Modifier,
    chapter: Chapter,
    manga: Manga,
    onClick: (chapterId: String) -> Unit,
    onDelete: (chapterId: String) -> Unit,
) {
    val direction = DismissDirection.EndToStart

    val dismissState = rememberDismissState(
        positionalThreshold = { it * 0.5f },
        confirmValueChange = {
            when(it) {
                DismissValue.DismissedToStart -> onDelete(chapter.id)
                else -> return@rememberDismissState false
            }
            return@rememberDismissState true
        }
    )

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .height(150.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = { onClick(chapter.id) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CoverThumbnail(coverUrl = manga.coverUrlSave)
                    Column(
                        Modifier.fillMaxWidth().padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = manga.title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        chapter.attributes.title?.ifBlank{ null }?.let {
                            Text(text = chapter.attributes.title)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                painter = painterResource(
                                    id = ChapterUtil.countryFlagFromLangCode(
                                        chapter.attributes.translatedLanguage.toString()
                                    )
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            if (chapter.attributes.volume != null) {
                                Text("${stringResource(id = R.string.volume_short)} ${chapter.attributes.volume}")
                            }
                            Text("${stringResource(id = R.string.chapter_short)} ${chapter.attributes.chapter}")
                        }
                    }
                }
            }
        },
        directions = setOf(direction)
    )
}