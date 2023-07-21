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
                    val response: FeedResponse? = useCases.mangaFeed(
                        FeedQuery(
                            id = UUID.fromString(event.id),
                            order = listOf(
                                ChapterOrder.Volume(OrderType.Descending),
                                ChapterOrder.Chapter(OrderType.Descending)
                            ),
                            includes = listOf(
                                EntityType.SCANLATION_GROUP,
                                EntityType.USER
                            )
                        )
                    )

                    if (response != null) {

                        val groupedVolume: Map<String, List<Chapter>> = response.data.groupBy { it.attributes.volume ?: "N/A" }

                        val groupedByVolumeAndChapter: Map<String, Map<String, List<Chapter>>> = groupedVolume.entries.associate { volume ->
                            volume.key to volume.value.groupBy { chapter ->
                                chapter.attributes.chapter ?: "N/A"
                            }
                        }

                        _state.value = _state.value.copy(
                            chapterList = groupedByVolumeAndChapter
                        )
                    }
                }
            }
        }
    }
}