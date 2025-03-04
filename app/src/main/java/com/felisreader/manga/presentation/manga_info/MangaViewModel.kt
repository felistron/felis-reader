package com.felisreader.manga.presentation.manga_info

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.core.domain.model.api.EntityType
import com.felisreader.core.domain.repository.MangaHistoryRepository
import com.felisreader.core.util.MangaUtil
import com.felisreader.manga.domain.model.Manga
import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.repository.MangaRepository
import com.felisreader.user.domain.model.ApiResult
import com.felisreader.user.domain.model.api.ReadingStatus
import com.felisreader.user.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaRepository: MangaRepository,
    private val historyRepository: MangaHistoryRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _state: MutableState<MangaState> = mutableStateOf(MangaState())
    val state: State<MangaState> = _state

    fun onEvent(event: MangaEvent) {
        when (event) {
            is MangaEvent.ToggleDescription -> toggleCollapseDescription()
            is MangaEvent.LoadManga -> loadManga(event.mangaId)
            is MangaEvent.SetRatingDialogVisible -> setRatingDialogVisible(event.visible)
            is MangaEvent.SetSignInDialogVisible -> setSignInDialogVisible(event.visible)
            is MangaEvent.SignInSuccess -> signInSuccess()
            is MangaEvent.SubmitRating -> submitRating(event.rating)
            is MangaEvent.SetReadingStatusDialogVisible -> setReadingStatusDialogVisible(event.visible)
            is MangaEvent.SubmitReadingStatus -> submitReadingStatus(event.status)
        }
    }

    private fun submitReadingStatus(status: ReadingStatus?) {
        viewModelScope.launch {
            _state.value.manga?.let { manga ->
                _state.value = _state.value.copy(
                    readingStatus = status,
                    readingStatusDialogVisible = false
                )
                userRepository.updateReadingStatus(manga.id, status)
            }
        }
    }

    private fun setReadingStatusDialogVisible(visible: Boolean) {
        if (!_state.value.loggedIn && visible) {
            _state.value = _state.value.copy(
                signInDialogVisible = true
            )
        } else {
            _state.value = _state.value.copy(
                readingStatusDialogVisible = visible
            )
        }
    }

    private fun submitRating(rating: Int) {
        viewModelScope.launch {
            state.value.manga?.let { manga ->
                if (_state.value.userRating != rating) {
                    _state.value = _state.value.copy(userRating = rating)
                    userRepository.createOrUpdateRating(manga.id, rating)
                } else {
                    _state.value = _state.value.copy(userRating = 0)
                    userRepository.deleteRating(manga.id)
                }
            }
        }
    }

    private fun signInSuccess() {
        viewModelScope.launch {
            _state.value.manga?.let { manga ->
                _state.value = _state.value.copy(
                    loggedIn = true
                )
                loadRating(manga.id)
                loadReadingStatus(manga.id)
            }
        }
    }

    private fun setSignInDialogVisible(visible: Boolean) {
        _state.value = _state.value.copy(
            signInDialogVisible = visible
        )
    }

    private fun setRatingDialogVisible(visible: Boolean) {
        _state.value = _state.value.copy(
            ratingDialogVisible = visible
        )
    }

    private fun loadManga(mangaId: String) {
        viewModelScope.launch {
            loadRating(mangaId)
            loadReadingStatus(mangaId)

            historyRepository.insert(mangaId)

            val statisticsResponse: StatisticsResponse? = mangaRepository.getMangaStatistics(mangaId)

            val manga: Manga? = getManga(mangaId)

            _state.value = _state.value.copy(
                manga = manga?.copy(
                    statistics = statisticsResponse?.statistics?.get(mangaId)
                )
            )
        }
    }

    private suspend fun loadReadingStatus(mangaId: String) {
        when (val response = userRepository.getReadingStatus(mangaId)) {
            is ApiResult.Success -> {
                val readingStatus = response.body.status
                _state.value = _state.value.copy(
                    loggedIn = true,
                    readingStatus = readingStatus,
                )
            }
            is ApiResult.Failure -> {
                _state.value = _state.value.copy(loggedIn = false)
            }
        }
    }

    private suspend fun loadRating(mangaId: String) {
        when (val response = userRepository.getRatings(listOf(mangaId))) {
            is ApiResult.Success -> {
                _state.value = _state.value.copy(loggedIn = true)

                val ratings = response.body.ratings

                ratings[mangaId]?.let {
                    _state.value = _state.value.copy(
                        userRating = it.rating,
                    )
                }
            }
            is ApiResult.Failure -> {
                _state.value = _state.value.copy(loggedIn = false)
            }
        }
    }

    private fun toggleCollapseDescription() {
        _state.value = _state.value.copy(
            isDescriptionCollapsed = !_state.value.isDescriptionCollapsed
        )
    }

    private suspend fun getManga(mangaId: String): Manga? {
        _state.value = _state.value.copy(loading = true)
        val manga = mangaRepository.getMangaById(
            id = mangaId,
            includes = listOf(
                EntityType.AUTHOR,
                EntityType.COVER_ART
            )
        ) ?: return null
        _state.value = _state.value.copy(loading = false)
        return MangaUtil.mangaEntityToManga(manga.data)
    }
}