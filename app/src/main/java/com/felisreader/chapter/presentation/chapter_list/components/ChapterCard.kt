package com.felisreader.chapter.presentation.chapter_list.components

import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChapterCard(
    modifier: Modifier = Modifier,
    chapter: Chapter,
    onButtonClick: (chapterId: String) -> Unit
) {
    var selected by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .clickable {
                selected = !selected
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation((if (selected) 10 else 1).dp),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column {
            Row(
                Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .weight(0.1f),
                    painter = painterResource(
                        id = ChapterUtil.countryFlagFromLangCode(chapter.attributes.translatedLanguage.toString())
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = if (chapter.attributes.title.isNullOrBlank()) "${stringResource(id = R.string.chapter)} ${chapter.attributes.chapter}" else chapter.attributes.title,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                if (selected) {
                    Icon(
                        modifier = Modifier.weight(0.1f),
                        imageVector = Icons.Outlined.ArrowDropUp,
                        contentDescription = "Arrow drop up Icon"
                    )
                } else {
                    Icon(
                        modifier = Modifier.weight(0.1f),
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = "Arrow drop down Icon"
                    )
                }
            }
            AnimatedVisibility(selected) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    tonalElevation = 2.dp
                ) {
                    Column(
                        Modifier
                            .padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        AssistChip(
                            onClick = { /*TODO: See Scanlation group info */ },
                            label = {
                                Text(
                                    text = ChapterUtil.getScanlationGroups(chapter).firstOrNull()
                                        ?: stringResource(id = R.string.no_group)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Group,
                                    contentDescription = "Group Icon"
                                )
                            },
                            border = null
                        )
                        AssistChip(
                            onClick = { /*TODO: See Uploader info*/ },
                            label = {
                                Text(
                                    text = ChapterUtil.getUploaders(chapter).firstOrNull()
                                        ?: stringResource(id = R.string.no_user)
                                )
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Person, contentDescription = "Person Icon")
                            },
                            border = null
                        )
                        AssistChip(
                            enabled = false,
                            onClick = { },
                            label = {
                                Text(text = getSinceText(chapter.attributes.publishAt))
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Timer, contentDescription = "Clock Icon")
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                                disabledLeadingIconContentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = null
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { onButtonClick(chapter.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Book,
                                    contentDescription = "Book Icon"
                                )
                                Text(text = stringResource(id = R.string.read_now))
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getSinceText(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
    val publishAt = LocalDateTime.parse(dateTime, formatter)

    val publishTime: Long = publishAt.toInstant(ZoneOffset.UTC).toEpochMilli()
    val currentTime: Long = System.currentTimeMillis()

    return DateUtils.getRelativeTimeSpanString(publishTime, currentTime, DateUtils.MINUTE_IN_MILLIS)
        .toString()
}