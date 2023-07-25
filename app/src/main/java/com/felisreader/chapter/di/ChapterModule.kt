package com.felisreader.chapter.di

import com.felisreader.chapter.data.repository.ChapterRepositoryImp
import com.felisreader.chapter.data.source.remote.ChapterService
import com.felisreader.chapter.domain.repository.ChapterRepository
import com.felisreader.chapter.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChapterModule {
    @Provides
    @Singleton
    fun provideChapterService(retrofit: Retrofit): ChapterService {
        return retrofit.create(ChapterService::class.java)
    }

    @Provides
    @Singleton
    fun provideChapterRepository(chapterService: ChapterService): ChapterRepository {
        return ChapterRepositoryImp(chapterService)
    }

    @Provides
    @Singleton
    fun provideChapterUseCases(chapterRepository: ChapterRepository): ChapterUseCases {
        return ChapterUseCases(
            MangaFeedUseCase(chapterRepository),
            FeedChapterUseCase(chapterRepository),
            ReportUseCase(chapterRepository),
            GetAggregateUseCase(chapterRepository),
            GetChapterUseCase(chapterRepository)
        )
    }
}