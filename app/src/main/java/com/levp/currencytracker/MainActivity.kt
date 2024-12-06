package com.levp.currencytracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.presentation.CurrencyViewModel
import com.levp.currencytracker.presentation.Header
import com.levp.currencytracker.presentation.components.CurrencyListItem
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clMainBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: CurrencyViewModel = hiltViewModel()
                    val state = viewModel.state

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(color = clMainBackground)
                    ) {
                        //header
                        Header(
                            {
                                Log.d("hehe", "switch clck")
                                viewModel.getCurrencyQuotes(SupportedSymbols.USD)
                            },
                            { Log.d("hehe", "filter click") },
                        )
                        //content
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 16.dp)
                                .background(color = clMainBackground)
                        ) {
                            items(state.currencyQuote.exchangeRates.size) { i ->
                                val symbol = state.currencyQuote.exchangeRates[i].symbol
                                val rate = state.currencyQuote.exchangeRates[i].rate
                                CurrencyListItem(
                                    currencyName = symbol,
                                    exchangeRate = rate.toString(),
                                    isFavorite = false
                                )
                                if (i < state.currencyQuote.exchangeRates.size) {
                                    Spacer(
                                        modifier = Modifier.height(
                                            8.dp
                                        )
                                    )
                                }
                            }
                        }
                        //footer
                    }
                }
            }
        }
    }
}
