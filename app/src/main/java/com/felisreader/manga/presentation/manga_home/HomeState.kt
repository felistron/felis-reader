package com.felisreader.manga.presentation.manga_home

import com.felisreader.manga.domain.model.api.MangaList
import com.felisreader.user.domain.model.api.UserEntity

data class HomeState(
    val popularManga: MangaList? = null,
    val recentManga: MangaList? = null,
    val welcomeDialogVisible: Boolean = false,
    val loggedUser: UserEntity? = null,
)