package com.levp.currencytracker.domain

data class CurrencyQuote(
    val currencyName: String,
    val quote: Double,
    val isFavorite: Boolean
)
