package com.levp.currencytracker.data.repository

import android.util.Log
import com.levp.currencytracker.data.networking.CurrencyApi
import com.levp.currencytracker.domain.CurrencyQuote
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepoImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) : CurrencyRepo {
    override suspend fun getLatestCurrencyQuotes(symbol: String): Flow<Resource<List<CurrencyQuote>>> {
        return flow {
            Log.i("hehe","start flow")
            emit(Resource.Loading(true))
            val remoteListings = try {
                val response = currencyApi.getLatestQuotesForSymbol(symbol)
                Log.d("hehe","response = $response")
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
        }
    }
}