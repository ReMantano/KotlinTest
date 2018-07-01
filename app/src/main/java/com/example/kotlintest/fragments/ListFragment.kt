package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.until.ListModel
import com.example.kotlintest.R
import com.example.kotlintest.adapter.ListModelAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val adapter = ListModelAdapter(context)
        listView.adapter = adapter
        adapter.setItems(getData())
    }

    private fun getData(): ArrayList<ListModel>{

        val data = ArrayList<ListModel>()

        for (i in 0..10){
            data.add(ListModel("name", i))
        }
        return data
    }
}