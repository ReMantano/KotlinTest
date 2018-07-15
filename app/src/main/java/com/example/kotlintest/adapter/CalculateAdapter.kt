package com.example.kotlintest.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.MainActivity
import com.example.kotlintest.R
import com.example.kotlintest.api.CoinMarketCap.Coin
import com.example.kotlintest.fragments.EmptyFragment
import com.example.kotlintest.fragments.ListFragment
import kotlinx.android.synthetic.main.item_list.view.*


class CalculateAdapter : RecyclerView.Adapter<CalculateAdapter.TextViewHolder>() {

    var data: MutableList<Coin?> = ListFragment.list

    val colors: Array<Int> = arrayOf(Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0),
            Color.argb(255, 0, 255, 255), Color.argb(255, 100, 100, 0),
            Color.argb(255, 0, 255, 100), Color.argb(255, 10, 255, 100))


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculateAdapter.TextViewHolder {
        var viewHolder: RecyclerView.ViewHolder


        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        viewHolder = TextViewHolder(v)

        return viewHolder
    }


    override fun onBindViewHolder(holder: CalculateAdapter.TextViewHolder, position: Int) {


        val model: Coin = data[position]!!
        val symbol: Int = model.name[0].toInt() + model.name[1].toInt()
        val convert: Double = MainActivity.EXTRA_COIN_PRICE * EmptyFragment.convert_number / model.price

        holder.nameView.text = model.name
        holder.priceView.text = ""
        holder.procentView.text = convert.toString()
        holder.imageView.text = model.symbol

        var draw: Drawable = holder.imageView.background
        var wrap: Drawable = DrawableCompat.wrap(draw)
        DrawableCompat.setTint(wrap, colors[symbol % colors.size])

    }

    override fun getItemCount(): Int = data.size


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView = view.list_name!!
        val priceView = view.list_price!!
        val imageView = view.list_image!!
        val procentView = view.list_procent!!

    }


}