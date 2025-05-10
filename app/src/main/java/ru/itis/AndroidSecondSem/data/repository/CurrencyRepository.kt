package ru.itis.AndroidSecondSem.data.repository

import jakarta.inject.Inject
import ru.itis.AndroidSecondSem.data.api.CurrencyApi
import ru.itis.AndroidSecondSem.data.model.CurrencyResponse
import ru.itis.AndroidSecondSem.Result

class CurrencyRepository @Inject constructor(private val api: CurrencyApi) {

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


    suspend fun getHistoricalRate(currencyCode: String, date: String): Result<Double> {
        return try {
            val response = api.getHistoricalRates(date)
            if (response.isSuccessful) {
                val rate = response.body()?.rates?.get(currencyCode)
                if (rate != null) {
                    Result.Success(rate)
                } else {
                    Result.Failure(Exception("Currency code not found"))
                }
            } else {
                when (response.code()) {
                    400 -> Result.Failure(Exception("Bad Request"))
                    401 -> Result.Failure(Exception("Unauthorized access"))
                    404 -> Result.Failure(Exception("Data not found"))
                    else -> Result.Failure(Exception("Server error: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }

    }

}