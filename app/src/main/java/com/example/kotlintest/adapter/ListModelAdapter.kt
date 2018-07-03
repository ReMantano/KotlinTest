package com.example.kotlintest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.api.Coin
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*

class ListModelAdapter(val list: MutableList<Coin>, val context: Context) : RecyclerView.Adapter<ListModelAdapter.ViewHolder>() {


    var data: MutableList<Coin> = list
    //val colors: Array<Int>  = arrayOf(0xFF9C27B,0xFF99000,0xFF00990,0xFF00009)
    val rand: Random = Random()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]

        holder.nameView.text = model.name
        holder.priceView.text = model.price.toString()
        holder.procentView.text = model.percent_24h.toString()
        holder.imageView.text = model.symbol

        if (model.percent_24h < 0) holder.procentView.setTextColor(0xFFff000)
        else holder.procentView.setTextColor(0xFF00ff0)

        //var draw: Drawable = holder.imageView.background
        //var wrap:Drawable = DrawableCompat.wrap(draw)
        // DrawableCompat.setTint(wrap,colors[rand.nextInt(colors.size)])
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView = view.list_name!!
        val priceView = view.list_price!!
        val imageView = view.list_image!!
        val procentView = view.list_procent!!

    }


    fun clear() {
        data.clear()
    }

}