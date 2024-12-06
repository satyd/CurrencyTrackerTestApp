package com.levp.currencytracker.data.repository

import android.util.Log
import com.levp.currencytracker.data.networking.CurrencyApi
import com.levp.currencytracker.data.networking.QuoteDto
import com.levp.currencytracker.data.networking.toExchangeRate
import com.levp.currencytracker.domain.CurrencyQuote
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepoImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) : CurrencyRepo {
    override suspend fun getLatestCurrencyQuotes(base: String): Flow<Resource<CurrencyQuote>> {
        return flow {
            Log.i("hehe", "start flow for base = $base")
            val symbols = SupportedSymbols.entries.filter { it.name != base }.map { it.name }
            val symbolsStr = symbols.joinToString(",")

            Log.i("hehe", "symbols = ${symbolsStr}")
            emit(Resource.Loading(true))
            val currencyQuotes = try {
                val response = currencyApi.getLatestQuotesForSymbol(
                    base = base,
                    symbols = symbolsStr
                )

                //Log.d("hehe","response = ${response.string()}")
                val rates = parseJson(response.string()).map { it.toExchangeRate() }
                CurrencyQuote(
                    currencyName = base,
                    exchangeRates = rates,
                    isFavorite = false
                )

                //companyListingsParser.parse(response.byteStream())
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
}

fun parseJson(jsonString: String): MutableList<QuoteDto> {
    val jsonObject = JSONObject(jsonString)
    val ratesObject = jsonObject.getJSONObject("rates")
    val keys = ratesObject.keys()
    val result = mutableListOf<QuoteDto>()
    while (keys.hasNext()) {
        val key = keys.next() as String
        val value = ratesObject.get(key).toString()
        result.add(QuoteDto(currencyName = key, rate = value))

    }
    Log.w("hehe", "res = ${result}")
    return result
}


