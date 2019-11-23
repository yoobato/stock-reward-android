package com.shinhan.stockreward.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
import com.github.mikephil.charting.model.GradientColor
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlin.math.roundToInt


class StockHistoryActivity : Activity() {
    private var stockId = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var stockList : List<Stock>
    private lateinit var chart: BarChart
    private var originValue = 0
    private var currentValue = 0
    private var currentUnit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_summary)

        stockId = intent.getIntExtra(Constants.KEY_STOCK_ID, 0)
        currentUnit = intent.getIntExtra(Constants.KEY_CURRENT_UNIT, 0)

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

        val xAxis = chart.getXAxis()
        xAxis.setPosition(XAxisPosition.BOTTOM)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.setLabelCount(7)


        val leftAxis = chart.getAxisLeft()
        leftAxis.setLabelCount(8, false)
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)

        val rightAxis = chart.getAxisRight()
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(8, false)
        rightAxis.setSpaceTop(15f)
        rightAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)
    }

    private fun setData() {
        val values = arrayListOf<BarEntry>()
        values.add(BarEntry(1f, originValue.toFloat()))
        values.add(BarEntry(2f, currentValue.toFloat()))
        val set1: BarDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values

        } else {
            set1 = BarDataSet(values, "")

            set1.setDrawIcons(false)

            val startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light)
            val startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light)
            val startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light)
            val endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
            val endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple)
            val endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark)
            val endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            val endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark)

            val gradientColors = arrayListOf<GradientColor>()
            gradientColors.add(GradientColor(startColor1, endColor1))
            gradientColors.add(GradientColor(startColor2, endColor2))
            gradientColors.add(GradientColor(startColor3, endColor3))
            gradientColors.add(GradientColor(startColor4, endColor4))
            gradientColors.add(GradientColor(startColor5, endColor5))

            set1.gradientColors = gradientColors

            val dataSets = arrayListOf<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
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
                Log.d("origin", originValue.toString())
                Log.d("current", currentValue.toString())
                setData()
            }

            override fun onFailure(call: Call<List<Stock>>, t: Throwable) {

            }
        })
    }

    private fun initAdapter() {

    }
}