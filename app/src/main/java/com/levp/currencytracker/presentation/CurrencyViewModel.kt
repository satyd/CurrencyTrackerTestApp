package com.levp.currencytracker.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.currencytracker.data.local.FavoriteCurrencyPair
import com.levp.currencytracker.data.local.toEntry
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.domain.model.ExchangeRateEntry
import com.levp.currencytracker.domain.util.SortingOptions
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.AppDispatchersProvider.dispatchers
import com.levp.currencytracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    //в идеале можно отправлять избранные вверх списка
    fun observeFavoritePairs() = ioScope.launch {
        //в более сложных случаях лучше собирать потоки через combine
        repository.observeFavoriteQuotes()
            .map { list ->
                list.filter {
                    it.symbol1 == state.selectedCurrency.name
                }
            }.collectLatest { list ->
                setFavorites(list)
            }
    }

    fun setFavorites(list: List<FavoriteCurrencyPair>) {
        val checkList = list.map { it.copy(id = 0) }
        val entriesWithFavorites = state.exchangeRateEntries.map {
            val checkEntry = FavoriteCurrencyPair(it.symbol1, it.symbol2, id = 0)
            if (checkEntry in checkList) {
                it.copy(isFavorite = true)
            } else {
                it.copy(isFavorite = false)
            }
        }
        state = state.copy(
            exchangeRateEntries = entriesWithFavorites
        )
    }

    fun sortCurrencyList(index: Int) {
        state = state.copy(
            selectedFilter = index,
            isLoading = true
        )
        val sort: SortingOptions = SortingOptions.entries[index]
        Log.d("hehe", "sort list ind = $index huh ${sort.name}")
        val sorted = when (sort) {
            SortingOptions.CodeAsc -> {
                state.exchangeRateEntries.sortedBy { it.symbol2 }
            }

            SortingOptions.CodeDesc -> {
                state.exchangeRateEntries.sortedByDescending { it.symbol2 }
            }

            SortingOptions.QuoteAsc -> {
                state.exchangeRateEntries.sortedBy { it.rate }
            }

            SortingOptions.QuoteDesc -> {
                state.exchangeRateEntries.sortedByDescending { it.rate }
            }
        }
        state = state.copy(
            exchangeRateEntries = sorted,
            isLoading = false
        )
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
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resource.Success -> {
                        result.data?.let {
                            state = state.copy(
                                selectedCurrency = it.selectedCurrency,
                                exchangeRateEntries = it.exchangeRateEntries
                            )
                            val list =
                                repository.getFavoriteQuotesForSymbol(it.selectedCurrency.name)
                            setFavorites(list)
                            //sortCurrencyList(state.selectedFilter)
                        }
                    }
                }
            }
        }
    }

    fun onSwitchValueChange(symbol: SupportedSymbols) {
        viewModelScope.launch {
            state = state.copy(
                selectedCurrency = symbol
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

    fun onRemoveFavoriteClick(rateEntry: ExchangeRateEntry) {
        if (rateEntry.isFavorite) {
            removeFromFavorites(rateEntry)
            state = state.copy(
                favoriteExchangeRates = state.favoriteExchangeRates.filter { it != rateEntry }
            )
        }
    }

    fun addToFavorites(rateEntry: ExchangeRateEntry) = ioScope.launch {
        //Log.i("hehe", "add favorite $rateEntry")
        repository.addToFavorites(FavoriteCurrencyPair(rateEntry.symbol1, rateEntry.symbol2, 0))
    }

    fun removeFromFavorites(rateEntry: ExchangeRateEntry) = ioScope.launch {
        //Log.i("hehe", "remove favorite $rateEntry")
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

    fun getAllFavoritesRates() = ioScope.launch {
        val allFavorites = repository.getFavoriteQuotes()
        //val favPairs = allFavorites.map { Pair(it.symbol1, it.symbol2) }
        val map = allFavorites.groupBy(
            keySelector = { it.symbol1 },
            valueTransform = { it.symbol2 }
        )
        // Здесь выполняется тот же запрос на сервер для получения актуальных курсов
        // только для каждого набора symbol1: { symbol0..symbolN } из списка
        // но у апишки всего 100 запросов в месяц, поэтому данные фейковые использую

        withContext(viewModelScope.coroutineContext) {
            repository.getFavoriteCurrencyQuotes(map = map).collect { result ->
                when (result) {
                    is Resource.Error -> { Log.d("hehe", "network issue") }
                    is Resource.Loading -> { Log.v("hehe","loadin") }
                    is Resource.Success -> {
                        Log.i("hehe","success")
                        result.data?.let {
                            state = state.copy(
                                favoriteExchangeRates = it
                            )
                        }
                    }
                }
            }
        }
    }
}