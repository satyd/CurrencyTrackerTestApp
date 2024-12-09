package com.levp.currencytracker.data.remote

import com.levp.currencytracker.domain.model.ExchangeRateEntry

data class QuoteDto(
    val currencyName1: String,
    val currencyName2: String,
    val rate: String
)

fun QuoteDto.toExchangeRate() = ExchangeRateEntry(
    symbol1 = currencyName1,
    symbol2 = currencyName2,
    rate = rate.toDoubleOrNull() ?: 0.0
)

fun QuoteDto.toFavExchangeRate() = ExchangeRateEntry(
    symbol1 = currencyName1,
    symbol2 = currencyName2,
    isFavorite = true,
    rate = rate.toDoubleOrNull() ?: 0.0
)
