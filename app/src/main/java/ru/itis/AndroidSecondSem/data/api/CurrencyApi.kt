package ru.itis.AndroidSecondSem.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.itis.AndroidSecondSem.data.model.CurrencyResponse

interface CurrencyApi {

    @GET("latest.json")
    suspend fun getLatestRates(
        @Query("app_id") apiKey: String = "02c622f203fe44bbb17a3c09fa686187"
    ): Response<CurrencyResponse>

    @GET("historical/{date}.json")
    suspend fun getHistoricalRates(
        @Path("date") date: String,
        @Query("app_id") apiKey: String = "02c622f203fe44bbb17a3c09fa686187"
    ): Response<CurrencyResponse>
}