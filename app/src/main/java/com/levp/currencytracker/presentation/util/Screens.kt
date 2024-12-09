package com.levp.currencytracker.presentation.util

sealed class Screens(val route: String) {
    data object Currencies : Screens("currencies")
    data object Favorites : Screens("favorites")
    data object Filters : Screens("filters")
}