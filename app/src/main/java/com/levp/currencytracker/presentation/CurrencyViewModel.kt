package com.levp.currencytracker.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levp.currencytracker.domain.CurrencyRepo
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepo
):ViewModel() {

    var state by mutableStateOf(CurrencyState())

    fun getCurrencyQuotes(
        symbol: SupportedSymbols
    ){
        viewModelScope.launch {
            repository.getLatestCurrencyQuotes(symbol.name).collect{ result->
                when(result){
                    is Resource.Error -> {
                        Log.d("hehe","network issue")
                    }
                    is Resource.Loading -> {
                        Log.i("hehe", "loading")
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i("hehe", "successs!!!!")
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
}