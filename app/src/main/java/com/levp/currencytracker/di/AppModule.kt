package com.levp.currencytracker.di

//import retrofit2.converter.moshi.MoshiConverterFactory
import com.levp.currencytracker.data.networking.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApi {
        return Retrofit.Builder()
            .baseUrl(CurrencyApi.BASE_URL)
            //.addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    /*@Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stock.db"
        ).build()
    }*/


}