package com.shinhan.stockreward.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.shinhan.stockreward.R
import com.shinhan.stockreward.api.ApiManager
import com.shinhan.stockreward.api.model.Stock
import com.shinhan.stockreward.util.Constants
import com.shinhan.stockreward.view.StockAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlin.math.roundToInt
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DecimalFormat


class StockHistoryActivity : Activity() {
    private var stockId = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var stockList : List<Stock>
    private lateinit var chart: BarChart
    private var originValue = 0
    private var currentValue = 0
    private var currentUnit = 0
    private val labels = arrayListOf("Point", "Stock", "no")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_summary)

        stockId = intent.getIntExtra(Constants.KEY_STOCK_ID, 0)
        currentUnit = intent.getIntExtra(Constants.KEY_CURRENT_UNIT, 0)

        findViewById<TextView>(R.id.text_current_price).text = "현재가 : ${DecimalFormat("#,###").format(currentUnit)}원"

        initRecyclerView()
        initChart()
    }

    private fun initChart() {
        chart = findViewById(R.id.bar_chart)

        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(false)
        chart.legend.isEnabled  = false
        chart.description.isEnabled = false
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)
        // chart.setDrawYLabels(false);

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f)
        xAxis.setCenterAxisLabels(true)
        xAxis.setValueFormatter(IndexAxisValueFormatter(labels))


        val leftAxis = chart.getAxisLeft()
        leftAxis.setLabelCount(8, false)
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.textColor = Color.WHITE
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)
        chart.setNoDataText("데이터를 불러오는 중입니다..")
        chart.axisRight.isEnabled = false
    }

    private fun setData() {
        val values = arrayListOf<BarEntry>()
        val values2 = arrayListOf<BarEntry>()
        values.add(BarEntry(1f, originValue.toFloat(), "Point"))
        values2.add(BarEntry(2f, currentValue.toFloat(), "Stock"))
        val set1: BarDataSet
        val set2: BarDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values

        } else {
            set1 = BarDataSet(values, "first")
            set2 = BarDataSet(values2, "second")

            set1.setDrawIcons(false)
            set2.setDrawIcons(false)

            set1.color = Color.parseColor("#0B77E3")
            set2.color = Color.parseColor("#6D7278")

            val dataSets = arrayListOf<IBarDataSet>()
            dataSets.add(set1)
            dataSets.add(set2)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTextColor(Color.WHITE)
            data.setBarWidth(0.6f)

            chart.data = data
        }
        chart.data.notifyDataChanged()
        chart.notifyDataSetChanged()
    }


    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)

        val orientation = LinearLayoutManager(this).orientation
        val dividerItemDecoration = DividerItemDecoration(this, orientation).apply {
            setDrawable(resources.getDrawable(R.drawable.divider_cccccc))
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        getData(stockId)
        initAdapter()
    }

    private fun getData(stockId: Int) {
        ApiManager.apiService.getStockDetail(stockId).enqueue(object: Callback<List<Stock>> {
            override fun onResponse(call: Call<List<Stock>>, response: Response<List<Stock>>) {
                recyclerView.adapter = StockAdapter(response.body()!!)
                stockList = response.body()!!
                for (stock : Stock in stockList) {
                    originValue += (stock.baseUnitPrice * stock.amount).roundToInt()
                    currentValue += (currentUnit * stock.amount).roundToInt()
                }
                findViewById<TextView>(R.id.text_title).text = stockList[0].stockName
                setData()
            }

            override fun onFailure(call: Call<List<Stock>>, t: Throwable) {

            }
        })
    }

    private fun initAdapter() {

    }
}