package com.felisreader.manga.domain.use_case

data class MangaUseCases(
    val getManga: GetMangaUseCase,
    val getMangaList: GetMangaListUseCase,
    val getMangaStatistics: GetMangaStatisticsUseCase,
    val getMangaTags: GetMangaTagsUseCase
)
