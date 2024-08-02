package com.app.teamdeco.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.teamdeco.R
import com.app.teamdeco.data.model.Ticker

class TickerAdapter : RecyclerView.Adapter<TickerAdapter.TickerViewHolder>() {

    private var items: List<Ticker> = emptyList()

    class TickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeTextView: TextView = itemView.findViewById(R.id.code)
        val priceTextView: TextView = itemView.findViewById(R.id.tradePrice)
        val accPriceTextView: TextView = itemView.findViewById(R.id.atp24h)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticker, parent, false)
        return TickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        val item = items[position]
        holder.codeTextView.text = item.code
        holder.priceTextView.text = item.tradePrice.toString()
        holder.accPriceTextView.text = item.atp24h.toString()
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Ticker>) {
        items = newItems
        notifyDataSetChanged()
    }
}