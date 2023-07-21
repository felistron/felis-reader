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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.felisreader.chapter.domain.model.Chapter
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

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
    val publishAt = LocalDateTime.parse(chapter.attributes.publishAt, formatter)

    val publishTime: Long = publishAt.toInstant(ZoneOffset.UTC).toEpochMilli()
    val currentTime: Long = System.currentTimeMillis()

    val since: String = DateUtils.getRelativeTimeSpanString(publishTime, currentTime, DateUtils.MINUTE_IN_MILLIS).toString()
    
    Card(
        modifier = modifier
            .clickable {
                selected = !selected
            }
    ) {
        Column {
            Row(
                Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .weight(0.2f),
                    painter = painterResource(
                        id = ChapterUtil.countryFlagFromLangCode(chapter.attributes.translatedLanguage)
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = chapter.attributes.title ?: "Chapter ${chapter.attributes.chapter}",
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
                    tonalElevation = 5.dp
                ) {
                    Column(
                        Modifier
                            .padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        AssistChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = ChapterUtil.getScanlationGroups(chapter).firstOrNull() ?: "No group")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Person, contentDescription = "Person Icon")
                            },
                            border = null
                        )
                        AssistChip(
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = ChapterUtil.getUploaders(chapter).firstOrNull() ?: "No user")
                            },
                            leadingIcon = {
                                Icon(imageVector = Icons.Outlined.Group, contentDescription = "Person Icon")
                            },
                            border = null
                        )
                        AssistChip(
                            enabled = false,
                            onClick = { /*TODO*/ },
                            label = {
                                Text(text = since)
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
                                onClick = { onButtonClick(chapter.id.toString()) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Book,
                                    contentDescription = "Book Icon"
                                )
                                Text(text = "Read now")
                            }
                        }
                    }
                }
            }
        }
    }
}