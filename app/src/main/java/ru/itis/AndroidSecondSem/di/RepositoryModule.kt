package ru.itis.AndroidSecondSem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.AndroidSecondSem.data.api.CurrencyApi
import ru.itis.AndroidSecondSem.data.repository.CurrencyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyApi): CurrencyRepository {
        return CurrencyRepository(api)
    }
}