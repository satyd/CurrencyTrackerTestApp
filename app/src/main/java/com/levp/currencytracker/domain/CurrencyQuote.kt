package com.levp.currencytracker.domain

data class CurrencyQuote(
    val currencyName: String = "",
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val isFavorite: Boolean = false
)
