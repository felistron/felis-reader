package com.felisreader.author.di

import com.felisreader.author.data.repository.AuthorRepositoryImp
import com.felisreader.author.data.source.remote.AuthorService
import com.felisreader.author.domain.repository.AuthorRepository
import com.felisreader.author.domain.use_case.AuthorUseCases
import com.felisreader.author.domain.use_case.GetAuthorTitlesUseCase
import com.felisreader.author.domain.use_case.GetAuthorUseCase
import com.felisreader.manga.domain.repository.MangaRepository
import com.felisreader.manga.domain.use_case.GetMangaStatisticsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthorModule {
    @Provides
    @Singleton
    fun provideAuthorService(retrofit: Retrofit): AuthorService {
        return retrofit.create(AuthorService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthorRepository(authorService: AuthorService): AuthorRepository {
        return AuthorRepositoryImp(authorService)
    }

    @Provides
    @Singleton
    fun provideAuthorUseCases(
        authorRepository: AuthorRepository,
        mangaRepository: MangaRepository
    ): AuthorUseCases {
        return AuthorUseCases(
            getAuthor = GetAuthorUseCase(authorRepository),
            getAuthorTitles = GetAuthorTitlesUseCase(mangaRepository),
            getTitlesStatistics = GetMangaStatisticsUseCase(mangaRepository),
        )
    }
}