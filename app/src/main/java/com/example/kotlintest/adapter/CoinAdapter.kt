package com.example.kotlintest.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.api.CoinMarketCap.Coin
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*

class CoinAdapter(val list: MutableList<Coin?>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_TEXT = 1
    private val TYPE_PROGRESS = 0

    var data: MutableList<Coin?> = list
    val colors: Array<Int> = arrayOf(Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0),
            Color.argb(255, 0, 255, 255), Color.argb(255, 100, 100, 0),
            Color.argb(255, 0, 255, 100), Color.argb(255, 10, 255, 100))
    val rand: Random = Random()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder

        if (viewType == TYPE_TEXT) {
            val v: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list, parent, false)
            viewHolder = TextViewHolder(v)
        } else {
            val v: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_bar, parent, false)
            viewHolder = ProgressViewHolder(v)
        }
        //val layoutInflater = LayoutInflater.from(parent.context)
        ///val view = layoutInflater.inflate(R.layout.item_list, parent, false)
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
            DrawableCompat.setTint(wrap, colors[symbol % colors.size])
        } else {
            //(holder as ProgressViewHolder).progreccBar.visibility = ProgressBar.VISIBLE
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = if (data.get(position) != null) TYPE_TEXT else TYPE_PROGRESS


    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView = view.list_name!!
        val priceView = view.list_price!!
        val imageView = view.list_image!!
        val procentView = view.list_procent!!

    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val progreccBar: ProgressBar = (view as ProgressBar).findViewById(R.id.progressBar)
    }


}