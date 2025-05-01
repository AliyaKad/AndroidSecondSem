package ru.itis.AndroidSecondSem.domain.useCase

import jakarta.inject.Inject
import ru.itis.AndroidSecondSem.data.model.CurrencyResponse
import ru.itis.AndroidSecondSem.data.repository.CurrencyRepository
import ru.itis.AndroidSecondSem.Result

class GetLatestRatesUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): Result<CurrencyResponse> {
        return repository.getLatestRates()
    }
}