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
import com.felisreader.manga.domain.model.api.MangaList
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
            is MangaHistoryEvent.LoadHistory -> loadHistory(event.callback)
            is MangaHistoryEvent.LoadMore -> loadMore()
            is MangaHistoryEvent.DeleteHistoryItem -> deleteHistoryItem(event.mangaId)
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
            val offset = _state.value.offset + LIMIT

            val history = getHistoryList(LIMIT, offset)

            if (history == null) {
                _state.value = _state.value.copy(canLoadMore = false)
            } else {
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

    private fun loadHistory(callback: suspend () -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                offset = 0,
                canLoadMore = true
            )

            val offset = _state.value.offset

            val history = getHistoryList(LIMIT, offset)

            if (history == null) {
                _state.value = _state.value.copy(canLoadMore = false)
            } else {
                _state.value = _state.value.copy(
                    history = history,
                    canLoadMore = history.data.isNotEmpty()
                )
            }

            callback()
        }
    }

    private suspend fun getHistoryList(limit: Int, offset: Int): MangaList? {
        val ids = historyUseCases.getMangaHistory(limit, offset).map { it.id }

        if (ids.isEmpty()) {
            return null
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
            return null
        }

        val historyTemp: MutableList<Manga> = mutableListOf()

        for (id in ids) {
            historyTemp.add( history.data.first { it.id == id} )
        }

        history = history.copy(
            data = historyTemp
        )

        return history
    }
}