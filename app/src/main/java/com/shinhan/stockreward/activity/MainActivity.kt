package com.shinhan.stockreward.activity

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.shinhan.stockreward.R
import com.shinhan.stockreward.api.ApiManager
import com.shinhan.stockreward.api.model.TestObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callApi()
    }

    private fun callApi() {
        ApiManager.apiService.getForeignStockOrder().enqueue(object: Callback<TestObject> {
            override fun onResponse(call: Call<TestObject>, response: Response<TestObject>) {
                findViewById<TextView>(R.id.text_result).apply {
                    text = response.body()?.resvSeq
                }
            }

            override fun onFailure(call: Call<TestObject>, t: Throwable) {
            }
        })
    }
}
