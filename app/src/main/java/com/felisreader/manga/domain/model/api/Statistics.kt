package com.felisreader.manga.domain.model.api

data class Statistics(
    val comments: StatisticsDetailsComments?,
    val rating: StatisticsRating,
    val follows: Int,
)

data class StatisticsDetailsComments(
    val threadId: Int,
    val repliesCount: Int
)

data class StatisticsRating(
    val average: Double?,
    val bayesian: Double,
    val distribution: Map<String, Int>?
)
