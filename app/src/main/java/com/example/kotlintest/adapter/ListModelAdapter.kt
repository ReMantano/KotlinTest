package com.example.kotlintest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlintest.until.ListModel

class ListModelAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var data: ArrayList<ListModel> = ArrayList()

    fun setItems(items : ArrayList<ListModel>){
        data = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ListModelViewHolder.create(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater, parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val model = data[position]
        (holder as ListModelViewHolder).bint(model)
    }

    override fun getItemCount(): Int = data.size




}