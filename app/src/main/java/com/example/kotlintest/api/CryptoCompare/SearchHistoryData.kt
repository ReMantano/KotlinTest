package com.example.kotlintest.api.CryptoCompare

import com.example.kotlintest.MainActivity

class SearchHistoryData(val api: CryptoCompareApi) {
    fun getHistoryDataDay(limit: Int): io.reactivex.Observable<HistoryData>
            = api.getHistoryDay(MainActivity.EXTRA_COIN_SYMBOL, "USD", limit)

    fun getHistoryDataHour(limit: Int): io.reactivex.Observable<HistoryData>
            = api.getHistoryHour(MainActivity.EXTRA_COIN_SYMBOL, "USD", limit)

    fun getHistoryDataMinute(limit: Int): io.reactivex.Observable<HistoryData>
            = api.getHistoryMinute(MainActivity.EXTRA_COIN_SYMBOL, "USD", limit)
}