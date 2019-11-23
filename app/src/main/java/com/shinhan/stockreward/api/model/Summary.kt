package com.shinhan.stockreward.api.model

data class Summary (
    val stockId: Int,
    val stockCode: String,
    val stockName: String,
    val balance: Float,
    val baseUnitPrice: Int,
    val currentUnitPrice: Int
)