package com.felisreader.author.domain.use_case

import com.felisreader.manga.domain.use_case.GetMangaStatisticsUseCase

data class AuthorUseCases(
    val getAuthor: GetAuthorUseCase,
    val getAuthorTitles: GetAuthorTitlesUseCase,
    val getTitlesStatistics: GetMangaStatisticsUseCase
)