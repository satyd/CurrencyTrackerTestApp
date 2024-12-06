package com.levp.currencytracker.domain.model

data class ExchangeRateEntry(
    val symbol1: String,
    val symbol2: String,
    val rate: Double,
    var isFavorite: Boolean = false
)
