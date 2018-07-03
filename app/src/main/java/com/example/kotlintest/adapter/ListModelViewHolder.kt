package com.example.kotlintest.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlintest.R
import com.example.kotlintest.until.ListModel

class ListModelViewHolder private constructor(root : View) : RecyclerView.ViewHolder(root){

    companion object {

        fun create(inflater: LayoutInflater, parent: ViewGroup) =
                ListModelViewHolder(inflater.inflate(R.layout.item_list, parent, false))


    }

    private var nameView: TextView? = null
    private var ageView: TextView? = null

    init{
        nameView = root.findViewById(R.id.list_name)
        ageView = root.findViewById(R.id.list_price)
    }

    fun bint(model: ListModel){

        nameView?.text = model.name
        ageView?.text = model.age.toString()

    }

}