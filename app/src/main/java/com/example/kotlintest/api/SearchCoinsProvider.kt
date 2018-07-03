package com.example.kotlintest.api

/**
 * Created by Vladislav on 03.07.2018.
 */
object SearchCoinsProvider {
    fun providerSearchCoins(): SearchCoins = SearchCoins(CoinMarketCapApi.create())
}