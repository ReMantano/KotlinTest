package com.example.kotlintest.api.CryptoCompare

class SearchHistoryData(val api: CryptoCompareApi) {
    fun getHistoryData(limit: Int): io.reactivex.Observable<HistoryData> = api.getHistory("BTC", "USD", limit)
}