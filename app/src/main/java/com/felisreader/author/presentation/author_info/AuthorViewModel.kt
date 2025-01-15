package com.felisreader.author.presentation.author_info


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felisreader.author.domain.model.api.AuthorEntity
import com.felisreader.author.domain.use_case.AuthorUseCases
import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.manga.domain.model.api.StatisticsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val authorUseCases: AuthorUseCases
) : ViewModel() {
    private val _state: MutableState<AuthorState> = mutableStateOf(AuthorState())
    val state: State<AuthorState> = _state

    fun onEvent(event: AuthorEvent) {
        when (event) {
            is AuthorEvent.LoadAuthor -> loadAuthor(event.authorId)
        }
    }

    private fun loadAuthor(authorId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            val author: AuthorEntity? = getAuthor(authorId)
            val titles: MangaList? = getTitles(authorId)

            _state.value = _state.value.copy(
                author = author,
                titles = titles,
                loading = false,
            )
        }
    }

    private suspend fun getAuthor(authorId: String): AuthorEntity? {
        val author = authorUseCases.getAuthor(
            id = authorId,
            includes = emptyList()
        )
        return author
    }

    private suspend fun getTitles(authorId: String): MangaList? {
        val titles = authorUseCases.getAuthorTitles(authorId, 50)

        val ids: List<String> = titles?.data?.map { manga -> manga.id } ?: emptyList()

        val stats: StatisticsResponse? = authorUseCases.getTitlesStatistics(ids)

        return titles?.copy(
            data = titles.data.map { manga ->
                manga.copy(statistics = stats?.statistics?.get(manga.id))
            }
        )
    }
}