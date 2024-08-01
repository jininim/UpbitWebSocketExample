package com.app.teamdeco.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.app.teamdeco.data.model.Coin
import com.app.teamdeco.databinding.ActivityMainBinding

class CoinItemAdapter (private var items: List<Coin>) : RecyclerView.Adapter<CoinItemAdapter.CoinItemViewHolder>() {

    inner class CoinItemViewHolder(binding: ActivityMainBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Coin) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemViewHolder {
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<Coin>) {
        items = newItems
        notifyDataSetChanged()
    }
}