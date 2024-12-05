package com.levp.currencytracker.data.networking

data class CurrencyQuoteDto(
    val currencyName: String,
    val quotes: List<QuoteDto>
)
