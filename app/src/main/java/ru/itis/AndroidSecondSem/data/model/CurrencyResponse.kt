package ru.itis.AndroidSecondSem.data.model

data class CurrencyResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Double>
)