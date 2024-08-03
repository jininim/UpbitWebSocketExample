package com.app.teamdeco.ui.adapter

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.teamdeco.R
import com.app.teamdeco.data.model.PriceChange
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
        when(item.priceChange){
            PriceChange.UP -> {
                // 상승 이벤트 처리 (예: 배경색 변경)
                holder.priceTextView.setBackgroundResource(R.drawable.border_up)
     //           animateView(holder.priceTextView, 1.1f, 1.1f) // 크기 변화 애니메이션
                snapView(holder.priceTextView)
            }
            PriceChange.DOWN -> {
                // 하락 이벤트 처리 (예: 배경색 변경)
                holder.priceTextView.setBackgroundResource(R.drawable.border_down)
     //           animateView(holder.priceTextView,0.9f, 0.9f) // 크기 변화 애니메이션
                snapView(holder.priceTextView)
            }
            PriceChange.NONE -> {
                // 변동 없음
                holder.priceTextView.setBackgroundResource(R.drawable.border_none)
                holder.priceTextView.scaleX = 1.0f
                holder.priceTextView.scaleY = 1.0f

            }
        }
        holder.priceTextView.text = item.tradePrice.toString()
        holder.accPriceTextView.text = item.atp24h.toString()
    }

    override fun getItemCount(): Int = items.size

    fun setData(newItems: List<Ticker>) {
        items = newItems
        notifyDataSetChanged()
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