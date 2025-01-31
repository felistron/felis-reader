package com.felisreader.library.presentation.reading_history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.chapter.domain.model.api.Chapter
import com.felisreader.chapter.domain.model.api.ChapterListQuery
import com.felisreader.chapter.domain.repository.ChapterRepository
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.model.api.Relationship
import com.felisreader.core.domain.repository.ReadingHistoryRepository
import com.felisreader.core.util.MangaUtil
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.repository.MangaRepository
import com.felisreader.user.domain.model.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingHistoryViewModel @Inject constructor(
    private val readingHistoryRepository: ReadingHistoryRepository,
    private val chapterRepository: ChapterRepository,
    private val mangaRepository: MangaRepository,
): ViewModel() {
    private val _state: MutableState<ReadingHistoryState> = mutableStateOf(ReadingHistoryState())
    val state: State<ReadingHistoryState> = _state

    fun onEvent(event: ReadingHistoryEvent) {
        when (event) {
            is ReadingHistoryEvent.OnDeleteChapter -> deleteChapter(event.chapterId)
            is ReadingHistoryEvent.LoadHistory -> loadHistory(event.callback)
            is ReadingHistoryEvent.LoadMore -> loadMore()
        }
    }

    private fun loadMore() {
        viewModelScope.launch {
            val limit = ReadingHistoryState.LIMIT
            val newOffset = _state.value.offset + limit

            val history = getHistory(limit, newOffset)
                .filterNot { new ->
                    _state.value.history?.any { it.second.id == new.second.id } ?: false
                }

            _state.value = _state.value.copy(
                history = _state.value.history?.plus(history),
                canLoadMore = history.isNotEmpty()
            )
        }
    }

    private fun loadHistory(callback: suspend () -> Unit) {
        viewModelScope.launch {
            val limit = ReadingHistoryState.LIMIT
            val offset = _state.value.offset

            val history = getHistory(limit, offset)

            _state.value = _state.value.copy(
                history = history,
                canLoadMore = history.isNotEmpty() && history.size >= ReadingHistoryState.LIMIT
            )

            callback()
        }
    }

    private suspend fun getHistory(limit: Int, offset: Int): List<Pair<Manga, Chapter>> {
        val chapterIds = readingHistoryRepository.getAll(limit, offset).map { it.id }

        if (chapterIds.isEmpty()) {
            return emptyList()
        }

        val chapterResponse = chapterRepository.getChapterList(
            ChapterListQuery(
                ids = chapterIds,
                contentRating = listOf(
                    ContentRating.SAFE,
                    ContentRating.SUGGESTIVE,
                    ContentRating.EROTICA,
                    ContentRating.PORNOGRAPHIC,
                ),
                includes = listOf(EntityType.MANGA)
            )
        )

        when (chapterResponse) {
            is ApiResult.Success -> {
                val chapters: MutableList<Chapter> = mutableListOf()

                for (id in chapterIds) {
                    val ch = chapterResponse.body.data.find { it.id == id }
                    if (ch == null) continue
                    chapters.add(ch)
                }

                val mangaIds = chapters.mapNotNull {
                    Relationship.queryRelationship(
                        it.relationships,
                        EntityType.MANGA
                    )?.id?.toString()
                }.distinct()

                val mangas = mangaRepository.getMangaList(
                    MangaListQuery(
                        ids = mangaIds,
                        contentRating = listOf(
                            ContentRating.SAFE,
                            ContentRating.SUGGESTIVE,
                            ContentRating.EROTICA,
                            ContentRating.PORNOGRAPHIC,
                        ),
                        includes = listOf(EntityType.COVER_ART, EntityType.AUTHOR)
                    )
                )?.let { res -> res.data.map { MangaUtil.mangaEntityToManga(it) } }
                    ?: return emptyList()

                val history: MutableList<Pair<Manga, Chapter>> = mutableListOf()

                for (chapter in chapters) {
                    val mangaId = Relationship.queryRelationship(chapter.relationships, EntityType.MANGA)?.id?.toString()
                        ?: return emptyList()

                    val manga = mangas.find { it.id == mangaId }
                        ?: return emptyList()

                    history.add(Pair(manga, chapter))
                }

                return history
            }
            is ApiResult.Failure -> { return emptyList() }
        }
    }

    private fun deleteChapter(chapterId: String) {
        viewModelScope.launch {
            readingHistoryRepository.deleteById(chapterId)

            _state.value = _state.value.copy(
                history = _state.value.history?.filterNot { it.second.id == chapterId }
            )
        }
    }
}