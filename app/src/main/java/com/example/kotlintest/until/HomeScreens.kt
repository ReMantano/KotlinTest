package com.example.kotlintest.until

import com.example.kotlintest.R

enum class HomeScreens( val  ItemId : Int){

    FIRST(R.id.action_first),
    SECOND(R.id.action_second),
    THIRD(R.id.action_third);
    companion object  {
       val DEFAULT_SCREEN = FIRST
    }
}
