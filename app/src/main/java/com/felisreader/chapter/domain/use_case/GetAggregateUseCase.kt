package com.felisreader.chapter.domain.use_case

import com.felisreader.chapter.domain.model.api.Aggregate
import com.felisreader.chapter.domain.model.api.AggregateQuery
import com.felisreader.chapter.domain.repository.ChapterRepository

class GetAggregateUseCase(
    private val chapterRepository: ChapterRepository
) {
    suspend operator fun invoke(query: AggregateQuery): Aggregate? {
        return chapterRepository.getAggregate(query)
    }
}