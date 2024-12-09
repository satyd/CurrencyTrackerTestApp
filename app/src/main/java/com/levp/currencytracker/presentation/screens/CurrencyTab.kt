package com.levp.currencytracker.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.levp.currencytracker.presentation.CurrencyState
import com.levp.currencytracker.presentation.CurrencyViewModel
import com.levp.currencytracker.presentation.components.CurrencyHeader
import com.levp.currencytracker.presentation.elements.CurrencyListItem
import com.levp.currencytracker.presentation.elements.SimpleDivider
import com.levp.currencytracker.ui.theme.clDivider
import com.levp.currencytracker.ui.theme.clMainBackground

@Composable
fun CurrencyTab(
    innerPadding: PaddingValues,
    state: CurrencyState,
    viewModel: CurrencyViewModel,
    onFilterBtnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(color = clMainBackground)
    ) {
        //header
        CurrencyHeader(
            selectedCurrency = state.selectedCurrency,
            onValueChange = viewModel::onSwitchValueChange,
            filterOnClick = onFilterBtnClick,
        )
        SimpleDivider()
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
                items(state.exchangeRateEntries.size) { i ->
                    val entry = state.exchangeRateEntries[i]
                    CurrencyListItem(
                        rateEntry = entry,
                        onFavoriteClick = viewModel::onFavoriteClick
                    )
                    if (i < state.exchangeRateEntries.size) {
                        Spacer(
                            modifier = Modifier.height(
                                8.dp
                            )
                        )
                    }
                }
            }
        }
        SimpleDivider()
    }
}