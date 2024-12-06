package com.levp.currencytracker.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.levp.currencytracker.data.util.FAVORITES_TABLE_NAME

@Entity(
    tableName = FAVORITES_TABLE_NAME,
    indices = [Index(
        value = ["symbol1", "symbol2"],
        unique = true
    )]
)
data class FavoriteCurrencyPair(
    val symbol1: String,
    val symbol2: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long,
)
