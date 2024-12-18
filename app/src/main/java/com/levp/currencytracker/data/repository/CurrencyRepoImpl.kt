package com.levp.currencytracker.data.repository

import android.util.Log
import com.levp.currencytracker.data.local.CurrencyDatabase
import com.levp.currencytracker.data.local.FavoriteCurrencyPair
import com.levp.currencytracker.data.remote.CurrencyApi
import com.levp.currencytracker.data.remote.toExchangeRate
import com.levp.currencytracker.data.util.getFakeExchangeRatesResponse
import com.levp.currencytracker.data.util.parseJson
import com.levp.currencytracker.data.remote.CurrencyQuote
import com.levp.currencytracker.data.remote.toFavExchangeRate
import com.levp.currencytracker.data.util.getFakeFavExchangeRatesResponse
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.domain.model.ExchangeRateEntry
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

    override suspend fun getLatestCurrencyQuotes(baseSymbol: SupportedSymbols): Flow<Resource<CurrencyQuote>> {
        return flow {
            Log.i("hehe", "start flow for base = $baseSymbol")
            val symbols = SupportedSymbols.entries.filter { it.name != baseSymbol.name }.map { it.name }
            val symbolsStr = symbols.joinToString(",")

            emit(Resource.Loading(true))
            val currencyQuotes = try {
                //это для реальных данных
                /*val response = currencyApi.getLatestQuotesForSymbol(
                    base = baseSymbol.name,
                    symbols = symbolsStr
                ).string()*/
                //это для фейковых в точности повторяющих апишные данных
                val response = getFakeExchangeRatesResponse(symbol = baseSymbol)
                Log.d("hehe","response = $response")
                val rates = parseJson(baseSymbol.name, response).map { it.toExchangeRate() }
                CurrencyQuote(
                    selectedCurrency = baseSymbol,
                    exchangeRateEntries = rates,
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

    override suspend fun getFavoriteCurrencyQuotes(map: Map<String, List<String>>): Flow<Resource<List<ExchangeRateEntry>>> {
        return flow {
            val mutableResult = mutableListOf<ExchangeRateEntry>()

            map.entries.forEach {
                val base = it.key
                val symbolsStr = it.value.joinToString(",")
                //Log.i("hehe","load data for ${base}, values = [$symbolsStr]")

                //emit(Resource.Loading(true))
                val favExchangeRates = try {
                    // это для реальных данных
                    /*val response = currencyApi.getLatestQuotesForSymbol(
                        base = base,
                        symbols = symbolsStr
                    ).string()*/
                    // это для фейковых в точности повторяющих апишные данных
                    val response = getFakeFavExchangeRatesResponse(base = base, symbols = symbolsStr)
                    //Log.d("hehe","responseFavs = $response")
                    val rates = parseJson(base, response).map { quote -> quote.toFavExchangeRate() }
                    //Log.v("hehe","received rates = $rates")
                    rates
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                    null
                }
                favExchangeRates?.let { rates ->
                    mutableResult.addAll(rates)
                }
            }
            Log.d("hehe","resulting rates = $mutableResult")
            emit(
                Resource.Success(
                    data = mutableResult
                )
            )
            //emit(Resource.Loading(false))
        }
    }

    override suspend fun addToFavorites(favoriteCurrencyPair: FavoriteCurrencyPair) {
        dao.insertFavoritePair(favoriteCurrencyPair)
    }

    override suspend fun removeFromFavorites(favoriteCurrencyPair: FavoriteCurrencyPair) {
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
