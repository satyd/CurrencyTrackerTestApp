package com.levp.currencytracker.presentation

import com.levp.currencytracker.domain.model.CurrencyQuote

data class CurrencyState(
    val currencyQuote: CurrencyQuote = CurrencyQuote(),
    val isLoading: Boolean = false,
    val filter: String = "" //hehe
)
