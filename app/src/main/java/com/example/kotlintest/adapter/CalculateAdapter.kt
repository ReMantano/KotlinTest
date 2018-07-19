package com.example.kotlintest.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.api.CoinMarketCap.Coin
import com.example.kotlintest.fragments.ListFragment
import com.example.kotlintest.until.StockRes
import kotlinx.android.synthetic.main.item_list.view.*


class CalculateAdapter : RecyclerView.Adapter<CalculateAdapter.TextViewHolder>() {

    var data: MutableList<Coin?> = ListFragment.list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculateAdapter.TextViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        viewHolder = TextViewHolder(v)

        return viewHolder
    }


    override fun onBindViewHolder(holder: CalculateAdapter.TextViewHolder, position: Int) {


        val model: Coin? = data[position]

        if (model != null) {

            val convert: Double = StockRes.EXTRA_COIN_PRICE * StockRes.convert_number / model.price

            holder.name_static.text = StockRes.EXTRA_COIN_NAME
            holder.symbol_static.text = StockRes.EXTRA_COIN_SYMBOL
            holder.price_static.text = StockRes.convert_number.toString()

            holder.name.text = model.name
            holder.symbol.text = model.symbol
            holder.price.text = convert.toString()

        }

    }

    override fun getItemCount(): Int = data.size


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name_static = view.item_coin_name!!
        val symbol_static = view.item_coin_symbol!!
        val price_static = view.item_coin_price!!
        val name = view.item_coin_procent_24H!!
        val symbol = view.item_calculate_symbol!!
        val price = view.item_coin_procent_7D!!

    }


}