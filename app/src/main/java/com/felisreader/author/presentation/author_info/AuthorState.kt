package com.felisreader.author.presentation.author_info

import com.felisreader.author.domain.model.api.AuthorEntity
import com.felisreader.manga.domain.model.api.MangaList

data class AuthorState(
    val author: AuthorEntity? = null,
    val titles: MangaList? = null,
    val loading: Boolean = true,
)
