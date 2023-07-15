package com.felisreader.manga.presentation.manga_list

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.MangaListRequest
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.MangaList
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaListViewModel @Inject constructor(
    private val useCases: MangaUseCases
): ViewModel() {
    private val _state: MutableState<MangaListState> = mutableStateOf(MangaListState())
    val state: State<MangaListState> = _state

    fun onEvent(event: MangaListEvent) {
        when (event) {
            is MangaListEvent.LoadMore -> {
                viewModelScope.launch {
                    loadMore()
                }
            }
            is MangaListEvent.ToggleFilter -> {
                _state.value = _state.value.copy(
                    expandedFilter = !_state.value.expandedFilter
                )
            }
            is MangaListEvent.ApplyFilter -> {
                _state.value = _state.value.copy(
                    query = _state.value.query.copy(
                        contentRating = event.query.contentRating,
                        publicationDemographic = event.query.publicationDemographic,
                        status = event.query.status,
                        title = if (event.query.title.isNullOrBlank()) null else event.query.title,
                        offset = 0
                    ),
                    mangaList = null,
                    lazyListState = LazyListState()
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
        }
    }

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

    private suspend fun getMangaList(query: MangaListRequest): MangaList? {

        _state.value = _state.value.copy(
            loading = true
        )

        val list = useCases.getMangaList(query)

        _state.value = _state.value.copy(
            loading = false
        )

        return list
    }
}