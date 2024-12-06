package com.levp.currencytracker.data.repository

import android.util.Log
import com.levp.currencytracker.data.local.CurrencyDatabase
import com.levp.currencytracker.data.local.FavoriteCurrencyPair
import com.levp.currencytracker.data.remote.CurrencyApi
import com.levp.currencytracker.data.remote.toExchangeRate
import com.levp.currencytracker.data.util.parseJson
import com.levp.currencytracker.domain.model.CurrencyQuote
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepoImpl @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val db: CurrencyDatabase,
) : CurrencyRepo {

    val dao = db.dao

    override suspend fun getLatestCurrencyQuotes(base: String): Flow<Resource<CurrencyQuote>> {
        return flow {
            Log.i("hehe", "start flow for base = $base")
            val symbols = SupportedSymbols.entries.filter { it.name != base }.map { it.name }
            val symbolsStr = symbols.joinToString(",")

            emit(Resource.Loading(true))
            val currencyQuotes = try {
                val response = currencyApi.getLatestQuotesForSymbol(
                    base = base,
                    symbols = symbolsStr
                )

                val rates = parseJson(base, response.string()).map { it.toExchangeRate() }
                CurrencyQuote(
                    currencyName = base,
                    exchangeRateEntries = rates,
                    isFavorite = false
                )

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            currencyQuotes?.let {
                emit(
                    Resource.Success(
                        data = it
                    )
                )
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun addToFavorites(favoriteCurrencyPair: FavoriteCurrencyPair) {
        dao.insertFavoritePair(favoriteCurrencyPair)
    }

    override suspend fun removeFromFavorites(favoriteCurrencyPair: FavoriteCurrencyPair) {
        Log.i("hehe","remove fav pair $favoriteCurrencyPair")
        dao.removeFavoritePair(favoriteCurrencyPair.symbol1, favoriteCurrencyPair.symbol2)
    }

    override suspend fun getFavoriteQuotes(): List<FavoriteCurrencyPair> {
        return dao.getAllFavorites()
    }

    override suspend fun getFavoriteQuotesForSymbol(symbol: String): List<FavoriteCurrencyPair> {
        return dao.getFavoritesForSymbol(symbol)
    }

    override fun observeFavoriteQuotes(): Flow<List<FavoriteCurrencyPair>> {
        return dao.observeFavorites()
    }

}
