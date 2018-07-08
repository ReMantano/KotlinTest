package com.example.kotlintest.api

import com.example.kotlintest.api.CoinMarketCap.CoinMarketCapApi
import com.example.kotlintest.api.CoinMarketCap.SearchCoins
import com.example.kotlintest.api.CryptoCompare.CryptoCompareApi
import com.example.kotlintest.api.CryptoCompare.SearchHistoryData

/**
 * Created by Vladislav on 03.07.2018.
 */
object SearchCoinsProvider {
    fun providerSearchCoins(): SearchCoins = SearchCoins(CoinMarketCapApi.create())
    fun provideSearchHistoryData(): SearchHistoryData = SearchHistoryData(CryptoCompareApi.create())
}