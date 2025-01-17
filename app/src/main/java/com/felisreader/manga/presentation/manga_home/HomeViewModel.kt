package com.felisreader.manga.presentation.manga_home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.OrderType
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.manga.domain.model.api.ContentRating
import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.manga.domain.model.api.MangaListQuery
import com.felisreader.manga.domain.model.api.MangaOrder
import com.felisreader.manga.domain.model.api.Status
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mangaUseCases: MangaUseCases
) : ViewModel() {
    private val _state: MutableState<HomeState> = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.LoadPopular -> loadPopular()
            is HomeEvent.LoadRecent -> loadRecent()
        }
    }

    private fun loadRecent() {
        viewModelScope.launch {
            val list: MangaList? = mangaUseCases.getMangaList(
                MangaListQuery(
                    limit = 20,
                    offset = 0,
                    includes = listOf(EntityType.COVER_ART, EntityType.AUTHOR),
                    hasAvailableChapters = true,
                    order = listOf(
                        MangaOrder.LatestUploadedChapter(OrderType.Descending)
                    )
                )
            )

            _state.value = _state.value.copy(
                recentManga = list
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadPopular() {
        viewModelScope.launch {
            val list: MangaList? = mangaUseCases.getMangaList(
                MangaListQuery(
                    limit = 10,
                    offset = 0,
                    status = listOf(Status.ONGOING),
                    includes = listOf(EntityType.COVER_ART, EntityType.AUTHOR),
                    hasAvailableChapters = true,
                    order = listOf(
                        MangaOrder.FollowedCount(OrderType.Descending)
                    ),
                    contentRating = listOf(
                        ContentRating.SAFE,
                        ContentRating.SUGGESTIVE
                    ),
                    createdAtSince = LocalDateTime.now().minusMonths(1)
                )
            )

            _state.value = _state.value.copy(
                popularManga = list,
            )
        }
    }
}