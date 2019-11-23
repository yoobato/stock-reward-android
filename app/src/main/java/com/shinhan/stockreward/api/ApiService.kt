package com.shinhan.stockreward.api

import com.shinhan.stockreward.api.model.Summary
import com.shinhan.stockreward.api.model.TestObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/fstock/order")
    fun getForeignStockOrder(): Call<TestObject>

    @GET("/api/user/{id}/stock-reward/summary")
    fun getSummary(@Path("id")id: Int): Call<List<Summary>>


}
