package com.example.kotlintest.until

import android.graphics.Color
import com.example.kotlintest.fragments.EmptyFragment
import com.example.kotlintest.fragments.GraphFragment
import com.example.kotlintest.fragments.ListFragment


class StockRes {
    companion object {

        val FRAGMENT_TAG_FIRST = fragmentTag<ListFragment>()
        val FRAGMENT_TAG_SECOND = fragmentTag<GraphFragment>()
        val FRAGMENT_TAG_THIRD = fragmentTag<EmptyFragment>()

        var current: HomeScreens = HomeScreens.DEFAULT_SCREEN

        var convert_number: Double = 1.0
        var EXTRA_COIN_SYMBOL = "BTC"
        var EXTRA_COIN_NAME = "Bitcoin"
        var EXTRA_COIN_PRICE = 6500.0
        val EXTRA_SHOW_SCREEN = "screen"

        val TYPE_TEXT = 1
        val TYPE_PROGRESS = 0

        val colors: Array<Int> = arrayOf(Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0),
                Color.argb(255, 0, 255, 255), Color.argb(255, 100, 100, 0),
                Color.argb(255, 0, 255, 100), Color.argb(255, 10, 255, 100))

    }
}