package com.felisreader.manga.di

import com.felisreader.manga.data.repository.MangaRepositoryImp
import com.felisreader.manga.data.source.remote.MangaService
import com.felisreader.manga.domain.repository.MangaRepository
import com.felisreader.manga.domain.use_case.GetMangaListUseCase
import com.felisreader.manga.domain.use_case.GetMangaStatisticsUseCase
import com.felisreader.manga.domain.use_case.GetMangaUseCase
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MangaModule {
    @Provides
    @Singleton
    fun provideMangaService(retrofit: Retrofit): MangaService {
        return retrofit.create(MangaService::class.java)
    }

    @Provides
    @Singleton
    fun provideMangaRepository(mangaService: MangaService): MangaRepository {
        return MangaRepositoryImp(mangaService)
    }

    @Provides
    @Singleton
    fun provideMangaUseCases(repository: MangaRepository): MangaUseCases {
        return MangaUseCases(
            GetMangaUseCase(repository),
            GetMangaListUseCase(repository),
            GetMangaStatisticsUseCase(repository)
        )
    }
}