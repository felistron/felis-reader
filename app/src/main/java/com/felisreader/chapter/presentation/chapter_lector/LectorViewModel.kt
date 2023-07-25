package com.felisreader.chapter.presentation.chapter_lector

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.chapter.domain.model.Aggregate
import com.felisreader.chapter.domain.model.AggregateChapter
import com.felisreader.chapter.domain.model.api.AtHomeResponse
import com.felisreader.chapter.domain.model.api.AggregateQuery
import com.felisreader.chapter.domain.model.api.ChapterQuery
import com.felisreader.chapter.domain.model.api.ChapterResponse
import com.felisreader.chapter.domain.use_case.ChapterUseCases
import com.felisreader.core.domain.model.EntityType
import com.felisreader.core.domain.model.Relationship
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LectorViewModel @Inject constructor(
    private val useCases: ChapterUseCases
): ViewModel() {
    private val _state: MutableState<LectorState> = mutableStateOf(LectorState())
    val state: State<LectorState> = _state

    fun onEvent(event: LectorEvent) {
        when(event) {
            is LectorEvent.LoadLector -> {
                if (!_state.value.loading) _state.value = _state.value.copy(loading = true)

                viewModelScope.launch {
                    val atHome: AtHomeResponse? = useCases.chapterFeed(event.chapterId)

                    val chapter: ChapterResponse? = useCases.getChapter(ChapterQuery(
                        chapterId = event.chapterId,
                        includes = listOf(
                            EntityType.MANGA,
                            EntityType.SCANLATION_GROUP,
                            EntityType.USER
                        )
                    ))

                    if (atHome != null && chapter != null) {

                        val chapterIdList: List<AggregateChapter> = when {
                            _state.value.chapterIdList.isEmpty() -> {
                                val mangaRelationship = Relationship.queryRelationship(chapter.data.relationships, EntityType.MANGA)

                                val aggregate: Aggregate? = useCases.aggregate(AggregateQuery(
                                    mangaId = mangaRelationship?.id.toString(),
                                    translatedLanguage = if (chapter.data.attributes.translatedLanguage == null) null else listOf(chapter.data.attributes.translatedLanguage),
                                ))

                                aggregate?.volumes!!.flatMap {
                                    it.value.chapters.values.toList()
                                } .sortedBy {
                                    it.chapter.toFloat()
                                }
                            }
                            else -> {
                                _state.value.chapterIdList
                            }
                        }

                        val prevIndex = chapterIdList.indexOf(
                            chapterIdList.find { it.id == chapter.data.id.toString() }
                        ) - 1

                        val nextIndex = chapterIdList.indexOf(
                            chapterIdList.find { it.id == chapter.data.id.toString() }
                        ) + 1

                        _state.value = _state.value.copy(
                            images = atHome.chapter.data.map { fileName ->
                                "${atHome.baseUrl}/data/${atHome.chapter.hash}/${fileName}"
                            },
                            chapter = chapter.data,
                            nextChapter = chapterIdList.getOrNull(nextIndex),
                            prevChapter = chapterIdList.getOrNull(prevIndex),
                            chapterIdList = chapterIdList,
                            loading = false
                        )

                        _state.value.lazyListState.scrollToItem(0)
                    }
                }
            }
            is LectorEvent.Report -> {
                viewModelScope.launch {
                    useCases.report(event.body)
                }
            }
        }
    }
}