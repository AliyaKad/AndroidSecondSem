package ru.itis.AndroidSecondSem.domain.useCase

import jakarta.inject.Inject
import ru.itis.AndroidSecondSem.data.repository.CurrencyRepository
import ru.itis.AndroidSecondSem.Result

class GetHistoricalRateUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend operator fun invoke(currencyCode: String, date: String): Pair<Double, Boolean> {
        return repository.getHistoricalRate(currencyCode, date)
    }
}