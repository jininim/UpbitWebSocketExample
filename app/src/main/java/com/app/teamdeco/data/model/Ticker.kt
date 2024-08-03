package com.app.teamdeco.data.model

import com.google.gson.annotations.SerializedName

data class Ticker(
    val code: String, //마켓 코드 (ex. KRW-BTC)
    @SerializedName("trade_price")
    val tradePrice: Double, //현재가
    @SerializedName("acc_trade_price_24h")
    val atp24h: Double,//24시간 누적 거래대금
    var priceChange: PriceChange = PriceChange.NONE // 가격 변동 상태
)

enum class PriceChange {
    UP, DOWN, NONE
}
