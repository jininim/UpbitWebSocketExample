package com.app.teamdeco.data.repository

import com.app.teamdeco.data.model.PriceChange
import com.app.teamdeco.data.model.Ticker
import com.app.teamdeco.data.network.UpbitWebSocketClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class TickerRepository @Inject constructor(
    private val webSocketClient: UpbitWebSocketClient
) {
    private val _tickerDataFlow = MutableStateFlow<List<Ticker>>(emptyList())
    val tickerDataFlow: StateFlow<List<Ticker>> get() = _tickerDataFlow

    // 현재 정렬 기준
    private var sortBy: SortBy = SortBy.ATP24H_DESC


    init {
        webSocketClient.setOnTickerDataReceived { newData ->
            val currentData = _tickerDataFlow.value.toMutableList()
            val index = currentData.indexOfFirst { it.code == newData.code }
            if (index >= 0) {
                // 기존 데이터가 있을 경우 현재가 비교
                newData.priceChange = when {
                    newData.tradePrice > currentData[index].tradePrice -> PriceChange.UP
                    newData.tradePrice < currentData[index].tradePrice -> PriceChange.DOWN
                    else -> PriceChange.NONE
                }

                // 기존 데이터 업데이트
                currentData[index] = newData

            } else {
                // 새로운 데이터 추가
                newData.priceChange = PriceChange.NONE
                currentData.add(newData)
            }
            _tickerDataFlow.value = sortData(currentData)
        }
    }

    fun startListening() {
        webSocketClient.startListenTicker()
    }

    // 현재가 내림차순으로 정렬
    fun sortByDescTradePrice() {
        sortBy = SortBy.TRADE_PRICE_DESC
        _tickerDataFlow.value = sortData(_tickerDataFlow.value)
    }
    // 현재가 오름차순으로 정렬
    fun sortByAscTradePrice() {
        sortBy = SortBy.TRADE_PRICE_ASC
        _tickerDataFlow.value = sortData(_tickerDataFlow.value)
    }

    // 거래대금 내림차순으로 정렬
    fun sortByDescAtp24h() {
        sortBy = SortBy.ATP24H_DESC
        _tickerDataFlow.value = sortData(_tickerDataFlow.value)
    }

    // 거래대금 내림차순으로 정렬
    fun sortByAscAtp24h() {
        sortBy = SortBy.ATP24H_ASC
        _tickerDataFlow.value = sortData(_tickerDataFlow.value)
    }

    private fun sortData(data: List<Ticker>): List<Ticker> {
        return when (sortBy) {
            SortBy.TRADE_PRICE_DESC -> data.sortedByDescending { it.tradePrice }
            SortBy.TRADE_PRICE_ASC -> data.sortedBy { it.tradePrice }
            SortBy.ATP24H_DESC -> data.sortedByDescending { it.atp24h }
            SortBy.ATP24H_ASC -> data.sortedBy { it.atp24h }
        }
    }

    private enum class SortBy {
        TRADE_PRICE_DESC,
        TRADE_PRICE_ASC,
        ATP24H_DESC,
        ATP24H_ASC
    }

}



