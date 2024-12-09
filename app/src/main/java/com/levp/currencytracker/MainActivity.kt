package com.levp.currencytracker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.presentation.CurrencyViewModel
import com.levp.currencytracker.presentation.util.TabBarItem
import com.levp.currencytracker.presentation.util.TabView
import com.levp.currencytracker.presentation.screens.CurrencyTab
import com.levp.currencytracker.presentation.screens.FavoritesTab
import com.levp.currencytracker.presentation.screens.FilterOptionsScreen
import com.levp.currencytracker.presentation.util.Screens
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
import com.levp.currencytracker.ui.theme.clMainBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        const val SELECTED_FILTER_INDEX = "SELECTED_FILTER_INDEX"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        setContent {
            CurrencyTrackerTheme {
                val currenciesTab = TabBarItem(
                    title = "Currencies",
                    destination = Screens.Currencies.route,
                    selectedIcon = ImageVector.vectorResource(R.drawable.currencies_selected),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.currencies_unselected)
                )
                val favoritesTab = TabBarItem(
                    title = "Favorites",
                    destination = Screens.Favorites.route,
                    selectedIcon = ImageVector.vectorResource(R.drawable.favorites_selected),
                    unselectedIcon = ImageVector.vectorResource(R.drawable.favorites_unselected)
                )
                val tabItems = listOf(currenciesTab, favoritesTab)
                val navController = rememberNavController()
                val viewModel: CurrencyViewModel = hiltViewModel()
                val state = viewModel.state

                LaunchedEffect(key1 = Unit) {
                    val sortingOptionIndex = sharedPref.getInt(SELECTED_FILTER_INDEX, 0)
                    viewModel.observeFavoritePairs()
                    //в идеале сохранять последнюю выбранную в префы и загружать из них #hehe
                    viewModel.onSwitchValueChange(SupportedSymbols.USD)
                    viewModel.sortCurrencyList(sortingOptionIndex)
                    viewModel.getAllFavoritesRates()
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = clMainBackground),
                    bottomBar = {
                        val currentBackStackEntry = navController.currentBackStackEntryAsState()
                        val currentDestination = currentBackStackEntry.value?.destination
                        if (currentDestination?.route in tabItems.map { it.destination }) {
                            TabView(
                                tabBarItems = tabItems,
                                navController = navController
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Currencies.route
                    ) {
                        composable(
                            route = Screens.Currencies.route
                        ) {
                            CurrencyTab(
                                innerPadding = innerPadding,
                                state = state,
                                viewModel = viewModel,
                                onFilterBtnClick = {
                                    navController.navigate(Screens.Filters.route)
                                }
                            )
                        }
                        composable(
                            route = Screens.Favorites.route
                        ) {
                            //hehe favorites add request for pairs
                            FavoritesTab(
                                innerPadding = innerPadding,
                                state = state,
                                viewModel = viewModel
                            )
                        }
                        composable(
                            route = Screens.Filters.route
                        ) {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = clMainBackground)
                            ) { innerPadding ->
                                //тут 2 варианта: либо по умолчанию данные не отсортированы и есть опция "без сортировки"
                                //либо данные всегда как-то сортируются, тогда нужно это как-то помечать, чтобы пользователь видел
                                FilterOptionsScreen(
                                    navController = navController,
                                    selectedItem = viewModel.state.selectedFilter,
                                    onApplyClick = { index ->
                                        navController.popBackStack()
                                        with (sharedPref.edit()) {
                                            putInt(SELECTED_FILTER_INDEX, index)
                                            apply()
                                        }
                                        viewModel.sortCurrencyList(index)
                                    },
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
