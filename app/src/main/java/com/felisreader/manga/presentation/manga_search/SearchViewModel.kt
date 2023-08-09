package com.felisreader.manga.presentation.manga_search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.SearchHistoryEntity
import com.felisreader.core.domain.use_case.HistoryUseCases
import com.felisreader.datastore.DataStoreManager
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases,
    private val historyUseCases: HistoryUseCases,
    private val dataStore: DataStoreManager
): ViewModel() {

    private val _state: MutableState<SearchState> = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    init {
        viewModelScope.launch {
            dataStore.getPreferences().collect { preferences ->
                _state.value = _state.value.copy(welcomeDialogVisible = preferences.showWelcome)
            }
        }

        viewModelScope.launch {
            getMangaList()?.let { mangaList ->
                val canLoadMore = mangaList.data.size < mangaList.total

                _state.value = _state.value.copy(
                    mangaList = mangaList,
                    canLoadMore = canLoadMore
                )
            }
        }

        viewModelScope.launch {
            historyUseCases.getHistory().collect { history ->
                _state.value = _state.value.copy(searchHistory = history)
            }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.LoadMore -> loadMore()

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
                    getMangaList()?.let { mangaList ->
                        val canLoadMore = mangaList.data.size < mangaList.total

                        _state.value = _state.value.copy(
                            mangaList = mangaList,
                            canLoadMore = canLoadMore
                        )
                    }
                }
            }

            is SearchEvent.SearchBarActive -> {
                _state.value = _state.value.copy(
                    searchBarActive = event.active
                )
            }

            is SearchEvent.CloseWelcomeDialog -> {
                _state.value = _state.value.copy(
                    welcomeDialogVisible = false
                )

                viewModelScope.launch {
                    dataStore.getPreferences().collect { preferences ->
                        dataStore.savePreferences(preferences.copy(showWelcome = event.showAgain))
                    }
                }
            }

            is SearchEvent.AddHistoryItem -> addHistoryItem(event.content, event.timestamp)

            is SearchEvent.DeleteHistoryItem -> deleteHistoryItem(event.item)
        }
    }

    private fun addHistoryItem(content: String, timestamp: Long) {
        viewModelScope.launch {
            historyUseCases.addItem(content, timestamp)

            historyUseCases.getHistory().collect {
                _state.value = _state.value.copy(
                    searchHistory = it
                )
            }
        }
    }

    private fun deleteHistoryItem(item: SearchHistoryEntity) {
        viewModelScope.launch {
            historyUseCases.deleteItem(item)

            _state.value = _state.value.copy(
                searchHistory = _state.value.searchHistory.minus(item)
            )
        }
    }

    private fun loadMore() {
        _state.value = _state.value.copy(
            query = _state.value.query.copy(
                offset = _state.value.query.offset?.plus(_state.value.query.limit!!),
            )
        )

        viewModelScope.launch {
            getMangaList()?.let { mangaList ->
                if (mangaList.data.isEmpty()) {
                    _state.value = _state.value.copy(canLoadMore = false)
                } else {
                    // Remove duplicates
                    val list: List<Manga> =
                        if (_state.value.mangaList == null) mangaList.data
                        else _state.value.mangaList!!.data.plus(mangaList.data)

                    val listOfIds: MutableList<String> = mutableListOf()

                    val filteredList: List<Manga> = list.filter {
                        val alreadyInList: Boolean = listOfIds.contains(it.id)
                        if (alreadyInList) {
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
                }
            }
        }
    }

    private suspend fun getMangaList(): MangaList? {
        _state.value = _state.value.copy(loading = true)

        val list: MangaList? = mangaUseCases.getMangaList(_state.value.query)

        val ids: List<String> = list?.data?.map { manga -> manga.id } ?: emptyList()

        val stats: StatisticsResponse? = mangaUseCases.getMangaStatistics(ids)

        _state.value = _state.value.copy(loading = false)

        return list?.copy(
            data = list.data.map { manga ->
                manga.copy(statistics = stats?.statistics?.get(manga.id))
            }
        )
    }
}
