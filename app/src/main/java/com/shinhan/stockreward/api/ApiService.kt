package com.shinhan.stockreward.api

import com.shinhan.stockreward.api.model.TestObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/fstock/order")
    fun getForeignStockOrder(): Call<TestObject>
}
