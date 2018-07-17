package com.example.kotlintest

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.kotlintest.until.HomeScreens
import com.example.kotlintest.until.ShowNotConnectItems
import com.example.kotlintest.until.StockRes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, ShowNotConnectItems {

    lateinit var notConnectionBlock: LinearLayout
    lateinit var notConnectionProgressBar: ProgressBar
    lateinit var notConnectionButon: Button

    override fun Show(load: Boolean) {

        if (load) {
            notConnectionBlock.visibility = View.INVISIBLE
            notConnectionProgressBar.visibility = View.INVISIBLE
        } else {
            notConnectionBlock.visibility = View.VISIBLE
            notConnectionProgressBar.visibility = View.INVISIBLE
        }
    }

    override fun restart() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
            StockRes.current = savedInstanceState.getSerializable(StockRes.EXTRA_SHOW_SCREEN) as? HomeScreens ?: HomeScreens.DEFAULT_SCREEN

        navigation.setOnNavigationItemSelectedListener(this)
        navigation.selectedItemId = StockRes.current.ItemId

        notConnectionButon = findViewById<Button>(R.id.notConnectionButton)
        notConnectionBlock = findViewById<LinearLayout>(R.id.notConnectionBlock)
        notConnectionProgressBar = findViewById<ProgressBar>(R.id.notConnectionProgressBar)

        notConnectionButon.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                notConnectionProgressBar.visibility = View.VISIBLE
                notConnectionBlock.visibility = View.INVISIBLE
                var th: Thread = Thread(object : Runnable {
                    override fun run() {
                        selectScreen(HomeScreens.FIRST)
                    }
                })
                th.run()

            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        StockRes.current = intent.getBundleExtra(StockRes.EXTRA_SHOW_SCREEN) as? HomeScreens ?: HomeScreens.DEFAULT_SCREEN
        selectScreen(StockRes.current)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        outState.putSerializable(StockRes.EXTRA_SHOW_SCREEN, StockRes.current)
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

        StockRes.current = screen
        when(screen){

            HomeScreens.FIRST -> placeFragment(StockRes.FRAGMENT_TAG_FIRST)
            HomeScreens.SECOND -> placeFragment(StockRes.FRAGMENT_TAG_SECOND)
            HomeScreens.THIRD -> placeFragment(StockRes.FRAGMENT_TAG_THIRD)
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
