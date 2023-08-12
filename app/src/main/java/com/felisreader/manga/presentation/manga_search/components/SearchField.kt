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
    searchText: String,
    history: List<String>,
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }

    SearchBar(
        modifier = modifier
            .padding(horizontal = if (state.searchBarActive) 0.dp else 8.dp)
            .animateContentSize()
            .fillMaxWidth()
            .focusRequester(focusRequester),
        query = searchText,
        onQueryChange = { onEvent(SearchEvent.OnSearchTextChange(it)) },
        onSearch = { onEvent(SearchEvent.OnSearch(it)) },
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
                visible = searchText.isNotEmpty()
            ) {
                IconButton(onClick = {
                    onEvent(SearchEvent.OnSearchTextChange(""))
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
            items(if (state.searchBarActive) history else emptyList() ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(SearchEvent.OnSearchTextChange(it))
                            onEvent(SearchEvent.OnSearch(it))
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
                        Text(text = it)
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