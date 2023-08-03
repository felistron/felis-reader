package com.felisreader.chapter.domain.use_case

data class ChapterUseCases(
    val mangaFeed: MangaFeedUseCase,
    val chapterFeed: FeedChapterUseCase,
    val report: ReportUseCase,
    val aggregate: GetAggregateUseCase,
    val getChapter: GetChapterUseCase
)