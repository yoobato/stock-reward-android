package com.shinhan.stockreward.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shinhan.stockreward.R
import com.shinhan.stockreward.api.model.Stock
import java.text.DecimalFormat
import kotlin.math.roundToInt

class StockAdapter constructor(private val list: List<Stock>) : RecyclerView.Adapter<StockAdapter.ViewHolder>() {
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var stock = itemView.findViewById<TextView>(R.id.text_stock)
        internal var money = itemView.findViewById<TextView>(R.id.text_money)
        internal var store = itemView.findViewById<TextView>(R.id.text_store)
        internal var date = itemView.findViewById<TextView>(R.id.date_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.item_stock, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = list[position]
        holder.stock.text = stock.amount.toString() + "주"
        holder.money.text = "${DecimalFormat("#,###").format((stock.amount * stock.baseUnitPrice).roundToInt())} 원"
        holder.store.text = stock.storeName
        holder.date.text = stock.createdAt.split("T")[0]

    }

    override fun getItemCount() = list.count()
}