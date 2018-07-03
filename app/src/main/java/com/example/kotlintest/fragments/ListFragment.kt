package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.adapter.ListModelAdapter
import com.example.kotlintest.api.Coin
import com.example.kotlintest.api.SearchCoins
import com.example.kotlintest.api.SearchCoinsProvider
import com.example.kotlintest.until.ListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    //@BindView(R.id.listView)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val getCoins: SearchCoins = SearchCoinsProvider.providerSearchCoins()
    private lateinit var adapter: ListModelAdapter

    private val list: MutableList<Coin> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        compositeDisposable.add(
                getCoins.GetCoins(0)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->

                            list.addAll(result)

                            adapter = ListModelAdapter(list, context)
                            listView.adapter = adapter


                        }
                        ))
    }

    private fun getData(): ArrayList<ListModel>{

        val data = ArrayList<ListModel>()

        for (i in 0..10){
            data.add(ListModel("name", i))
        }
        return data
    }
}