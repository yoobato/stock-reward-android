package com.shinhan.stockreward.api.model

data class Stock (
    val stockId: Int,
    val stockCode: String,
    val stockName: String,
    val storeName: String,
    val amount: Float,
    val baseUnitPrice: Int,
    val createdAt: String
)

//"stock_id":5,
//"stock_code":"USAHLT",
//"stock_name":"힐튼",
//"store_name":"삼성 디지털프라자",
//"amount":0.17,
//"base_unit_price":230134,
//"created_at":"2019-11-22T21:26:03.000Z"