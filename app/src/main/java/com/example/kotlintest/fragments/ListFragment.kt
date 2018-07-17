package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import com.example.kotlintest.R
import com.example.kotlintest.adapter.CoinAdapter
import com.example.kotlintest.api.CoinMarketCap.Coin
import com.example.kotlintest.api.CoinMarketCap.SearchCoins
import com.example.kotlintest.api.SearchCoinsProvider
import com.example.kotlintest.until.CheckInternetConection
import com.example.kotlintest.until.ShowNotConnectItems
import com.example.kotlintest.until.StockRes
import com.example.kotlintest.until.fragmentTag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class ListFragment : Fragment(), View.OnClickListener, ShowNotConnectItems {

    companion object {
        val list: MutableList<Coin?> = mutableListOf()
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var getCoins: SearchCoins
    private lateinit var adapter: CoinAdapter
    private lateinit var llm: LinearLayoutManager


    private var isLoading: Boolean = false


    override fun Show(load: Boolean) {}

    override fun restart() {
        loadCoin(0)
    }


    override fun onClick(p0: View?) {

        StockRes.EXTRA_COIN_NAME = p0?.findViewById<TextView>(R.id.list_name)?.text as String
        StockRes.EXTRA_COIN_SYMBOL = p0.findViewById<TextView>(R.id.list_image)?.text as String
        StockRes.EXTRA_COIN_PRICE = p0.findViewById<TextView>(R.id.list_price)?.text.toString().toDouble()


        val transaction = activity.supportFragmentManager.beginTransaction()

        val fragment = Fragment.instantiate(context, fragmentTag<GraphFragment>(), null)

        transaction.setCustomAnimations(
                android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_out, android.R.anim.fade_in)
        transaction.replace(R.id.conteiner_fragment, fragment, fragmentTag<GraphFragment>())
        transaction.commit()
    }

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

        adapter = CoinAdapter(list, this)
        listView.adapter = adapter

        if (CheckInternetConection(context)) loadCoin(0)
        else ((activity) as ShowNotConnectItems).Show(false)

        adapter.notifyDataSetChanged()


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = p0?.getItemAtPosition(p2).toString()
                sortList(item)

            }
        }


    }


    private fun loadCoin(last: Int) {

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
                            // adapter.addData(list as ArrayList<Coin>)
                            ((activity) as ShowNotConnectItems).Show(true)

                            isLoading = true
                        }
                        ))


    }

    private fun sortList(sort: String) {
        when (sort) {

            "По убыванию цены" -> {

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