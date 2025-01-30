package com.felisreader.chapter.presentation.chapter_lector

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.chapter.domain.model.api.*
import com.felisreader.chapter.domain.repository.ChapterRepository
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.Relationship
import com.felisreader.core.domain.repository.ReadingHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LectorViewModel @Inject constructor(
    private val chapterRepository: ChapterRepository,
    private val readingHistoryRepository: ReadingHistoryRepository,
): ViewModel() {
    private val _state: MutableState<LectorState> = mutableStateOf(LectorState())
    val state: State<LectorState> = _state

    fun onEvent(event: LectorEvent) {
        when(event) {
            is LectorEvent.LoadLector -> loadLector(event.chapterId)
            is LectorEvent.ReportImage -> reportImage(event.body)
        }
    }

    private fun loadLector(chapterId: String) {
        _state.value = _state.value.copy(loading = true)

        viewModelScope.launch {
            readingHistoryRepository.insert(chapterId)

            val atHome: AtHomeResponse? = chapterRepository.getChapterFeed(chapterId)

            val chapter: ChapterResponse? = chapterRepository.getChapter(ChapterQuery(
                chapterId = chapterId,
                includes = listOf(
                    EntityType.MANGA,
                    EntityType.SCANLATION_GROUP,
                    EntityType.USER
                )
            ))

            if (atHome == null || chapter == null) return@launch

            val chapterIdList: List<AggregateChapter> = when {
                _state.value.chapters.isEmpty() -> {
                    val mangaRelationship = Relationship
                        .queryRelationship(
                            chapter.data.relationships, EntityType.MANGA
                        )

                    mangaRelationship?.let {
                        val aggregate: Aggregate? = chapterRepository.getAggregate(
                            AggregateQuery(
                                mangaId = mangaRelationship.id.toString(),
                                translatedLanguage =
                                if (chapter.data.attributes.translatedLanguage == null) null
                                else listOf(chapter.data.attributes.translatedLanguage),
                            )
                        )

                        aggregate?.volumes?.flatMap {
                            it.value.chapters.values.toList()
                        } ?.sortedBy {
                            it.chapter.toFloat()
                        }
                    } ?: emptyList()
                }
                else -> {
                    _state.value.chapters
                }
            }

            val prevIndex = chapterIdList.indexOf(
                chapterIdList.find {
                    it.id == chapter.data.id || it.others.contains(chapter.data.id)
                }
            ) - 1

            val nextIndex = chapterIdList.indexOf(
                chapterIdList.find {
                    it.id == chapter.data.id || it.others.contains(chapter.data.id)
                }
            ) + 1

            _state.value = _state.value.copy(
                // TODO: Select quality based on user preferences
                images = atHome.chapter.data.map { fileName ->
                    "${atHome.baseUrl}/data/${atHome.chapter.hash}/${fileName}"
                },
                chapter = chapter.data,
                nextChapter = chapterIdList.getOrNull(nextIndex),
                prevChapter = chapterIdList.getOrNull(prevIndex),
                chapters = chapterIdList,
                loading = false
            )

            if (_state.value.images.size > 1) {
                _state.value.lazyListState.scrollToItem(0)
            }
        }
    }

    private fun reportImage(body: AtHomeReportBody) {
        viewModelScope.launch {
            chapterRepository.postReport(body)
        }
    }
}