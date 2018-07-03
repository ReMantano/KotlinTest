package com.example.kotlintest.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Vladislav on 03.07.2018.
 */
interface CoinMarketCapApi {


    @GET("/ticker/")
    fun GetCoins(
            @Query("start") start: Int,
            @Query("limit") limit: Int): io.reactivex.Observable<List<Coin>>

    companion object Factory {

        fun create(): CoinMarketCapApi {
            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://api.coinmarketcap.com/v1")
                    .build()
            return retrofit.create(CoinMarketCapApi::class.java)
        }
    }

}