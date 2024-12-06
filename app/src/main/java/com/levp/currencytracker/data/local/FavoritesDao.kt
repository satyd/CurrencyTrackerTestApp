package com.levp.currencytracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levp.currencytracker.data.util.FAVORITES_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePair(
        currencyPair: FavoriteCurrencyPair
    )

    @Query("DELETE FROM $FAVORITES_TABLE_NAME")
    suspend fun clearFavorites()

    @Delete
    suspend fun deleteFavoritePair(currencyPair: FavoriteCurrencyPair)

    @Query("DELETE FROM $FAVORITES_TABLE_NAME WHERE symbol1 LIKE :sym1 AND symbol2 LIKE :sym2")
    suspend fun removeFavoritePair(sym1: String, sym2: String)

    @Query("SELECT * FROM $FAVORITES_TABLE_NAME")
    suspend fun getAllFavorites(): List<FavoriteCurrencyPair>

    @Query("SELECT * FROM $FAVORITES_TABLE_NAME WHERE symbol1 LIKE :symbol")
    suspend fun getFavoritesForSymbol(symbol: String): List<FavoriteCurrencyPair>

    @Query("SELECT * FROM $FAVORITES_TABLE_NAME")
    fun observeFavorites(): Flow<List<FavoriteCurrencyPair>>

}