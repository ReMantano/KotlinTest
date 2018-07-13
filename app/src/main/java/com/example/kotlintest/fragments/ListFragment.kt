package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.kotlintest.R
import com.example.kotlintest.adapter.CoinAdapter
import com.example.kotlintest.api.CoinMarketCap.Coin
import com.example.kotlintest.api.CoinMarketCap.SearchCoins
import com.example.kotlintest.api.SearchCoinsProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*
import kotlin.collections.ArrayList


class ListFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val getCoins: SearchCoins = SearchCoinsProvider.providerSearchCoins()
    private lateinit var adapter: CoinAdapter
    private lateinit var llm: LinearLayoutManager

    private val list: MutableList<Coin?> = mutableListOf()
    private var isLoading: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list,container,false)

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

        adapter = CoinAdapter(list, context)
        listView.adapter = adapter
        loadCoin(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = p0?.getItemAtPosition(p2).toString()
                Log.d("BOOBOBOBOBOBOB", item)
                when (item) {

                    "По убыванию цены" -> {
                        Log.d("BOOBOBOBOBOBOB", item)
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

                    "По возрастанию цены" -> {
                        Log.d("BOOBOBOBOBOBOB", item)
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

                    "По ежедневному росту" -> {
                        Log.d("BOOBOBOBOBOBOB", item)
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

                    "По ежедневному падению" -> {

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
        }



        adapter.notifyDataSetChanged()
    }




    private fun loadCoin(last: Int) {
        isLoading = false
        compositeDisposable.add(
                getCoins.getCoins(last)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->

                            list.addAll(result)
                            list.add(null)
                            adapter.notifyDataSetChanged()
                            // adapter.addData(list as ArrayList<Coin>)


                            isLoading = true
                        }
                        ))

    }

    private fun sortList(size: Int, listArray: ArrayList<Coin>): ArrayList<Coin> {

        if (size <= 1) return listArray
        else {

            val midsize: Int = size / 2

            var lArray: ArrayList<Coin> = ArrayList<Coin>()
            var rArray: ArrayList<Coin> = ArrayList<Coin>()

            if (midsize != 1) {
                lArray.addAll(listArray.subList(0, midsize - 1))
                if (midsize != 2) rArray.addAll(listArray.subList(midsize, size - 1))
                else rArray.add(listArray.get(2))
            } else {
                lArray.add(listArray.get(0))
                rArray.add(listArray.get(1))
            }

            var lsize = lArray.size
            var rsize = rArray.size

            lArray = sortList(lsize, lArray)
            rArray = sortList(rsize, rArray)

            var l = 0
            var r = 0

            var array: ArrayList<Coin> = ArrayList<Coin>()


            while (l < lsize && r < rsize) {

                if (lArray.get(l).price < rArray.get(r).price) {

                    array.add(lArray.get(l))
                    l++

                } else {
                    array.add(rArray.get(r))
                    r++
                }
            }
            if (l == lsize) {

                if (r != rsize) array.addAll(((rArray.subList(r, rsize - 1))))
                else array.add(rArray.get(r))

            } else {

                if (l != lsize) array.addAll(((lArray.subList(l, lsize - 1))))
                else array.add(lArray.get(l))

            }




            return array
        }
    }



}