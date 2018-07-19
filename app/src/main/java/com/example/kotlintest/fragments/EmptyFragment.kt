package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.adapter.CalculateAdapter
import com.example.kotlintest.until.StockRes
import kotlinx.android.synthetic.main.fragment_calculate.*

class EmptyFragment : Fragment(), View.OnClickListener {

    override fun onClick(p0: View?) {
        StockRes.convert_number = editText.text.toString().toDouble()
        adapter.notifyDataSetChanged()
    }


    private lateinit var adapter: CalculateAdapter
    private lateinit var llm: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_calculate, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = llm

        imageView.text = StockRes.EXTRA_COIN_SYMBOL
        textView.text = StockRes.EXTRA_COIN_NAME


        button.setOnClickListener(this)

        adapter = CalculateAdapter()
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }




}
