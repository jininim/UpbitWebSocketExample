package com.app.teamdeco.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.teamdeco.R
import com.app.teamdeco.data.model.PriceChange
import com.app.teamdeco.data.model.Ticker
import com.app.teamdeco.databinding.ItemTickerBinding

class TickerAdapter : RecyclerView.Adapter<TickerAdapter.TickerViewHolder>() {


    private var items: List<Ticker> = emptyList()

    class TickerViewHolder(val binding: ItemTickerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val binding = ItemTickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TickerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        val item = items[position]
        val binding = holder.binding
        binding.code.text = item.code
        when(item.priceChange){
            PriceChange.UP -> {
                // 상승 이벤트 처리 (예: 배경색 변경)
                binding.tradePrice.setBackgroundResource(R.drawable.border_up)
                snapView(binding.tradePrice)
            }
            PriceChange.DOWN -> {
                // 하락 이벤트 처리 (예: 배경색 변경)
                binding.tradePrice.setBackgroundResource(R.drawable.border_down)
                snapView(binding.tradePrice)
            }
            PriceChange.NONE -> {
                // 변동 없음
                binding.tradePrice.setBackgroundResource(R.drawable.border_none)
                binding.tradePrice.scaleX = 1.0f
                binding.tradePrice.scaleY = 1.0f

            }
        }
        binding.tradePrice.text = item.tradePrice.toString()
        binding.atp24h.text = item.atp24h.toString()
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Ticker>) {
        items = newItems
        notifyDataSetChanged()
    }

    //애니메이션
    private fun snapView(view: View) {
        view.animate()
            .translationY(-10f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .translationY(0f)
                    .setDuration(100)
                    .start()
            }
            .start()
    }
}

//fun animateView(view: View, startAlpha: Float, endAlpha: Float) {
//    view.animate()
//        .scaleX(startAlpha)
//        .scaleY(endAlpha)
//        .setDuration(500)
//        .setInterpolator(AccelerateDecelerateInterpolator())
//        .start()
//}

