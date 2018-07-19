package com.example.kotlintest.until

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

        var EXTRA_TIME_PERIOD_SELECT = "24H"

        val TYPE_TEXT = 1
        val TYPE_PROGRESS = 0


    }
}