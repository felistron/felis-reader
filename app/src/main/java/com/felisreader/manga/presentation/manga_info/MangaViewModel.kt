package com.felisreader.manga.presentation.manga_info

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases
) : ViewModel() {
    private val _state: MutableState<MangaState> = mutableStateOf(MangaState())
    val state: State<MangaState> = _state

    fun onEvent(event: MangaEvent) {
        when (event) {
            is MangaEvent.ToggleDescription -> toggleCollapseDescription()
            is MangaEvent.LoadManga -> loadManga(event.mangaId)
        }
    }

    private fun loadManga(mangaId: String) {
        viewModelScope.launch {
            val statisticsResponse: StatisticsResponse? = mangaUseCases.getMangaStatistics(mangaId)

            val manga: Manga? = getManga(mangaId)

            _state.value = _state.value.copy(
                manga = manga?.copy(
                    statistics = statisticsResponse?.statistics?.get(mangaId)
                )
            )
        }
    }
    private fun toggleCollapseDescription() {
        _state.value = _state.value.copy(
            isDescriptionCollapsed = !_state.value.isDescriptionCollapsed
        )
    }

    private suspend fun getManga(mangaId: String): Manga? {
        _state.value = _state.value.copy(loading = true)
        val manga: Manga? = mangaUseCases.getManga(
            id = mangaId,
            includes = listOf(
                EntityType.AUTHOR,
                EntityType.COVER_ART
            )
        )
        _state.value = _state.value.copy(loading = false)
        return manga
    }
}