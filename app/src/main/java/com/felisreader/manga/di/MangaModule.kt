package com.felisreader.manga.di

import com.felisreader.manga.data.repository.MangaRepositoryImp
import com.felisreader.manga.data.source.remote.MangaService
import com.felisreader.manga.domain.repository.MangaRepository
import com.felisreader.manga.domain.use_case.GetMangaListUseCase
import com.felisreader.manga.domain.use_case.GetMangaUseCase
import com.felisreader.manga.domain.use_case.MangaUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MangaModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(MangaService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

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
    fun provideMangaUseCases(mangaRepository: MangaRepository): MangaUseCases {
        return MangaUseCases(
            GetMangaUseCase(mangaRepository),
            GetMangaListUseCase(mangaRepository)
        )
    }
}