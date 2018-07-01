package com.example.kotlintest

import android.support.v4.app.Fragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.example.kotlintest.fragments.EmptyFragment
import com.example.kotlintest.fragments.ListFragment
import com.example.kotlintest.until.HomeScreens
import com.example.kotlintest.until.fragmentTag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {



    companion object {

        private const val EXTRA_SCREEN_DATA = "data"
        private const val EXTRA_SHOW_SCREEN = "screen"

    }

    private val FRAGMENT_TAG_FIRST = fragmentTag<ListFragment>()
    private val FRAGMENT_TAG_SECOND = fragmentTag<EmptyFragment>()
    private val FRAGMENT_TAG_THIRD = fragmentTag<ListFragment>()

    private var current: HomeScreens = HomeScreens.DEFAULT_SCREEN


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
            current = savedInstanceState.getSerializable(EXTRA_SHOW_SCREEN) as? HomeScreens ?: HomeScreens.DEFAULT_SCREEN

        navigation.setOnNavigationItemSelectedListener(this)
        navigation.selectedItemId = current.ItemId
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        current = intent.getBundleExtra(EXTRA_SHOW_SCREEN) as? HomeScreens ?: HomeScreens.DEFAULT_SCREEN
        selectScreen(current)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        outState.putSerializable(EXTRA_SHOW_SCREEN,current)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.action_first -> selectScreen(HomeScreens.FIRST)
            R.id.action_second -> selectScreen(HomeScreens.SECOND)
            R.id.action_third -> selectScreen(HomeScreens.THIRD)
        }


       return true
    }

    private fun selectScreen(screen : HomeScreens){

        current = screen
        when(screen){

            HomeScreens.FIRST -> placeFragment(FRAGMENT_TAG_FIRST)
            HomeScreens.SECOND -> placeFragment(FRAGMENT_TAG_SECOND)
            HomeScreens.THIRD -> placeFragment(FRAGMENT_TAG_THIRD)
        }
    }


    fun placeFragment(fragmentTag: String, args : Bundle? = null) : Fragment?{

        if (isFinishing)
            return null

        val transaction = supportFragmentManager.beginTransaction()

        val fragment = Fragment.instantiate(this,fragmentTag,args)

        transaction.setCustomAnimations(
                android.R.anim.fade_in,android.R.anim.fade_out,
                android.R.anim.fade_out,android.R.anim.fade_in)
        transaction.replace(R.id.conteiner_fragment,fragment,fragmentTag)
        transaction.commit()

     return null
    }

}
