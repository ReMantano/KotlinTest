package com.example.kotlintest.api.CryptoCompare

import com.google.gson.annotations.SerializedName
import com.jjoe64.graphview.series.DataPoint

data class DataC(
        @SerializedName("time") val time: Double,
        @SerializedName("close") val close: Double
) : DataPoint(time, close)
