package com.example.kotlintest.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.api.CoinMarketCap.Coin
import com.example.kotlintest.until.StockRes
import kotlinx.android.synthetic.main.item_list.view.*

class CoinAdapter(list: MutableList<Coin?>, clickListener: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val listen = clickListener
    var data: MutableList<Coin?> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder

        if (viewType == StockRes.TYPE_TEXT) {
            val v: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list, parent, false)
            v.setOnClickListener(listen)
            viewHolder = TextViewHolder(v)
        } else {
            val v: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_bar, parent, false)
            viewHolder = ProgressViewHolder(v)
        }

        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val model = data[position]

        if (holder is TextViewHolder && model != null) {

            val symbol: Int = model.name[0].toInt() + model.name[1].toInt()

            holder.nameView.text = model.name
            holder.priceView.text = model.price.toString()
            holder.procentView.text = model.percent_24h.toString()
            holder.imageView.text = model.symbol

            if (model.percent_24h < 0) holder.procentView.setTextColor(Color.argb(255, 255, 0, 0))
            else holder.procentView.setTextColor(Color.argb(255, 0, 255, 0))

            var draw: Drawable = holder.imageView.background
            var wrap: Drawable = DrawableCompat.wrap(draw)
            DrawableCompat.setTint(wrap, StockRes.colors[symbol % StockRes.colors.size])
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = if (data.get(position) != null) StockRes.TYPE_TEXT else StockRes.TYPE_PROGRESS


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView = view.list_name!!
        val priceView = view.list_price!!
        val imageView = view.list_image!!
        val procentView = view.list_procent!!

    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)


}