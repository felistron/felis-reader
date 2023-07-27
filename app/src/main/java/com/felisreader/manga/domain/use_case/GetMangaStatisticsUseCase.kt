package com.felisreader.manga.domain.use_case

import com.felisreader.manga.domain.model.api.StatisticsResponse
import com.felisreader.manga.domain.repository.MangaRepository

class GetMangaStatisticsUseCase(
    private val mangaRepository: MangaRepository
) {
    suspend operator fun invoke(ids: List<String>): StatisticsResponse? {
        return mangaRepository.getStatistics(ids)
    }

    suspend operator fun invoke(id: String): StatisticsResponse? {
        return mangaRepository.getMangaStatistics(id)
    }
}