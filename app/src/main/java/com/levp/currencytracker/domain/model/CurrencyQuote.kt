package com.levp.currencytracker.domain.model

data class CurrencyQuote(
    val currencyName: String = "",
    val exchangeRateEntries: List<ExchangeRateEntry> = emptyList(),
    val isFavorite: Boolean = false
)
