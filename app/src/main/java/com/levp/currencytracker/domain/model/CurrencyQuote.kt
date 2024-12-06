package com.levp.currencytracker.domain.model

import com.levp.currencytracker.domain.util.SupportedSymbols

data class CurrencyQuote(
    val selectedCurrency: SupportedSymbols = SupportedSymbols.USD,
    val exchangeRateEntries: List<ExchangeRateEntry> = emptyList(),
    val isFavorite: Boolean = false
)
