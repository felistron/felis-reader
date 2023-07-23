package com.felisreader.chapter.presentation.chapter_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.chapter.domain.model.Chapter
import com.felisreader.chapter.domain.model.api.FeedQuery
import com.felisreader.chapter.domain.model.api.FeedResponse
import com.felisreader.chapter.domain.use_case.ChapterUseCases
import com.felisreader.core.domain.model.ChapterOrder
import com.felisreader.core.domain.model.EntityType
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
            is ChapterListEvent.FeedChapters -> {
                viewModelScope.launch {
                    val query = FeedQuery(
                        id = UUID.fromString(event.id),
                        order = listOf(
                            ChapterOrder.Volume(OrderType.Descending),
                            ChapterOrder.Chapter(OrderType.Descending)
                        ),
                        includes = listOf(
                            EntityType.SCANLATION_GROUP,
                            EntityType.USER
                        ),
                        limit = ChapterListState.LIMIT,
                        offset = 0
                    )

                    val response: FeedResponse? = useCases.mangaFeed(query)

                    if (response != null) {
                        _state.value = _state.value.copy(
                            chapterList = response.data,
                            loading = false,
                            feedQuery = query
                        )
                    }
                }
            }
            is ChapterListEvent.LoadMore -> {
                viewModelScope.launch {
                    val query = _state.value.feedQuery?.copy(
                        offset = _state.value.feedQuery?.offset!!.plus(ChapterListState.LIMIT)
                    )

                    // Possible bugs incoming...
                    val response: FeedResponse? = useCases.mangaFeed(query!!)

                    if (response != null) {
                        if (response.data.isNotEmpty()) {

                            // Remove duplicates
                            val ids: List<String> = _state.value.chapterList.map { it.id.toString() }

                            val list: List<Chapter> = response.data.filter {
                                !ids.contains(it.id.toString())
                            }

                            _state.value = _state.value.copy(
                                chapterList = _state.value.chapterList.plus(list),
                                feedQuery = query
                            )
                        } else {
                            _state.value = _state.value.copy(
                                canLoadMore = false
                            )
                        }
                    }
                }
            }
        }
    }
}