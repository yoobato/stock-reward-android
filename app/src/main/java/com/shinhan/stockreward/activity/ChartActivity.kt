package com.shinhan.stockreward.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.shinhan.stockreward.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class ChartActivity : Activity(), OnChartValueSelectedListener {
    private lateinit var chart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        chart = findViewById(R.id.chart1)
        chart.apply{
            setUsePercentValues(true)
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            setExtraOffsets(5f, 10f, 5f, 5f)
            setDrawCenterText(true)
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            animateY(1400, Easing.EaseInOutQuad)

            description.isEnabled = true
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            holeRadius = 58f
            transparentCircleRadius = 61f
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            legend.isEnabled = false
        }
        chart.setOnChartValueSelectedListener(this)

        setData(10, 1.2f)
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    i.toString(),
                    resources.getDrawable(R.drawable.common_full_open_on_phone)
                )
            )
        }

        val dataSet = PieDataSet(entries, "Election Results").apply {
            setDrawIcons(false)

            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
        }


        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        chart.data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(chart))
            setValueTextSize(11f)
            setValueTextColor(Color.WHITE)
        }

        chart.highlightValues(null)
        chart.invalidate()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    override fun onNothingSelected() {

    }
}