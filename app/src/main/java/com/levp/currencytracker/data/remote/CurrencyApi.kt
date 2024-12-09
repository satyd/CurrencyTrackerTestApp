package com.levp.currencytracker.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/exchangerates_data/latest?")
    suspend fun getLatestQuotesForSymbol(
        @Query("base") base: String,
        @Query("symbols") symbols: String,
        @Query("apikey") apiKey: String = API_KEY_2
    ): ResponseBody

    companion object{
        const val API_KEY = "9LjMfuzJJpxghOVeIKlfPY6mx6FNpJs2" //used limit
        const val API_KEY_2 = "6XccQKJYlNnbpcGbr4VtDNxJS2P5fV9U"
        // на второй ключ даёт 403 Forbidden: {"message": "You cannot consume this service"}
        // видимо нужно с другого Ip его использовать

        const val BASE_URL = "https://api.apilayer.com"
    }
}