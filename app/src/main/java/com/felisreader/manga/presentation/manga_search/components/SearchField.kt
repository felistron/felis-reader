package com.felisreader.manga.presentation.manga_search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.felisreader.R
import com.felisreader.manga.presentation.manga_search.SearchEvent
import com.felisreader.manga.presentation.manga_search.SearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    val title: MutableState<String> = remember { mutableStateOf(state.query.title ?: "") }
    val focusRequester: FocusRequester = remember { FocusRequester() }

    SearchBar(
        modifier = modifier
            .padding(horizontal = if (state.searchBarActive) 0.dp else 8.dp)
            .animateContentSize()
            .fillMaxWidth()
            .focusRequester(focusRequester),
        query = title.value,
        onQueryChange = { title.value = it },
        onSearch = {
            if (it.isNotBlank()) {
                onEvent(
                    SearchEvent.AddHistoryItem(it, System.currentTimeMillis())
                )
            }
            onEvent(SearchEvent.SearchBarActive(false))
            onEvent(SearchEvent.ApplyFilter(
                query = state.query.copy(
                    title = title.value
                )
            ))
        },
        active = state.searchBarActive,
        onActiveChange = {
            onEvent(SearchEvent.SearchBarActive(it))
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_by_title))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = title.value.isNotEmpty()
            ) {
                IconButton(onClick = {
                    title.value = ""
                    onEvent(SearchEvent.SearchBarActive(true))
                    focusRequester.requestFocus()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        },
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        LazyColumn {
            items(state.searchHistory.filter {
                if (title.value.isBlank()) return@filter true
                else return@filter it.content.contains(title.value, ignoreCase = true)
            }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            title.value = it.content
                            onEvent(SearchEvent.AddHistoryItem(
                                it.content, System.currentTimeMillis()
                            ))
                            onEvent(SearchEvent.SearchBarActive(false))
                            onEvent(SearchEvent.ApplyFilter(
                                query = state.query.copy(
                                    title = title.value
                                )
                            ))
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = "History Icon"
                        )
                        Text(text = it.content)
                    }
                    IconButton(onClick = {
                        onEvent(
                            SearchEvent.DeleteHistoryItem(it)
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close icon"
                        )
                    }
                }
            }
        }
    }
}