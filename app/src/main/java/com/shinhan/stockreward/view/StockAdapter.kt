package com.shinhan.stockreward.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shinhan.stockreward.R

class StockAdapter constructor(val list: ArrayList<String>) : RecyclerView.Adapter<StockAdapter.ViewHolder>() {
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var textView1: TextView = itemView.findViewById(R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.item_stock, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = list.get(position)
        holder.textView1.text = text
    }

    override fun getItemCount() = list.count()
}