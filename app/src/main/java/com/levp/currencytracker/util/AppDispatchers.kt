package com.levp.currencytracker.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

class AppDispatchers {
    val main: MainCoroutineDispatcher = Dispatchers.Main
    val default: CoroutineDispatcher = Dispatchers.Default
    val io: CoroutineDispatcher = Dispatchers.IO
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}

object AppDispatchersProvider {
    val dispatchers = AppDispatchers()
}