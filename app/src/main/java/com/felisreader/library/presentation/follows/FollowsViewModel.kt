package com.felisreader.library.presentation.follows

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.util.MangaUtil
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.repository.MangaRepository
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.api.ReadingStatus
import com.felisreader.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val mangaRepository: MangaRepository,
): ViewModel() {
    private val _state: MutableState<FollowsState> = mutableStateOf(FollowsState())
    val state: State<FollowsState> = _state

    init {
        viewModelScope.launch {
            when (val response = userRepository.getAllReadingStatus()) {
                is ApiResult.Success -> {
                    _state.value = _state.value.copy(statuses = response.body.statuses)
                }
                is ApiResult.Failure -> { /* Failure bc of authorization*/ }
            }
        }
    }

    fun onEvent(event: FollowsEvent) {
        when (event) {
            is FollowsEvent.SetSelectedTab -> setSelectedTab(event.index)
            is FollowsEvent.LoadReadingStatus -> loadReadingStatus(event.status)
        }
    }

    private fun loadReadingStatus(status: ReadingStatus) {
        viewModelScope.launch {
            val mangas = _state.value.statuses?.getMangas(status)

            when (status) {
                ReadingStatus.READING -> _state.value = _state.value.copy(reading = mangas)
                ReadingStatus.ON_HOLD -> _state.value = _state.value.copy(onHold = mangas)
                ReadingStatus.PLAN_TO_READ -> _state.value = _state.value.copy(planToRead = mangas)
                ReadingStatus.DROPPED -> _state.value = _state.value.copy(dropped = mangas)
                ReadingStatus.RE_READING -> _state.value = _state.value.copy(reReading = mangas)
                ReadingStatus.COMPLETED -> _state.value = _state.value.copy(completed = mangas)
            }
        }
    }

    private fun setSelectedTab(index: Int) {
        _state.value = _state.value.copy(selectedTab = index)
    }

    private suspend fun Map<String, ReadingStatus>.getMangas(status: ReadingStatus): List<Manga> {
        val ids = this.filter { it.value == status }.map { it.key }

        if (ids.isEmpty()) return emptyList()

        return mangaRepository.getMangaList(
            MangaListQuery(
                limit = 100,
                ids = ids,
                includes = listOf(EntityType.AUTHOR, EntityType.COVER_ART),
            )
        )?.data?.map { MangaUtil.mangaEntityToManga(it) } ?: return emptyList()
    }
}