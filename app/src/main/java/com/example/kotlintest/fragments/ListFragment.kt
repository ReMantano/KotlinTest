package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlintest.MainActivity
import com.example.kotlintest.R
import com.example.kotlintest.adapter.CoinAdapter
import com.example.kotlintest.api.CoinMarketCap.Coin
import com.example.kotlintest.api.CoinMarketCap.SearchCoins
import com.example.kotlintest.api.SearchCoinsProvider
import com.example.kotlintest.until.CheckInternetConection
import com.example.kotlintest.until.ShowNotConnectItems
import com.example.kotlintest.until.StockRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class ListFragment : Fragment(), View.OnClickListener {

    companion object {
        val list: MutableList<Coin?> = mutableListOf()
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var getCoins: SearchCoins
    private lateinit var adapter: CoinAdapter
    private lateinit var llm: LinearLayoutManager


    private var isLoading: Boolean = false


    override fun onClick(p0: View?) {

        StockRes.EXTRA_COIN_NAME = p0?.findViewById<TextView>(R.id.item_coin_name)?.text as String
        StockRes.EXTRA_COIN_SYMBOL = p0.findViewById<TextView>(R.id.item_coin_symbol)?.text as String
        StockRes.EXTRA_COIN_PRICE = p0.findViewById<TextView>(R.id.item_coin_price)?.text.toString().toDouble()

        ((activity) as MainActivity).navigation.selectedItemId = R.id.action_second

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listView.layoutManager = llm
        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visebleItemCount: Int = llm.childCount
                val totalItemCount: Int = recyclerView.adapter.itemCount
                val firstItem: Int = llm.findFirstVisibleItemPosition()

                if (isLoading) {
                    if (firstItem + visebleItemCount >= totalItemCount) {
                        list.removeAt(list.size - 1)
                        loadCoin(totalItemCount)


                    }
                }
            }
        })

        adapter = CoinAdapter(list, this)
        listView.adapter = adapter

        deleteAllNullInList()

        loadCoin(list.size)

        adapter.notifyDataSetChanged()

        textViewSort1H.setOnClickListener(getSortClickListener())
        textViewSort24H.setOnClickListener(getSortClickListener())
        textViewSort7D.setOnClickListener(getSortClickListener())
        textViewSortName.setOnClickListener(getSortClickListener())
        textViewSortPrice.setOnClickListener(getSortClickListener())


    }


    private fun loadCoin(last: Int) {
        if (CheckInternetConection(context)) {
            getCoins = SearchCoinsProvider.providerSearchCoins()
            isLoading = false
            compositeDisposable.add(
                    getCoins.getCoins(last)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->

                                list.addAll(result)
                                list.add(null)
                                adapter.notifyDataSetChanged()

                                ((activity) as ShowNotConnectItems).Show(true)

                                isLoading = true
                            }
                            ))
        } else ((activity) as ShowNotConnectItems).Show(false)
    }

    private fun sortList(sort: Int) {
        when (sort) {

            R.id.textViewSortPrice -> {

                Collections.sort(list, object : Comparator<Coin?> {
                    override fun compare(p0: Coin?, p1: Coin?): Int {

                        if (p0 != null && p1 != null) {
                            return -p0.price.compareTo(p1.price)
                        } else {
                            return 0
                        }
                    }
                })
                adapter.notifyDataSetChanged()
            }

            R.id.textViewSort1H -> {

                Collections.sort(list, object : Comparator<Coin?> {
                    override fun compare(p0: Coin?, p1: Coin?): Int {

                        if (p0 != null && p1 != null) {
                            return p0.price.compareTo(p1.price)
                        } else {
                            return 0
                        }
                    }
                })
                adapter.notifyDataSetChanged()

            }

            R.id.textViewSort24H -> {

                Collections.sort(list, object : Comparator<Coin?> {
                    override fun compare(p0: Coin?, p1: Coin?): Int {

                        if (p0 != null && p1 != null) {
                            return -p0.percent_24h.compareTo(p1.percent_24h)
                        } else {
                            return 0
                        }
                    }
                })
                adapter.notifyDataSetChanged()
            }

            R.id.textViewSort7D -> {

                Collections.sort(list, object : Comparator<Coin?> {
                    override fun compare(p0: Coin?, p1: Coin?): Int {

                        if (p0 != null && p1 != null) {
                            return p0.percent_24h.compareTo(p1.percent_24h)
                        } else {
                            return 0
                        }
                    }
                })
                adapter.notifyDataSetChanged()
            }

        }
    }

    private fun deleteAllNullInList() {
        for (i in 0..(list.size - 1)) {
            if (list.get(i) == null) list.removeAt(i)
        }
    }

    private fun getSortClickListener(): View.OnClickListener {
        val click = object : View.OnClickListener {
            override fun onClick(p0: View?) {

                textViewSort1H.background = null
                textViewSort24H.background = null
                textViewSort7D.background = null
                textViewSortPrice.background = null
                textViewSortName.background = null

                val item = p0!!.id
                p0.setBackgroundResource(R.drawable.ic_sort_click)

                sortList(item)
            }
        }
        return click
    }
}
