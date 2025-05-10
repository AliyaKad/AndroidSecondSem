package ru.itis.AndroidSecondSem.data.repository

import jakarta.inject.Inject
import ru.itis.AndroidSecondSem.data.api.CurrencyApi
import ru.itis.AndroidSecondSem.data.model.CurrencyResponse
import ru.itis.AndroidSecondSem.Result

class CurrencyRepository @Inject constructor(private val api: CurrencyApi, private val cacheManager: CacheManager) {

    suspend fun getLatestRates(): Result<CurrencyResponse> {
        return try {
            val response = api.getLatestRates()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    suspend fun getHistoricalRate(currencyCode: String, date: String): Pair<Double, Boolean> {
        val key = "$currencyCode-$date"
        val cachedResult = cacheManager.getCachedResult(key)
        if (cachedResult != null) {
            return Pair(cachedResult, true)
        }
        return try {
            val response = api.getHistoricalRates(date)
            if (response.isSuccessful) {
                val rate = response.body()?.rates?.get(currencyCode)
                if (rate != null) {
                    cacheManager.saveToCache(key, rate)
                    Pair(rate, false)
                } else {
                    throw Exception("Currency code not found")
                }
            } else {
                when (response.code()) {
                    400 -> throw Exception("Bad Request")
                    401 -> throw Exception("Unauthorized access")
                    404 -> throw Exception("Data not found")
                    else -> throw Exception("Server error: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

}