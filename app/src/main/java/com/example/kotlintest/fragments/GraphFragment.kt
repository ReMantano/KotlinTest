package com.example.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.kotlintest.R
import com.example.kotlintest.api.CryptoCompare.DataC
import com.example.kotlintest.api.CryptoCompare.HistoryData
import com.example.kotlintest.api.CryptoCompare.SearchHistoryData
import com.example.kotlintest.api.SearchCoinsProvider
import com.example.kotlintest.until.CheckInternetConection
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

        spinnerData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val select = p0?.getItemAtPosition(p2).toString()
                if (CheckInternetConection(context)) selectData(select)
            }


        }
        loadDataDay()

    }


    private fun loadDataDay() {
        compositeDisposable.add(
                getHistory.getHistoryDataDay(10)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result -> fragmentSetting(result) })
        )
    }

    private fun loadDataHour() {
        compositeDisposable.add(
                getHistory.getHistoryDataHour(10)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result -> fragmentSetting(result) })
        )
    }

    private fun loadDataMinute() {
        compositeDisposable.add(
                getHistory.getHistoryDataMinute(10)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result -> fragmentSetting(result) })
        )
    }


    private fun selectData(select: String) {
        when (select) {

            "Дневной график" -> {

                loadDataDay()

            }

            "Часовой график" -> {

                loadDataHour()

            }

            "Минутный график" -> {

                loadDataMinute()

            }


        }
    }

    private fun putXAxis(array: Array<DataC>): Array<DataPoint> {
        var point: ArrayList<DataPoint> = ArrayList<DataPoint>()
        for (i in 0..array.size - 1) {
            point.add(DataPoint(i * 1.0, array.get(i).close))

        }

        return point.toArray(arrayOfNulls<DataPoint>(point.size))
    }

    private fun fragmentSetting(result: HistoryData) {

        fragmentGraph.removeAllSeries()

        historyData = result

        var data: Array<DataPoint> = putXAxis(historyData.data)

        var linePoint: LineGraphSeries<DataPoint> = LineGraphSeries(data)

        when (spinnerData.selectedItem.toString()) {
            "Дневной график" -> fragmentGraph.gridLabelRenderer.labelFormatter = FormatDay
            "Часовой график" -> fragmentGraph.gridLabelRenderer.labelFormatter = FormatHour
            "Минутный график" -> fragmentGraph.gridLabelRenderer.labelFormatter = FormatMinute
        }

        fragmentGraph.gridLabelRenderer.textSize = 16f
        fragmentGraph.gridLabelRenderer.verticalLabelsVAlign = GridLabelRenderer.VerticalLabelsVAlign.BELOW
        fragmentGraph.addSeries(linePoint)


        fragmentGraph.gridLabelRenderer.numHorizontalLabels = historyData.data.size
        fragmentGraph.gridLabelRenderer.numVerticalLabels = historyData.data.size
    }

    private object FormatDay : DefaultLabelFormatter() {


        var date: Calendar = Calendar.getInstance()

        override fun formatLabel(value: Double, isValueX: Boolean): String {

            if (isValueX) {

                if (date.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) date.add(Calendar.DAY_OF_MONTH, -11)
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
}
