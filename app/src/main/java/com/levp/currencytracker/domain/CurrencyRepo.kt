package com.levp.currencytracker.domain

import com.levp.currencytracker.data.local.FavoriteCurrencyPair
import com.levp.currencytracker.domain.model.CurrencyQuote
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {

    suspend fun getLatestCurrencyQuotes(
        symbol: SupportedSymbols
    ): Flow<Resource<CurrencyQuote>>

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