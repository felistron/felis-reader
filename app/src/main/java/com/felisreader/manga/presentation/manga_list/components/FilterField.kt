package com.felisreader.manga.presentation.manga_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.felisreader.core.domain.ContentRating
import com.felisreader.core.domain.MangaListRequest
import com.felisreader.core.domain.PublicationDemographic
import com.felisreader.core.domain.Status
import com.felisreader.manga.presentation.manga_list.MangaListEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterField(
    expanded: Boolean,
    onEvent: (MangaListEvent) -> Unit,
    query: MangaListRequest
) {
    val queryState = remember { mutableStateOf(query.copy()) }

    Surface(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        tonalElevation = if (expanded) 5.dp else 0.dp
    ) {
        Column {
            AssistChip(
                onClick = { onEvent(MangaListEvent.ToggleFilter) },
                label = {
                    Text(text = "Filter")
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.FilterList,
                        contentDescription = "Outlined filter list icon"
                    )
                },
                border = null,
                modifier = Modifier.padding(10.dp)
            )
            AnimatedVisibility (expanded) {
                FilterList(onEvent, queryState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChip(
    onClick: () -> Unit,
    label: String,
    selected: Boolean = false
) {
    InputChip(
        onClick = onClick,
        label = {
            Text(text = label)
        },
        trailingIcon = {
            if (selected) {
                Icon(
                    Icons.Outlined.Close,
                    contentDescription = "Cancel icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        border = InputChipDefaults.inputChipBorder(
            borderWidth = 1.dp,
            borderColor = if (selected) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.outline
        ),
        colors = InputChipDefaults.inputChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
            labelColor = if (selected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface,
        )
    )
}

@Composable
fun FilterList(
    onEvent: (MangaListEvent) -> Unit,
    queryState: MutableState<MangaListRequest>
) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TitleInputField(queryState)
        ContentRatingField(queryState)
        PublicationStatusField(queryState)
        MagazineDemographicField(queryState)
        Button(
            onClick = {
                onEvent(MangaListEvent.ToggleFilter)
                onEvent(MangaListEvent.ApplyFilter(queryState.value))
            },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Apply changes")
        }
    }
}

@Composable
fun TitleInputField(
    queryState: MutableState<MangaListRequest>
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = queryState.value.title ?: "",
        onValueChange = {
            queryState.value = queryState.value.copy(
                title = it
            )
        },
        placeholder = {
            Text(text = "Title")
        },
        maxLines = 1,
        singleLine = true,
        trailingIcon = {
            if (!queryState.value.title.isNullOrEmpty()) {
                IconButton(onClick = { queryState.value = queryState.value.copy(title = null) }) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close icon")
                }
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MagazineDemographicField(
    queryState: MutableState<MangaListRequest>
) {
    val anySelected: Boolean = queryState.value.publicationDemographic.isNullOrEmpty()
    val shounenSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(PublicationDemographic.SHOUNEN)
    val shoujoSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(PublicationDemographic.SHOUJO)
    val seinenSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(PublicationDemographic.SEINEN)
    val joseiSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(PublicationDemographic.JOSEI)

    Column {
        Text(
            text = "Magazine demographic",
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top,
        ) {
            FilterChip(
                onClick = {
                    queryState.value = queryState.value.copy(
                        publicationDemographic = null
                    )
                },
                label = "Any",
                selected = anySelected
            )
            FilterChip(
                onClick = {
                    if (!shounenSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(PublicationDemographic.SHOUNEN)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(PublicationDemographic.SHOUNEN)
                        )
                    }
                },
                label = "Shounen",
                selected = shounenSelected
            )
            FilterChip(
                onClick = {
                    if (!shoujoSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(PublicationDemographic.SHOUJO)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(PublicationDemographic.SHOUJO)
                        )
                    }
                },
                label = "Shoujo",
                selected = shoujoSelected
            )
            FilterChip(
                onClick = {
                    if (!seinenSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(PublicationDemographic.SEINEN)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(PublicationDemographic.SEINEN)
                        )
                    }
                },
                label = "Seinen",
                selected = seinenSelected
            )
            FilterChip(
                onClick = {
                    if (!joseiSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(PublicationDemographic.JOSEI)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(PublicationDemographic.JOSEI)
                        )
                    }
                },
                label = "Josei",
                selected = joseiSelected
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PublicationStatusField(
    queryState: MutableState<MangaListRequest>
) {
    val anySelected: Boolean = queryState.value.status.isNullOrEmpty()
    val ongoingSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.ONGOING)
    val completedSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.COMPLETED)
    val cancelledSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.CANCELLED)
    val hiatusSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.HIATUS)

    Column {
        Text(
            text = "Publication status",
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            FilterChip(
                onClick = {
                    queryState.value = queryState.value.copy(
                        status = null
                    )
                },
                label = "Any",
                selected = anySelected
            )
            FilterChip(
                onClick = {
                    if (!ongoingSelected) {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status.plusOrCreate(Status.ONGOING)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status?.minus(Status.ONGOING)
                        )
                    }
                },
                label = "Ongoing",
                selected = ongoingSelected
            )
            FilterChip(
                onClick = {
                    if (!completedSelected) {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status.plusOrCreate(Status.COMPLETED)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status?.minus(Status.COMPLETED)
                        )
                    }
                },
                label = "Completed",
                selected = completedSelected
            )
            FilterChip(
                onClick = {
                    if (!cancelledSelected) {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status.plusOrCreate(Status.CANCELLED)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status?.minus(Status.CANCELLED)
                        )
                    }
                },
                label = "Cancelled",
                selected = cancelledSelected
            )
            FilterChip(
                onClick = {
                    if (!hiatusSelected) {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status.plusOrCreate(Status.HIATUS)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            status = queryState.value.status?.minus(Status.HIATUS)
                        )
                    }
                },
                label = "Hiatus",
                selected = hiatusSelected
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentRatingField(
    queryState: MutableState<MangaListRequest>
) {
    val anySelected: Boolean = queryState.value.contentRating.isNullOrEmpty()
    val safeSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.SAFE)
    val suggestiveSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.SUGGESTIVE)
    val eroticaSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.EROTICA)
    val pornographicSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.PORNOGRAPHIC)

    Column {
        Text(
            text = "Content rating",
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            FilterChip(
                onClick = {
                    queryState.value = queryState.value.copy(
                        contentRating = null
                    )
                },
                label = "Any",
                selected = anySelected
            )
            FilterChip(
                onClick = {
                    if (!safeSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(ContentRating.SAFE)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.SAFE)
                        )
                    }
                },
                label = "Safe",
                selected = safeSelected
            )
            FilterChip(
                onClick = {
                    if (!suggestiveSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(ContentRating.SUGGESTIVE)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.SUGGESTIVE)
                        )
                    }
                },
                label = "Suggestive",
                selected = suggestiveSelected
            )
            FilterChip(
                onClick = {
                    if (!eroticaSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(ContentRating.EROTICA)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.EROTICA)
                        )
                    }
                },
                label = "Erotica",
                selected = eroticaSelected
            )
            FilterChip(
                onClick = {
                    if (!pornographicSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(ContentRating.PORNOGRAPHIC)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.PORNOGRAPHIC)
                        )
                    }
                },
                label = "Pornographic",
                selected = pornographicSelected
            )
        }
    }
}

private fun <T> List<T>?.plusOrCreate(element: T): List<T> {
    val list: List<T> = this ?: emptyList()
    return list.plus(element)
}

private fun <T> List<T>?.isNotNullAndHas(value: T): Boolean {
    return !this.isNullOrEmpty() && this.contains(value)
}