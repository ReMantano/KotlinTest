package com.example.kotlintest.api.CryptoCompare

import com.google.gson.annotations.SerializedName

data class HistoryData(
        @SerializedName("Data") val data: Array<DataC>
)