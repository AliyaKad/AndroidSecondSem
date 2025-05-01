package ru.itis.AndroidSecondSem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.AndroidSecondSem.data.repository.CurrencyRepository
import ru.itis.AndroidSecondSem.domain.useCase.GetHistoricalRateUseCase
import ru.itis.AndroidSecondSem.domain.useCase.GetLatestRatesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLatestRatesUseCase(repository: CurrencyRepository): GetLatestRatesUseCase {
        return GetLatestRatesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetHistoricalRateUseCase(repository: CurrencyRepository): GetHistoricalRateUseCase {
        return GetHistoricalRateUseCase(repository)
    }
}