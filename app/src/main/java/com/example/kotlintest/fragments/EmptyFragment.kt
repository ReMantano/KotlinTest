package com.example.kotlintest.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.MainActivity
import com.example.kotlintest.R
import com.example.kotlintest.adapter.CalculateAdapter
import kotlinx.android.synthetic.main.fragment_calculate.*

class EmptyFragment : Fragment(), View.OnClickListener {

    companion object {
        var convert_number: Double = 1.0
    }

    override fun onClick(p0: View?) {
        convert_number = editText.text.toString().toDouble()
        adapter.notifyDataSetChanged()
    }

    val colors: Array<Int> = arrayOf(Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0),
            Color.argb(255, 0, 255, 255), Color.argb(255, 100, 100, 0),
            Color.argb(255, 0, 255, 100), Color.argb(255, 10, 255, 100))

    private lateinit var adapter: CalculateAdapter
    private lateinit var llm: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_calculate, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = llm

        imageView.text = MainActivity.EXTRA_COIN_SYMBOL
        textView.text = MainActivity.EXTRA_COIN_NAME

        val symbol: Int = MainActivity.EXTRA_COIN_NAME[0].toInt() + MainActivity.EXTRA_COIN_NAME[1].toInt()
        var draw: Drawable = imageView.background
        var wrap: Drawable = DrawableCompat.wrap(draw)
        DrawableCompat.setTint(wrap, colors[symbol % colors.size])

        button.setOnClickListener(this)

        adapter = CalculateAdapter()
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }




}
