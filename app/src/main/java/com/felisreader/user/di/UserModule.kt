package com.felisreader.user.di

import com.felisreader.cipher.SecurePreferencesManager
import com.felisreader.user.data.repository.AuthRepositoryImp
import com.felisreader.user.data.source.remote.AuthService
import com.felisreader.user.domain.repository.AuthRepository
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
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit
            .newBuilder()
            .baseUrl(AuthService.AUTH_BASE_URL)
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        prefsManager: SecurePreferencesManager
    ): AuthRepository {
        return AuthRepositoryImp(authService, prefsManager)
    }
}