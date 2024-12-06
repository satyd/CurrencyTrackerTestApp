package com.levp.currencytracker.data.networking

import com.levp.currencytracker.domain.ExchangeRate

data class QuoteDto(
    val currencyName: String,
    val rate: String
)

fun QuoteDto.toExchangeRate() = ExchangeRate(
    symbol = currencyName,
    rate = rate.toDoubleOrNull() ?: 0.0
)