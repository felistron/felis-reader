package com.felisreader.manga.presentation.manga_search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.felisreader.R
import com.felisreader.core.util.AppUtil
import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.model.api.PublicationDemographic
import com.felisreader.manga.domain.model.api.Status
import com.felisreader.manga.domain.model.api.TagEntity
import com.felisreader.manga.presentation.manga_search.SearchEvent

@Composable
fun FilterField(
    expanded: Boolean,
    onEvent: (SearchEvent) -> Unit,
    query: MangaListQuery,
    supportedTags: List<TagEntity>,
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
                onClick = { onEvent(SearchEvent.ToggleFilter) },
                label = {
                    Text(text = stringResource(id = R.string.filter))
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.FilterList,
                        contentDescription = "Outlined filter list icon"
                    )
                },
                border = null,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            AnimatedVisibility (expanded) {
                FilterList(onEvent, queryState, supportedTags)
            }
        }
    }
}

@Composable
fun FilterList(
    onEvent: (SearchEvent) -> Unit,
    queryState: MutableState<MangaListQuery>,
    supportedTags: List<TagEntity>
) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TagField(queryState, supportedTags)
        Divider()
        ContentRatingField(queryState)
        PublicationStatusField(queryState)
        MagazineDemographicField(queryState)
        Button(
            onClick = {
                onEvent(SearchEvent.ToggleFilter)
                onEvent(SearchEvent.ApplyFilter(queryState.value))
            },
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = stringResource(id = R.string.filter_apply))
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
                )
            }
        },
        border = null,
        colors = InputChipDefaults.inputChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onSecondary,
        ),
        selected = selected
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TagField(
    queryState: MutableState<MangaListQuery>,
    supportedTags: List<TagEntity>
) {
    var tagText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val selectedTags: List<TagEntity> = queryState.value.includedTags?.mapNotNull {
        supportedTags.firstOrNull { tag -> tag.id.toString() == it }
    } ?: emptyList()

    // Tags to be displayed in the dropdown menu
    val displayTags = supportedTags
        .filter {
            // Exclude tags that are already selected
            !(queryState.value.includedTags?.contains(it.id.toString()) ?: false)
        }
        .sortedBy {
            // Sort tags by its levenshtein distance to the input
            AppUtil.levenshteinDistance(tagText, it.attributes.name["en"] ?: "")
        }
        .take(5)

    Column {
        Text(
            text = stringResource(id = R.string.filter_tags),
            style = MaterialTheme.typography.titleMedium
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                value = tagText,
                onValueChange = {
                    tagText = it
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.filter_tag_placeholder))
                },
                singleLine = true,
            )
            DropdownMenu(
                modifier = Modifier
                    .exposedDropdownSize(true),
                properties = PopupProperties(focusable = false),
                expanded = tagText.isNotBlank(),
                onDismissRequest = { expanded = false }
            ) {
                displayTags.forEach { tag ->
                    DropdownMenuItem(
                        onClick = {
                            queryState.value = queryState.value.copy(
                                includedTags = queryState.value.includedTags.plusOrCreate(tag.id.toString())
                            )
                            tagText = ""
                            expanded = false
                        },
                        text = {
                            Text(text = tag.attributes.name["en"] ?: "")
                        }
                    )
                }
            }
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            selectedTags.take(5).forEach { tag ->
                FilterChip(
                    onClick = {
                        queryState.value = queryState.value.copy(
                            includedTags = queryState.value.includedTags?.minus(tag.id.toString())
                        )
                    },
                    label = tag.attributes.name["en"] ?: "",
                    selected = true
                )
            }
            if (selectedTags.size > 5) {
                AssistChip(
                    enabled = false,
                    onClick = {  },
                    label = {
                        Text(
                            text = "+${selectedTags.size - 5}",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    border = null,
                    colors = AssistChipDefaults.assistChipColors(
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MagazineDemographicField(
    queryState: MutableState<MangaListQuery>
) {
    val anySelected: Boolean = queryState.value.publicationDemographic.isNullOrEmpty()
    val shounenSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(
        PublicationDemographic.SHOUNEN)
    val shoujoSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(
        PublicationDemographic.SHOUJO)
    val seinenSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(
        PublicationDemographic.SEINEN)
    val joseiSelected: Boolean = queryState.value.publicationDemographic.isNotNullAndHas(
        PublicationDemographic.JOSEI)

    Column {
        Text(
            text = stringResource(id = R.string.demography),
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            FilterChip(
                onClick = {
                    queryState.value = queryState.value.copy(
                        publicationDemographic = null
                    )
                },
                label = stringResource(id = R.string.filter_any),
                selected = anySelected
            )
            FilterChip(
                onClick = {
                    if (!shounenSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(
                                PublicationDemographic.SHOUNEN)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(
                                PublicationDemographic.SHOUNEN)
                        )
                    }
                },
                label = stringResource(id = R.string.demography_shounen),
                selected = shounenSelected
            )
            FilterChip(
                onClick = {
                    if (!shoujoSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(
                                PublicationDemographic.SHOUJO)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(
                                PublicationDemographic.SHOUJO)
                        )
                    }
                },
                label = stringResource(id = R.string.demography_shoujo),
                selected = shoujoSelected
            )
            FilterChip(
                onClick = {
                    if (!seinenSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(
                                PublicationDemographic.SEINEN)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(
                                PublicationDemographic.SEINEN)
                        )
                    }
                },
                label = stringResource(id = R.string.demography_seinen),
                selected = seinenSelected
            )
            FilterChip(
                onClick = {
                    if (!joseiSelected) {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic.plusOrCreate(
                                PublicationDemographic.JOSEI)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            publicationDemographic = queryState.value.publicationDemographic?.minus(
                                PublicationDemographic.JOSEI)
                        )
                    }
                },
                label = stringResource(id = R.string.demography_josei),
                selected = joseiSelected
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PublicationStatusField(
    queryState: MutableState<MangaListQuery>
) {
    val anySelected: Boolean = queryState.value.status.isNullOrEmpty()
    val ongoingSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.ONGOING)
    val completedSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.COMPLETED)
    val cancelledSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.CANCELLED)
    val hiatusSelected: Boolean = queryState.value.status.isNotNullAndHas(Status.HIATUS)

    Column {
        Text(
            text = stringResource(id = R.string.status),
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            FilterChip(
                onClick = {
                    queryState.value = queryState.value.copy(
                        status = null
                    )
                },
                label = stringResource(id = R.string.filter_any),
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
                label = stringResource(id = R.string.status_ongoing),
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
                label = stringResource(id = R.string.status_completed),
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
                label = stringResource(id = R.string.status_cancelled),
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
                label = stringResource(id = R.string.status_hiatus),
                selected = hiatusSelected
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentRatingField(
    queryState: MutableState<MangaListQuery>
) {
    val anySelected: Boolean = queryState.value.contentRating.isNullOrEmpty()
    val safeSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.SAFE)
    val suggestiveSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.SUGGESTIVE)
    val eroticaSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.EROTICA)
    val pornographicSelected: Boolean = queryState.value.contentRating.isNotNullAndHas(ContentRating.PORNOGRAPHIC)

    Column {
        Text(
            text = stringResource(id = R.string.content_rating),
            style = MaterialTheme.typography.titleMedium
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            FilterChip(
                onClick = {
                    queryState.value = queryState.value.copy(
                        contentRating = null
                    )
                },
                label = stringResource(id = R.string.filter_any),
                selected = anySelected
            )
            FilterChip(
                onClick = {
                    if (!safeSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(
                                ContentRating.SAFE)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.SAFE)
                        )
                    }
                },
                label = stringResource(id = R.string.content_rating_safe),
                selected = safeSelected
            )
            FilterChip(
                onClick = {
                    if (!suggestiveSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(
                                ContentRating.SUGGESTIVE)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.SUGGESTIVE)
                        )
                    }
                },
                label = stringResource(id = R.string.content_rating_suggestive),
                selected = suggestiveSelected
            )
            FilterChip(
                onClick = {
                    if (!eroticaSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(
                                ContentRating.EROTICA)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.EROTICA)
                        )
                    }
                },
                label = stringResource(id = R.string.content_rating_erotica),
                selected = eroticaSelected
            )
            FilterChip(
                onClick = {
                    if (!pornographicSelected) {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating.plusOrCreate(
                                ContentRating.PORNOGRAPHIC)
                        )
                    } else {
                        queryState.value = queryState.value.copy(
                            contentRating = queryState.value.contentRating?.minus(ContentRating.PORNOGRAPHIC)
                        )
                    }
                },
                label = stringResource(id = R.string.content_rating_pornographic),
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