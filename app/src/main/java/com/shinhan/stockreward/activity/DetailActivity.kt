package com.shinhan.stockreward.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shinhan.stockreward.R
import com.shinhan.stockreward.view.StockAdapter

class DetailActivity : Activity() {

    private lateinit var recyclerView: RecyclerView
    private val stockList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_summary)

        recyclerView = findViewById(R.id.recycler_view)

        val orientation = LinearLayoutManager(this).orientation
        val dividerItemDecoration = DividerItemDecoration(this, orientation).apply {
            setDrawable(resources.getDrawable(R.drawable.divider_cccccc))
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        getData()
        initAdapter()

        //ItemClickSupport.addTo(recyclerView).setOnItemClickListener
    }

    private fun getData() {
        stockList.add("string 1")
        stockList.add("string 2")
        stockList.add("string 3")
        stockList.add("string 4")
        stockList.add("string 5")

    }

    private fun initAdapter() {
        recyclerView.adapter = StockAdapter(stockList)
    }
}