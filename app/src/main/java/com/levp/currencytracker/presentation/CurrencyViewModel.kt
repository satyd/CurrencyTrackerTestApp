package com.levp.currencytracker.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.currencytracker.data.local.FavoriteCurrencyPair
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.domain.model.CurrencyQuote
import com.levp.currencytracker.domain.model.ExchangeRateEntry
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.AppDispatchersProvider.dispatchers
import com.levp.currencytracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepo,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val ioScope =
        CoroutineScope(viewModelScope.coroutineContext + dispatchers.io + exceptionHandler)

    private val mainScope =
        CoroutineScope(viewModelScope.coroutineContext + dispatchers.main + exceptionHandler)

    fun observeFavorites() = repository.observeFavoriteQuotes().flowOn(dispatchers.io)

    var state by mutableStateOf(CurrencyState())

    //в идеале ещё отправлять избранные вверх списка
    fun observeFavoritePairs() = ioScope.launch {
        repository.observeFavoriteQuotes()
            .map { list ->
                list.filter {
                    it.symbol1 == state.currencyQuote.selectedCurrency.name
                }
            }.collect { list ->
                val checkList = list.map { it.copy(id = 0) }
                //Log.i("heh","start filter ${state.currencyQuote.exchangeRateEntries}")
                val entriesWithFavorites = state.currencyQuote.exchangeRateEntries.map {
                    val checkEntry = FavoriteCurrencyPair(it.symbol1, it.symbol2, id = 0)
                    if (checkEntry in checkList) {
                        it.copy(isFavorite = true)
                    } else {
                        it.copy(isFavorite = false)
                    }
                }
                val newQuote = state.currencyQuote.copy(exchangeRateEntries = entriesWithFavorites)
                state = state.copy(
                    currencyQuote = newQuote
                )
                //Log.i("heh","end filter ${newQuote.exchangeRateEntries}")
            }
    }

    fun getCurrencyQuotes(
        symbol: SupportedSymbols
    ) {
        viewModelScope.launch {
            repository.getLatestCurrencyQuotes(symbol).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        //Log.d("hehe", "network issue")
                    }

                    is Resource.Loading -> {
                        //Log.i("hehe", "loading")
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resource.Success -> {
                        //Log.i("hehe", "successs!!!!")
                        result.data?.let {
                            state = state.copy(
                                currencyQuote = it
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSwitchValueChange(symbol: SupportedSymbols) {
        viewModelScope.launch {
            state = state.copy(
                currencyQuote = state.currencyQuote.copy(
                    selectedCurrency = symbol
                )
            )
            getCurrencyQuotes(symbol)
        }
    }

    fun onFavoriteClick(rateEntry: ExchangeRateEntry) {
        if (rateEntry.isFavorite) {
            removeFromFavorites(rateEntry)
        } else {
            addToFavorites(rateEntry)
        }
    }

    fun addToFavorites(rateEntry: ExchangeRateEntry) = ioScope.launch {
        Log.i("hehe", "add favorite $rateEntry")
        repository.addToFavorites(FavoriteCurrencyPair(rateEntry.symbol1, rateEntry.symbol2, 0))
    }

    fun removeFromFavorites(rateEntry: ExchangeRateEntry) = ioScope.launch {
        Log.i("hehe", "remove favorite $rateEntry")
        repository.removeFromFavorites(
            FavoriteCurrencyPair(
                rateEntry.symbol1,
                rateEntry.symbol2,
                0
            )
        )
    }

    fun getFavoritesForSymbol(symbol: String) = ioScope.launch {
        repository.getFavoriteQuotesForSymbol(symbol)
    }
}