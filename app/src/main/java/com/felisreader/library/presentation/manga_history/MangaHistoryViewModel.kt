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
        private const val LIMIT: Int = 20
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

            _state.value = _state.value.copy(
                history = _state.value.history?.filterNot { it.id == mangaId }
            )
        }
    }

    private fun loadMore() {
        viewModelScope.launch {
            val offset = _state.value.offset + LIMIT

            val history = getHistoryList(LIMIT, offset)

            val storedIds = _state.value.history?.map { it.id } ?: emptyList()
            val list = history.filter { manga -> !storedIds.contains(manga.id) }

            _state.value = _state.value.copy(
                history = _state.value.history?.plus(list),
                canLoadMore = list.isNotEmpty()
            )
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

            _state.value = _state.value.copy(
                history = history,
                canLoadMore = history.isNotEmpty() && history.size >= LIMIT
            )

            callback()
        }
    }

    private suspend fun getHistoryList(limit: Int, offset: Int): List<Manga> {
        val ids = historyUseCases.getMangaHistory(limit, offset).map { it.id }

        if (ids.isEmpty()) {
            return emptyList()
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
            return emptyList()
        }

        val historyTemp: MutableList<Manga> = mutableListOf()

        for (id in ids) {
            historyTemp.add( history.data.first { it.id == id} )
        }

        history = history.copy(
            data = historyTemp
        )

        return history.data
    }
}