package com.levp.currencytracker.data.remote

import com.levp.currencytracker.domain.model.ExchangeRateEntry
import com.levp.currencytracker.domain.util.SupportedSymbols

data class CurrencyQuote(
    val selectedCurrency: SupportedSymbols = SupportedSymbols.USD,
    val exchangeRateEntries: List<ExchangeRateEntry> = emptyList(),
)
