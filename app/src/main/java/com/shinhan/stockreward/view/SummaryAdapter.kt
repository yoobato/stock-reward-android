package com.shinhan.stockreward.view

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.shinhan.stockreward.R
import com.shinhan.stockreward.api.model.Summary
import java.text.DecimalFormat


class SummaryAdapter constructor(private val list: List<Summary>) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {
    val images = arrayOf(
        R.drawable.ic_samsung,
        R.drawable.ic_lf,
        R.drawable.ic_hanssem,
        R.drawable.ic_apple,
        R.drawable.ic_hilton
    )
    inner class ViewHolder internal constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        internal var icon = itemView.findViewById<ImageView>(R.id.image_icon)
        internal var stockName = itemView.findViewById<TextView>(R.id.text_stock_name)
        internal var currentPrice = itemView.findViewById<TextView>(R.id.text_current_price)
        internal var percent = itemView.findViewById<TextView>(R.id.text_percent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.item_summary, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val summary = list[position]
        val percent = ((summary.currentUnitPrice - summary.baseUnitPrice).toFloat() /
                summary.baseUnitPrice.toFloat() * 100f)
        holder.icon.setImageResource(images[summary.stockId - 1])
        holder.stockName.text = summary.stockName
        holder.currentPrice.text = "현재가 ${DecimalFormat("#,###").format(summary.currentUnitPrice)}"
        holder.percent.text = "${if (percent > 0 ) "+" else ""}${"%.2f".format(percent).toDouble()} %"
        holder.percent.setTextColor(if (percent >= 0) Color.RED else Color.GREEN)

    }

    override fun getItemCount() = list.count()
}