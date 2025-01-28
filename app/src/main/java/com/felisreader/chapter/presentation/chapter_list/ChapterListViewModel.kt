package com.felisreader.chapter.presentation.chapter_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.chapter.domain.model.api.Chapter
import com.felisreader.chapter.domain.model.api.FeedQuery
import com.felisreader.chapter.domain.use_case.ChapterUseCases
import com.felisreader.chapter.domain.model.api.ChapterOrder
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChapterListViewModel @Inject constructor(
    private val useCases: ChapterUseCases
) : ViewModel() {

    private val _state: MutableState<ChapterListState> = mutableStateOf(ChapterListState())
    val state: State<ChapterListState> = _state

    fun onEvent(event: ChapterListEvent) {
        when (event) {
            is ChapterListEvent.FeedChapters -> feedChapters(event.mangaId, event.callback)
            is ChapterListEvent.LoadMore -> loadMore()
            is ChapterListEvent.ToggleOrder -> toggleOrder()
        }
    }

    private fun feedChapters(mangaId: String, callback: suspend () -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                canLoadMore = true,
                loading = true,
            )

            val order = _state.value.order

            val query = FeedQuery(
                id = UUID.fromString(mangaId),
                order = listOf(
                    ChapterOrder.Volume(order),
                    ChapterOrder.Chapter(order)
                ),
                includes = listOf(
                    EntityType.SCANLATION_GROUP,
                    EntityType.USER
                ),
                limit = ChapterListState.LIMIT,
                offset = 0
            )

            useCases.mangaFeed(query)?.let { response ->
                _state.value = _state.value.copy(
                    chapterList = response.data,
                    loading = false,
                    feedQuery = query,
                    order = order,
                    canLoadMore = response.data.size < response.total
                )
            }

            callback()
        }
    }

    private fun loadMore() {
        viewModelScope.launch {
            val query = _state.value.feedQuery?.copy(
                offset = _state.value.feedQuery?.offset!!.plus(ChapterListState.LIMIT)
            )

            // Possible bugs incoming...
            useCases.mangaFeed(query!!)?.let { response ->
                val canLoadMore = response.data.isNotEmpty()

                // Remove duplicates
                val ids: List<String> = _state.value.chapterList.map { it.id }

                val list: List<Chapter> = response.data.filter { chapter ->
                    !ids.contains(chapter.id)
                }

                _state.value = _state.value.copy(
                    chapterList = _state.value.chapterList.plus(list),
                    feedQuery = query,
                    canLoadMore = canLoadMore
                )
            }
        }
    }

    private fun toggleOrder() {
        _state.value.feedQuery?.let {
            val order: OrderType = when(_state.value.order) {
                is OrderType.Descending -> OrderType.Ascending
                is OrderType.Ascending -> OrderType.Descending
            }

            _state.value = _state.value.copy(
                order = order
            )

            onEvent(ChapterListEvent.FeedChapters(_state.value.feedQuery?.id.toString()))
        }
    }
}