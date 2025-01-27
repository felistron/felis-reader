package com.felisreader.manga.presentation.manga_home

import com.felisreader.manga.domain.model.api.MangaList

data class HomeState(
    val popularManga: MangaList? = null,
    val recentManga: MangaList? = null,
    val welcomeDialogVisible: Boolean = false,
)