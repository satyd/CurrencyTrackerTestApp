package com.levp.currencytracker.data.networking

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/exchangerates_data/latest")
    suspend fun getLatestQuotesForSymbol(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object{
        const val API_KEY = "9LjMfuzJJpxghOVeIKlfPY6mx6FNpJs2"
        const val BASE_URL = "https://api.apilayer.com/"
    }
}