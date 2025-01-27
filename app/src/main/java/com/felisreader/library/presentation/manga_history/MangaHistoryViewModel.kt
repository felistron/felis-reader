package com.felisreader.library.presentation.manga_history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.use_case.HistoryUseCases
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaHistoryViewModel @Inject constructor(
    private val historyUseCases: HistoryUseCases,
    private val mangaUseCases: MangaUseCases,
) : ViewModel() {
    companion object {
        private const val LIMIT: Int = 10
    }

    private val _state: MutableState<MangaHistoryState> = mutableStateOf(MangaHistoryState())
    val state: State<MangaHistoryState> = _state

    fun onEvent(event: MangaHistoryEvent) {
        when (event) {
            is MangaHistoryEvent.LoadHistory -> loadHistory()
            is MangaHistoryEvent.LoadMore -> loadMore()
            is MangaHistoryEvent.DeleteHistoryItem -> deleteHistoryItem(event.mangaId)
            is MangaHistoryEvent.Refresh -> refresh()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = MangaHistoryState()

            loadHistory()
        }
    }

    private fun deleteHistoryItem(mangaId: String) {
        viewModelScope.launch {
            historyUseCases.deleteMangaItem(mangaId)

            val items = _state.value.history?.data?.filterNot { it.id == mangaId } ?: emptyList()

            _state.value = _state.value.copy(
                history = _state.value.history?.copy(
                    data = items
                )
            )
        }
    }

    private fun loadMore() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                offset = _state.value.offset + LIMIT
            )

            loadHistory()
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val offset = _state.value.offset

            val ids = historyUseCases.getMangaHistory(LIMIT, offset).map { it.id }

            if (ids.isEmpty()) {
                _state.value = _state.value.copy(canLoadMore = false)
                return@launch
            }

            var history = mangaUseCases.getMangaList(MangaListQuery(
                ids = ids,
                // this is needed bc mangadex decided to ¯\_(ツ)_/¯
                // otherwise it just won't work correctly
                contentRating = listOf(
                    ContentRating.SAFE,
                    ContentRating.SUGGESTIVE,
                    ContentRating.EROTICA,
                    ContentRating.PORNOGRAPHIC,
                ),
                includes = listOf(EntityType.AUTHOR, EntityType.COVER_ART),
            ))

            if (history == null || history.data.isEmpty()) {
                _state.value = _state.value.copy(canLoadMore = false)
            } else {
                val historyTemp: MutableList<Manga> = mutableListOf()

                for (id in ids) {
                    historyTemp.add( history.data.first { it.id == id} )
                }

                history = history.copy(
                    data = historyTemp
                )

                val storedIds = _state.value.history?.data?.map { it.id } ?: emptyList()
                val list = history.data.filter { manga -> !storedIds.contains(manga.id) }

                _state.value = _state.value.copy(
                    history = _state.value.history?.copy(
                        limit = history.limit,
                        offset = history.offset,
                        total = history.total,
                        data = _state.value.history?.data?.plus(list) ?: list
                    ) ?: history,
                    canLoadMore = list.isNotEmpty()
                )
            }
        }
    }
}