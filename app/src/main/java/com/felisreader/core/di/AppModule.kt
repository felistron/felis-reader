package com.felisreader.core.di

import android.app.Application
import androidx.room.Room
import com.felisreader.core.data.repository.HistoryRepositoryImp
import com.felisreader.core.data.source.local.HistoryDatabase
import com.felisreader.core.domain.repository.HistoryRepository
import com.felisreader.core.domain.use_case.AddHistoryItemUseCase
import com.felisreader.core.domain.use_case.DeleteHistoryItemUseCase
import com.felisreader.core.domain.use_case.GetHistoryListUseCase
import com.felisreader.core.domain.use_case.HistoryUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHistoryDatabase(app: Application): HistoryDatabase {
        return Room.databaseBuilder(
            app,
            HistoryDatabase::class.java,
            HistoryDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(db: HistoryDatabase): HistoryRepository {
        return HistoryRepositoryImp(db.historyDao)
    }

    @Provides
    @Singleton
    fun provideUseCases(historyRepository: HistoryRepository): HistoryUseCases {
        return HistoryUseCases(
            getHistory = GetHistoryListUseCase(historyRepository),
            addItem = AddHistoryItemUseCase(historyRepository),
            deleteItem = DeleteHistoryItemUseCase(historyRepository)
        )
    }
}