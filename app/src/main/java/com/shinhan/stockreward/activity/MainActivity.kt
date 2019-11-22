package com.shinhan.stockreward.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
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
        Log.d("MainActivity", "start")
    }

    private fun callApi() {
        Log.d("MainActivity", "callApi")
        ApiManager.apiService.getBoardTalks().enqueue(object: Callback<TestObject> {
            override fun onResponse(call: Call<TestObject>, response: Response<TestObject>) {
                Log.d("succeess", "ok")
                findViewById<TextView>(R.id.text_result).apply {
                    text = response.body()?.resvSeq
                    Log.d("success", response.body()?.resvSeq)
                }
            }

            override fun onFailure(call: Call<TestObject>, t: Throwable) {
                Log.d("faile",t.message)
            }
        })
    }
}
