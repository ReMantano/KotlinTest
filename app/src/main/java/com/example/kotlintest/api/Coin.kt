package com.example.kotlintest.api

import com.google.gson.annotations.SerializedName

/**
 * Created by Vladislav on 03.07.2018.
 */
data class Coin(
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("price_usd") val price: Double,
        @SerializedName("percent_change_24h") val percent_24h: Double
)