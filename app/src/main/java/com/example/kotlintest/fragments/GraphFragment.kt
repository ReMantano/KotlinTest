package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlintest.R
import com.example.kotlintest.api.CryptoCompare.DataC
import com.example.kotlintest.api.CryptoCompare.HistoryData
import com.example.kotlintest.api.CryptoCompare.SearchHistoryData
import com.example.kotlintest.api.SearchCoinsProvider
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_graph.*
import java.util.*

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


                            val d: Array<DataC> = historyData.data

                            val point: Array<DataPoint> = arrayOf(DataPoint(0.0, d[0].close), DataPoint(1.0, d[1].close), DataPoint(2.0, d[2].close),
                                    DataPoint(3.0, d[3].close), DataPoint(4.0, d[4].close), DataPoint(5.0, d[5].close), DataPoint(6.0, d[6].close),
                                    DataPoint(7.0, d[7].close), DataPoint(8.0, d[8].close), DataPoint(9.0, d[9].close), DataPoint(10.0, d[10].close))

                            linePoint = LineGraphSeries(point)
                            fragmentGraph.gridLabelRenderer.labelFormatter = Format
                            fragmentGraph.gridLabelRenderer.textSize = 15f
                            fragmentGraph.gridLabelRenderer.verticalLabelsVAlign = GridLabelRenderer.VerticalLabelsVAlign.BELOW
                            fragmentGraph.addSeries(linePoint)


                            fragmentGraph.gridLabelRenderer.numHorizontalLabels = historyData.data.size
                            fragmentGraph.gridLabelRenderer.numVerticalLabels = historyData.data.size


                        })
        )


    }

    private object Format : DefaultLabelFormatter() {


        var date: Calendar = Calendar.getInstance()

        override fun formatLabel(value: Double, isValueX: Boolean): String {

            if (isValueX) {

                if (date.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) date.add(Calendar.DAY_OF_MONTH, -10)
                date.add(Calendar.DAY_OF_MONTH, 1)

                var text = ""

                if (date.get(Calendar.DAY_OF_MONTH) < 10) text = "0" + date.get(Calendar.DAY_OF_MONTH).toString() + "."
                else text = date.get(Calendar.DAY_OF_MONTH).toString() + "."

                if (date.get(Calendar.MONTH) < 10) text += "0" + date.get(Calendar.MONTH).toString()
                else text += date.get(Calendar.MONTH).toString()


                return text + "." + super.formatLabel((date.get(Calendar.YEAR) - 2000).toDouble(), isValueX)

            } else {


                return super.formatLabel(value, isValueX)
            }
        }
    }
}
