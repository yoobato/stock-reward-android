package com.shinhan.stockreward.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shinhan.stockreward.R
import com.shinhan.stockreward.api.ApiManager
import com.shinhan.stockreward.api.model.Stock
import com.shinhan.stockreward.util.Constants
import com.shinhan.stockreward.view.StockAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockHistoryActivity : Activity() {
    private var stockId = 0
    private lateinit var recyclerView: RecyclerView
    private val stockList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_summary)

        stockId = intent.getIntExtra(Constants.KEY_STOCK_ID, 0)

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
            }

            override fun onFailure(call: Call<List<Stock>>, t: Throwable) {

            }
        })
    }

    private fun initAdapter() {

    }
}