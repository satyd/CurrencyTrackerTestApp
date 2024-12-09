package com.levp.currencytracker.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levp.currencytracker.presentation.CurrencyState
import com.levp.currencytracker.presentation.CurrencyViewModel
import com.levp.currencytracker.presentation.components.FavoritesHeader
import com.levp.currencytracker.presentation.elements.CurrencyListItem
import com.levp.currencytracker.presentation.elements.SimpleDivider
import com.levp.currencytracker.ui.theme.clDivider
import com.levp.currencytracker.ui.theme.clMainBackground

@Composable
fun FavoritesTab(
    innerPadding: PaddingValues,
    state: CurrencyState,
    viewModel: CurrencyViewModel
){
    Log.d("hehe","favstate = ${state.favoriteExchangeRates}")
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(color = clMainBackground)
    ) {
        FavoritesHeader()
        SimpleDivider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
                .background(color = clMainBackground)
        ) {
            items(state.favoriteExchangeRates.size) { i ->
                val entry = state.favoriteExchangeRates[i]
                CurrencyListItem(
                    rateEntry = entry,
                    isFavoritesTab = true,
                    onFavoriteClick = viewModel::onRemoveFavoriteClick
                )

                if (i < state.favoriteExchangeRates.size) {
                    Spacer(
                        modifier = Modifier.height(
                            8.dp
                        )
                    )
                }
            }
        }
        SimpleDivider()
    }
}