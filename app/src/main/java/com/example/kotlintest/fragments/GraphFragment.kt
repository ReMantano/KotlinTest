package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlintest.R
import com.example.kotlintest.api.CryptoCompare.DataC
import com.example.kotlintest.api.CryptoCompare.HistoryData
import com.example.kotlintest.api.CryptoCompare.SearchHistoryData
import com.example.kotlintest.api.SearchCoinsProvider
import com.example.kotlintest.until.CheckInternetConection
import com.example.kotlintest.until.ShowNotConnectItems
import com.example.kotlintest.until.StockRes
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_graph.*
import java.util.*
import kotlin.collections.ArrayList

class GraphFragment : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val getHistory: SearchHistoryData = SearchCoinsProvider.provideSearchHistoryData()
    private lateinit var historyData: HistoryData
    private lateinit var linePoint: LineGraphSeries<DataPoint>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_graph, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePeriod24H.setOnClickListener(getTimeClickListener())
        timePeriod1H.setOnClickListener(getTimeClickListener())
        timePeriod1M.setOnClickListener(getTimeClickListener())

        loadDataDay()

    }


    private fun loadDataDay() {
        if (CheckInternetConection(context)) {
            compositeDisposable.add(
                    getHistory.getHistoryDataDay(10)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                ((activity) as ShowNotConnectItems).Show(true)
                                fragmentSetting(result)
                            })
            )
        } else ((activity) as ShowNotConnectItems).Show(false)
    }

    private fun loadDataHour() {
        if (CheckInternetConection(context)) {
            compositeDisposable.add(
                    getHistory.getHistoryDataHour(10)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                ((activity) as ShowNotConnectItems).Show(true)
                                fragmentSetting(result)
                            })
            )
        } else ((activity) as ShowNotConnectItems).Show(false)
    }

    private fun loadDataMinute() {
        if (CheckInternetConection(context)) {
            compositeDisposable.add(
                    getHistory.getHistoryDataMinute(10)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({ result ->
                                ((activity) as ShowNotConnectItems).Show(true)
                                fragmentSetting(result)
                            })
            )
        } else ((activity) as ShowNotConnectItems).Show(false)
    }


    private fun selectData(select: Int) {
        when (select) {

            R.id.timePeriod24H -> {

                loadDataDay()

            }

            R.id.timePeriod1H -> {

                loadDataHour()

            }

            R.id.timePeriod1M -> {

                loadDataMinute()

            }


        }
    }

    private fun putXAxis(array: Array<DataC>): Array<DataPoint> {
        val point: ArrayList<DataPoint> = ArrayList<DataPoint>()
        for (i in 0..array.size - 1) {
            point.add(DataPoint(i * 1.0, array.get(i).close))

        }

        return point.toArray(arrayOfNulls<DataPoint>(point.size))
    }

    private fun fragmentSetting(result: HistoryData) {

        historyData = result

        val data: Array<DataPoint> = putXAxis(historyData.data)

        linePoint = LineGraphSeries(data)

        fragmentGraph.removeAllSeries()
        fragmentGraph.addSeries(linePoint)

        when (StockRes.EXTRA_TIME_PERIOD_SELECT) {
            "24H" -> fragmentGraph.gridLabelRenderer.labelFormatter = FormatDay
            "1H" -> fragmentGraph.gridLabelRenderer.labelFormatter = FormatHour
            "1M" -> fragmentGraph.gridLabelRenderer.labelFormatter = FormatMinute
        }

        fragmentGraph.gridLabelRenderer.textSize = 26f
        fragmentGraph.gridLabelRenderer.verticalLabelsVAlign = GridLabelRenderer.VerticalLabelsVAlign.BELOW

        fragmentGraph.gridLabelRenderer.numHorizontalLabels = historyData.data.size / 2
        fragmentGraph.gridLabelRenderer.numVerticalLabels = historyData.data.size
    }

    private object FormatDay : DefaultLabelFormatter() {


        var date: Calendar = Calendar.getInstance()

        override fun formatLabel(value: Double, isValueX: Boolean): String {

            if (isValueX) {

                if (date.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) date.add(Calendar.DAY_OF_MONTH, -11)
                date.add(Calendar.DAY_OF_MONTH, 1)

                var text: String

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

    private object FormatHour : DefaultLabelFormatter() {


        var date: Calendar = Calendar.getInstance()

        override fun formatLabel(value: Double, isValueX: Boolean): String {

            if (isValueX) {

                if (date.get(Calendar.HOUR_OF_DAY) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) date.add(Calendar.HOUR_OF_DAY, -11)
                date.add(Calendar.HOUR_OF_DAY, 1)




                return super.formatLabel(date.get(Calendar.HOUR_OF_DAY).toDouble(), isValueX) + ":00"

            } else {


                return super.formatLabel(value, isValueX)
            }
        }
    }

    private object FormatMinute : DefaultLabelFormatter() {


        var date: Calendar = Calendar.getInstance()

        override fun formatLabel(value: Double, isValueX: Boolean): String {

            if (isValueX) {

                if (date.get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)) date.add(Calendar.MINUTE, -11)
                date.add(Calendar.MINUTE, 1)


                return date.get(Calendar.HOUR_OF_DAY).toString() + ":" + super.formatLabel(date.get(Calendar.MINUTE).toDouble(), isValueX)

            } else {


                return super.formatLabel(value, isValueX)
            }
        }
    }

    private fun getTimeClickListener(): View.OnClickListener {
        val listen = object : View.OnClickListener {
            override fun onClick(p0: View?) {
                timePeriod1H.background = null
                timePeriod24H.background = null
                timePeriod1M.background = null

                p0!!.setBackgroundResource(R.drawable.ic_sort_click)
                StockRes.EXTRA_TIME_PERIOD_SELECT = ((p0) as TextView).text.toString()


                if (CheckInternetConection(context)) selectData(p0.id)
            }
        }
        return listen
    }
}
