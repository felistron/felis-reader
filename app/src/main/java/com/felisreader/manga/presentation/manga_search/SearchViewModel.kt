package com.felisreader.manga.presentation.manga_search

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.MangaListQuery
import com.felisreader.core.domain.use_case.HistoryUseCases
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.MangaList
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases,
    private val historyUseCases: HistoryUseCases
): ViewModel() {
    private val _state: MutableState<SearchState> = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    init {
        viewModelScope.launch {
            val mangaList: MangaList? = getMangaList(_state.value.query)

            if (mangaList != null) {

                if (mangaList.limit == mangaList.total) {
                    _state.value = _state.value.copy(
                        canLoadMore = false
                    )
                }

                _state.value = _state.value.copy(
                    mangaList = mangaList
                )
            }

            historyUseCases.getHistory().collect {
                _state.value = _state.value.copy(
                    searchHistory = it
                )
            }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.LoadMore -> {
                viewModelScope.launch {
                    loadMore()
                }
            }
            is SearchEvent.ToggleFilter -> {
                _state.value = _state.value.copy(
                    expandedFilter = !_state.value.expandedFilter
                )
            }
            is SearchEvent.ApplyFilter -> {
                _state.value = _state.value.copy(
                    query = _state.value.query.copy(
                        contentRating = event.query.contentRating,
                        publicationDemographic = event.query.publicationDemographic,
                        status = event.query.status,
                        title = if (event.query.title.isNullOrBlank()) null else event.query.title,
                        offset = 0
                    ),
                    mangaList = null,
                    lazyListState = LazyListState(),
                    canLoadMore = true
                )

                viewModelScope.launch {
                    val mangaList: MangaList? = getMangaList(_state.value.query)

                    if (mangaList != null) {

                        if (mangaList.limit == mangaList.total) {
                            _state.value = _state.value.copy(
                                canLoadMore = false
                            )
                        }

                        _state.value = _state.value.copy(
                            mangaList = mangaList
                        )
                    }
                }
            }
            is SearchEvent.SearchBarActive -> {
                _state.value = _state.value.copy(
                    searchBarActive = event.active
                )
            }
            is SearchEvent.AddHistoryItem -> {
                viewModelScope.launch {
                    historyUseCases.addItem(event.content, event.timestamp)

                    historyUseCases.getHistory().collect {
                        _state.value = _state.value.copy(
                            searchHistory = it
                        )
                    }
                }
            }
            is SearchEvent.DeleteHistoryItem -> {
                viewModelScope.launch {
                    historyUseCases.deleteItem(event.item)

                    historyUseCases.getHistory().collect {
                        _state.value = _state.value.copy(
                            searchHistory = it
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadMore() {
        _state.value = _state.value.copy(
            query = _state.value.query.copy(
                offset = _state.value.query.offset?.plus(_state.value.query.limit!!),
            )
        )

        val mangaList: MangaList? = getMangaList(_state.value.query)

        if (mangaList != null) {
            if (mangaList.data.isNotEmpty()) {
                // Remove duplicates
                val list: List<Manga> =
                    if (_state.value.mangaList == null) mangaList.data
                    else _state.value.mangaList!!.data.plus(mangaList.data)

                val listOfIds: MutableList<String> = mutableListOf()

                val filteredList: List<Manga> = list.filter {
                    val alreadyInList: Boolean = listOfIds.contains(it.id)
                    if (alreadyInList) {
                        Log.i("MangaListViewModel", "Deleted duplicated: ${it.id}")
                        return@filter false
                    }
                    else {
                        listOfIds.add(it.id)
                        return@filter true
                    }
                }

                _state.value = _state.value.copy(
                    mangaList = MangaList(
                        limit = mangaList.limit,
                        offset = mangaList.offset,
                        total = mangaList.total,
                        data = filteredList
                    )
                )
            } else {
                _state.value = _state.value.copy(
                    canLoadMore = false
                )
            }
        }
    }

    private suspend fun getMangaList(query: MangaListQuery): MangaList? {

        _state.value = _state.value.copy(
            loading = true
        )

        val list = mangaUseCases.getMangaList(query)

        _state.value = _state.value.copy(
            loading = false
        )

        return list
    }
}