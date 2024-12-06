package com.levp.currencytracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCurrencyPair::class],
    version = 1
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract val dao: FavoritesDao
}