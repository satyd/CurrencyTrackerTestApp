package com.levp.currencytracker.presentation

import com.levp.currencytracker.domain.model.ExchangeRateEntry
import com.levp.currencytracker.domain.util.SupportedSymbols

data class CurrencyState(
    val selectedCurrency: SupportedSymbols = SupportedSymbols.USD,
    val exchangeRateEntries: List<ExchangeRateEntry> = emptyList(),
    val favoriteExchangeRates: List<ExchangeRateEntry> = emptyList(),
    val isLoading: Boolean = false,
    val selectedFilter: Int = 0 //hehe
)
