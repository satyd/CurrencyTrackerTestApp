package com.levp.currencytracker.data.networking

import com.squareup.moshi.Json
//hehe hzhz
data class CurrencyQuoteDto(
    @field:Json(name = "base")
    val base: String,
    val rates: List<QuoteDto>
)
