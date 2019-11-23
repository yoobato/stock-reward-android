package com.shinhan.stockreward.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.shinhan.stockreward.R
import com.shinhan.stockreward.api.ApiManager
import com.shinhan.stockreward.api.model.Summary
import com.shinhan.stockreward.util.Constants
import com.shinhan.stockreward.util.ItemClickSupport
import com.shinhan.stockreward.view.SummaryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import kotlin.math.roundToInt

class MainActivity : Activity() {
    private lateinit var chart: PieChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var balance: TextView
    private lateinit var data: List<Summary>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balance = findViewById(R.id.text_balance)
        chart = findViewById(R.id.chart1)
        chart.apply{
            setUsePercentValues(true)
            setHoleColor(Color.parseColor("#333333"))
            setTransparentCircleColor(Color.parseColor("#333333"))
            setTransparentCircleAlpha(110)
            setExtraOffsets(5f, 10f, 5f, 5f)
            setDrawCenterText(true)
            setEntryLabelColor(Color.parseColor("#eeeeee"))
            setEntryLabelTextSize(12f)
            animateY(1400, Easing.EaseInOutQuad)

            description.isEnabled = false
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            holeRadius = 48f
            transparentCircleRadius = 49f
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            legend.isEnabled = false
        }
        //chart.setOnChartValueSelectedListener(this)

        recyclerView = findViewById(R.id.recycler_view)

        val orientation = LinearLayoutManager(this).orientation
        val dividerItemDecoration = DividerItemDecoration(this, orientation).apply {
            setDrawable(resources.getDrawable(R.drawable.divider_cccccc))
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(object: ItemClickSupport.OnItemClickListener{
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                val intent = Intent(this@MainActivity, StockHistoryActivity::class.java)
                intent.putExtra(Constants.KEY_STOCK_ID, data[position].stockId)
                startActivity(intent)
            }
        })


        getSummary(1)
    }


    private fun setData(data: List<Summary>) {
        var summaryBlance = 0
        val entries = ArrayList<PieEntry>()
        for (s: Summary in data) {
            summaryBlance += (s.currentUnitPrice * s.balance).roundToInt()
            entries.add(
                PieEntry(
                    s.balance * s.baseUnitPrice,
                    s.stockName,
                    resources.getDrawable(R.drawable.common_full_open_on_phone)
                )
            )
        }
        balance.text = DecimalFormat("#,###").format(summaryBlance)

        val dataSet = PieDataSet(entries, "Election Results").apply {
            setDrawIcons(false)

            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
        }


        // add a lot of colors

        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#00074a"))
        colors.add(Color.parseColor("#000f99"))
        colors.add(Color.parseColor("#0011b6"))
        colors.add(Color.parseColor("#0018fb"))
        colors.add(Color.parseColor("#344555"))

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        chart.data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(chart))
            setValueTextSize(11f)
            setValueTextColor(Color.parseColor("#eeeeee"))
        }

        chart.highlightValues(null)
        chart.invalidate()
    }


    private fun getSummary(userId: Int) {
        ApiManager.apiService.getSummary(userId).enqueue(object: Callback<List<Summary>>{
            override fun onResponse(call: Call<List<Summary>>, response: Response<List<Summary>>) {
                setData(response.body()!!)
                data = response.body()!!
                recyclerView.adapter = SummaryAdapter(response.body()!!)
            }

            override fun onFailure(call: Call<List<Summary>>, t: Throwable) {
                Log.e("error", t.message)
            }
        })
    }
}
