package com.example.kotlintest.api.CoinMarketCap

import com.google.gson.annotations.SerializedName

data class Coin(
        @SerializedName("name") val name: String,
        @SerializedName("symbol") val symbol: String,
        @SerializedName("price_usd") val price: Double,
        @SerializedName("percent_change_1h") val percent_1h: Double,
        @SerializedName("percent_change_24h") val percent_24h: Double,
        @SerializedName("percent_change_7d") val percent_7d: Double
)