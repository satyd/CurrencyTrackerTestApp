package com.levp.currencytracker.domain

import com.levp.currencytracker.util.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {

    suspend fun getLatestCurrencyQuotes(
        symbol:String
    ): Flow<Resource<List<CurrencyQuote>>>

}