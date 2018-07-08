package com.example.kotlintest.api.CryptoCompare

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareApi {

    @GET("/data/histoday")
    fun getHistory(
            @Query("fsym") nameCoin: String,
            @Query("tsym") nameCur: String,
            @Query("limit") limit: Int
    ): io.reactivex.Observable<HistoryData>


    companion object Factory {

        fun create(): CryptoCompareApi {

            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://min-api.cryptocompare.com/")
                    .build()
            return retrofit.create(CryptoCompareApi::class.java)
        }
    }
}