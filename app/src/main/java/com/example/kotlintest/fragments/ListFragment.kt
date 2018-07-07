package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.adapter.CoinAdapter
import com.example.kotlintest.api.Coin
import com.example.kotlintest.api.SearchCoins
import com.example.kotlintest.api.SearchCoinsProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*


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


                            isLoading = true
                        }
                        ))

    }

}