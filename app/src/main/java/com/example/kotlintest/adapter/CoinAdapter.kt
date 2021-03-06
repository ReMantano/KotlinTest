package com.example.kotlintest.adapter

import android.graphics.Color
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
        val viewHolder: RecyclerView.ViewHolder

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

            holder.name.text = model.name
            holder.price.text = model.price.toString()
            holder.procent_1H.text = model.percent_1h.toString()
            holder.procent_24H.text = model.percent_24h.toString()
            holder.procent_7D.text = model.percent_7d.toString()
            holder.symbol.text = model.symbol

            if (model.percent_1h < 0) holder.procent_1H.setTextColor(Color.argb(255, 255, 0, 0))
            else holder.procent_1H.setTextColor(Color.argb(255, 0, 255, 0))

            if (model.percent_24h < 0) holder.procent_24H.setTextColor(Color.argb(255, 255, 0, 0))
            else holder.procent_24H.setTextColor(Color.argb(255, 0, 255, 0))

            if (model.percent_7d < 0) holder.procent_7D.setTextColor(Color.argb(255, 255, 0, 0))
            else holder.procent_7D.setTextColor(Color.argb(255, 0, 255, 0))

        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = if (data.get(position) != null) StockRes.TYPE_TEXT else StockRes.TYPE_PROGRESS


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.item_coin_name!!
        val price = view.item_coin_price!!
        val symbol = view.item_coin_symbol!!
        val procent_1H = view.item_coin_procent_1H!!
        val procent_24H = view.item_coin_procent_24H!!
        val procent_7D = view.item_coin_procent_7D!!

    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view)


}