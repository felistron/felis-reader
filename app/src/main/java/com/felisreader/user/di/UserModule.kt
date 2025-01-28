package com.felisreader.user.di

import com.felisreader.user.data.repository.UserRepositoryImp
import com.felisreader.user.data.source.remote.UserService
import com.felisreader.user.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit
            .newBuilder()
            .baseUrl(UserService.AUTH_BASE_URL)
            .build()
            .create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepositoryImp(userService)
    }
}