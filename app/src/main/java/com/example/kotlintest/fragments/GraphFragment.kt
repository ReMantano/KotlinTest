package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.api.CryptoCompare.HistoryData
import com.example.kotlintest.api.CryptoCompare.SearchHistoryData
import com.example.kotlintest.api.SearchCoinsProvider
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_graph.*

class GraphFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val getHistory: SearchHistoryData = SearchCoinsProvider.provideSearchHistoryData()
    private lateinit var historyData: HistoryData
    private lateinit var linePoint: LineGraphSeries<DataPoint>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_graph, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable.add(
                getHistory.getHistoryData(10)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->

                            historyData = result
                            for (a in 1..10) {
                                Log.d("BOMMMMMMMMMM", "Time: " + historyData.data[a].time.toString()
                                        + "\nClose:" + historyData.data[a].close.toString())
                            }

                            linePoint = LineGraphSeries(historyData.data)
                            fragmentGraph.addSeries(linePoint)

                            // fragmentGraph.viewport.isXAxisBoundsManual = true
                            fragmentGraph.viewport.isYAxisBoundsManual = true

                            //fragmentGraph.viewport.setMaxY(7000.0)
                            fragmentGraph.viewport.setMaxX(historyData.data[historyData.data.size - 1].time)

                            //fragmentGraph.viewport.setMinY(5871.28)
                            fragmentGraph.viewport.setMinX(historyData.data[0].time)

                            fragmentGraph.gridLabelRenderer.numHorizontalLabels = historyData.data.size
                            fragmentGraph.gridLabelRenderer.isHumanRounding = false

                        })
        )

        // val point: Array<DataPoint> = arrayOf(DataPoint(historyData.data,0.0),DataPoint(1.0,1.0),DataPoint(2.0,1.0),
        //       DataPoint(3.0,2.0),DataPoint(3.0,3.0))


    }
}
