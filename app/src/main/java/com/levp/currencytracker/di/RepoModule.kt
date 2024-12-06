package com.levp.currencytracker.di

import com.levp.currencytracker.data.repository.CurrencyRepoImpl
import com.levp.currencytracker.domain.CurrencyRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindCurrencyRepository(
        currencyRepoImpl: CurrencyRepoImpl
    ): CurrencyRepo

}