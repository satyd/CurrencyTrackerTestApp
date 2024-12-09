package com.levp.currencytracker.domain

import com.levp.currencytracker.data.local.FavoriteCurrencyPair
import com.levp.currencytracker.data.remote.CurrencyQuote
import com.levp.currencytracker.domain.model.ExchangeRateEntry
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {

    suspend fun getLatestCurrencyQuotes(
        baseSymbol: SupportedSymbols
    ): Flow<Resource<CurrencyQuote>>

    suspend fun getFavoriteCurrencyQuotes(
        map: Map<String, List<String>>
    ): Flow<Resource<List<ExchangeRateEntry>>>

    suspend fun addToFavorites(
        favoriteCurrencyPair: FavoriteCurrencyPair
    )

    suspend fun removeFromFavorites(
        favoriteCurrencyPair: FavoriteCurrencyPair
    )

    suspend fun getFavoriteQuotes(): List<FavoriteCurrencyPair>

    suspend fun getFavoriteQuotesForSymbol(symbol: String): List<FavoriteCurrencyPair>

    fun observeFavoriteQuotes(): Flow<List<FavoriteCurrencyPair>>

}