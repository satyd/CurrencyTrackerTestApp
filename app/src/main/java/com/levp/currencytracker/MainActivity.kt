package com.levp.currencytracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.presentation.CurrencyViewModel
import com.levp.currencytracker.presentation.components.CurrencyHeader
import com.levp.currencytracker.presentation.components.FavoritesHeader
import com.levp.currencytracker.presentation.elements.CurrencyListItem
import com.levp.currencytracker.presentation.elements.TabBarItem
import com.levp.currencytracker.presentation.elements.TabView
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clDivier
import com.levp.currencytracker.ui.theme.clMainBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyTrackerTheme {
                val currenciesTab = TabBarItem(
                    title = "Currencies",
                    selectedIcon = ImageVector.vectorResource(R.drawable.currencies_selected),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.currencies_unselected)
                )
                val favoritesTab = TabBarItem(
                    title = "Favorites",
                    selectedIcon = ImageVector.vectorResource(R.drawable.favorites_selected),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.favorites_unselected)
                )
                val tabItems = listOf(currenciesTab, favoritesTab)

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize().background(color = clMainBackground),
                    bottomBar = {
                        TabView(tabItems, navController)
                    }
                ) { innerPadding ->
                    val viewModel: CurrencyViewModel = hiltViewModel()
                    val state = viewModel.state
                    LaunchedEffect(key1 = Unit) {
                        //в идеале сохранять последнюю выбранную в префы и загружать из них #hehe
                        viewModel.observeFavoritePairs()
                        viewModel.getCurrencyQuotes(SupportedSymbols.USD)
                    }
                    NavHost(navController = navController, startDestination = currenciesTab.title) {
                        composable(currenciesTab.title) {
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .background(color = clMainBackground)
                            ) {
                                //header
                                CurrencyHeader(
                                    selectedCurrency = state.currencyQuote.selectedCurrency,
                                    onValueChange = viewModel::onSwitchValueChange,
                                    { Log.d("hehe", "filter click") },
                                )
                                Spacer(modifier = Modifier.height(1.dp).background(color = clDivier))
                                if (state.isLoading) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = clMainBackground),
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                } else {
                                    //content
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(all = 16.dp)
                                            .background(color = clMainBackground)
                                    ) {
                                        items(state.currencyQuote.exchangeRateEntries.size) { i ->
                                            val entry = state.currencyQuote.exchangeRateEntries[i]
                                            CurrencyListItem(
                                                rateEntry = entry,
                                                onFavoriteClick = viewModel::onFavoriteClick
                                            )
                                            if (i < state.currencyQuote.exchangeRateEntries.size) {
                                                Spacer(
                                                    modifier = Modifier.height(
                                                        8.dp
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(1.dp).background(color = clDivier))
                            }
                        }
                        composable(favoritesTab.title) {//hehe favorites add request for pairs
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .background(color = clMainBackground)
                            ) {
                                FavoritesHeader()
                                Spacer(modifier = Modifier.height(1.dp).background(color = clDivier))
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(all = 16.dp)
                                        .background(color = clMainBackground)
                                ) {
                                    items(state.currencyQuote.exchangeRateEntries.size) { i ->
                                        val entry = state.currencyQuote.exchangeRateEntries[i]
                                        CurrencyListItem(
                                            rateEntry = entry,
                                            onFavoriteClick = viewModel::onFavoriteClick
                                        )
                                        if (i < state.currencyQuote.exchangeRateEntries.size) {
                                            Spacer(
                                                modifier = Modifier.height(
                                                    8.dp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
