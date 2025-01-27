package com.felisreader.manga.presentation.manga_search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.Sentence
import com.felisreader.core.domain.use_case.HistoryUseCases
import com.felisreader.core.util.AppUtil
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.model.api.TagEntity
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases,
    private val historyUseCases: HistoryUseCases,
): ViewModel() {

    private val _state: MutableState<SearchState> = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _titleSearchState = MutableStateFlow("")
    val titleSearchState = _titleSearchState.asStateFlow()

    private val _historyState = MutableStateFlow<List<String>>(emptyList())
    @OptIn(FlowPreview::class)
    val historyState = titleSearchState
        .debounce(50L)
        .combine(_historyState) { title, list ->
            val closestSentences: List<Sentence> = AppUtil
                .findClosestSentences(title, list.toMutableList(), 10)

            closestSentences.filterIndexed { index, sentence ->
                if (index > 0) (sentence.score - closestSentences[0].score) < 2 && sentence.score < 3
                else sentence.score < 3
            }.map { it.sentence }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _historyState.value
        )

    private val _tagsState = MutableStateFlow<List<TagEntity>>(emptyList())
    val tagsState = _tagsState.asStateFlow()

    init {
        viewModelScope.launch {
            historyUseCases.getHistory().collect { history ->
                val list: List<String> = history.map {
                    it.content
                }
                _historyState.value = list
            }
        }

        viewModelScope.launch {
            _tagsState.value = mangaUseCases.getMangaTags()
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
                        includedTags = event.query.includedTags,
                        offset = 0,
                        order = event.query.order
                    ),
                    mangaList = null,
                    lazyListState = LazyListState(),
                    canLoadMore = true,
                    loading = true
                )

                viewModelScope.launch {
                    getMangaList()?.let { mangaList ->
                        val canLoadMore = mangaList.data.size < mangaList.total

                        _state.value = _state.value.copy(
                            mangaList = mangaList,
                            canLoadMore = canLoadMore,
                            loading = false
                        )
                    }
                }
            }

            is SearchEvent.SearchBarActive -> {
                _state.value = _state.value.copy(
                    searchBarActive = event.active
                )
            }

            is SearchEvent.AddHistoryItem -> addHistoryItem(event.content, event.timestamp)

            is SearchEvent.DeleteHistoryItem -> deleteHistoryItem(event.item)

            is SearchEvent.OnSearchTextChange -> {
                _titleSearchState.value = event.text
            }

            is SearchEvent.OnSearch -> onSearch(event.title)

            is SearchEvent.LoadMangaList -> loadMangaList(event.title, event.tag)
        }
    }

    private fun loadMangaList(title: String?, tag: String?) {
        _state.value = _state.value.copy(
            query = _state.value.query.copy(
                title = title,
                includedTags = tag?.let { listOf(it) },
            ),
            mangaList = null,
            lazyListState = LazyListState(),
            canLoadMore = true,
            loading = true
        )

        viewModelScope.launch {
            getMangaList()?.let { mangaList ->
                val canLoadMore = mangaList.data.size < mangaList.total

                _state.value = _state.value.copy(
                    mangaList = mangaList,
                    canLoadMore = canLoadMore,
                    loading = false
                )
            }
        }
    }

    private fun onSearch(title: String) {
        if (title.isNotBlank()) {
            onEvent(
                SearchEvent.AddHistoryItem(title, System.currentTimeMillis())
            )
        }
        onEvent(SearchEvent.SearchBarActive(false))
        onEvent(SearchEvent.ApplyFilter(
            query = _state.value.query.copy(
                title = title
            )
        ))
    }

    private fun addHistoryItem(content: String, timestamp: Long) {
        viewModelScope.launch {
            historyUseCases.addItem(content, timestamp)

            historyUseCases.getHistory().collect { list ->
                _historyState.value = list.map { it.content }
            }
        }
    }

    private fun deleteHistoryItem(item: String) {
        viewModelScope.launch {
            historyUseCases.deleteItem(item)
            // it is no necessary to update the historyState
            // trust me (it is updated right after when the search text is cleared)
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
                    val ids: List<String> = _state.value.mangaList?.data?.map { it.id } ?: emptyList()

                    val list: List<Manga> = mangaList.data.filter { manga ->
                        !ids.contains(manga.id)
                    }

                    _state.value = _state.value.copy(
                        mangaList = MangaList(
                            limit = mangaList.limit,
                            offset = mangaList.offset,
                            total = mangaList.total,
                            data = _state.value.mangaList?.data?.plus(list) ?: list
                        )
                    )
                }
            }
        }
    }

    private suspend fun getMangaList(): MangaList? {
        val list: MangaList? = mangaUseCases.getMangaList(_state.value.query)

        val ids: List<String> = list?.data?.map { manga -> manga.id } ?: emptyList()

        val stats: StatisticsResponse? = mangaUseCases.getMangaStatistics(ids)

        return list?.copy(
            data = list.data.map { manga ->
                manga.copy(statistics = stats?.statistics?.get(manga.id))
            }
        )
    }
}
