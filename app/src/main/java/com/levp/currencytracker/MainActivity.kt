package com.levp.currencytracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.levp.currencytracker.domain.util.SupportedSymbols
import com.levp.currencytracker.presentation.CurrencyViewModel
import com.levp.currencytracker.presentation.Header
import com.levp.currencytracker.ui.theme.CurrencyTrackerTheme
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
                    Column(modifier = Modifier.padding(innerPadding)) {
                        //header
                        Header(
                            { Log.d("hehe","switch click")},
                            {
                                Log.d("hehe","flter clck")
                                viewModel.getCurrencyQuotes(SupportedSymbols.USD)
                            },
                        )
                        //content
                        LazyColumn { }
                        //footer
                    }
                }
            }
        }
    }
}
