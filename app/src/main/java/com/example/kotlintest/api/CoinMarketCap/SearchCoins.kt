package com.example.kotlintest.api.CoinMarketCap

class SearchCoins(val api: CoinMarketCapApi) {
    fun getCoins(start: Int): io.reactivex.Observable<List<Coin>> = api.GetCoins(start, 20)
}