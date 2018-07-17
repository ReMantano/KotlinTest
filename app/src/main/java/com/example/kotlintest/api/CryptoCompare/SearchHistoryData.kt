package com.example.kotlintest.api.CryptoCompare

import com.example.kotlintest.until.StockRes

class SearchHistoryData(val api: CryptoCompareApi) {
    fun getHistoryDataDay(limit: Int): io.reactivex.Observable<HistoryData>
            = api.getHistoryDay(StockRes.EXTRA_COIN_SYMBOL, "USD", limit)

    fun getHistoryDataHour(limit: Int): io.reactivex.Observable<HistoryData>
            = api.getHistoryHour(StockRes.EXTRA_COIN_SYMBOL, "USD", limit)

    fun getHistoryDataMinute(limit: Int): io.reactivex.Observable<HistoryData>
            = api.getHistoryMinute(StockRes.EXTRA_COIN_SYMBOL, "USD", limit)
}