package com.app.teamdeco.data

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


    init {
        webSocketClient.setOnTickerDataReceived { newData ->
            val currentData = _tickerDataFlow.value.toMutableList()
            val index = currentData.indexOfFirst { it.code == newData.code }
            if (index >= 0) {
                // 기존 데이터 업데이트
                currentData[index] = newData
            } else {
                // 새로운 데이터 추가
                currentData.add(newData)
            }
            _tickerDataFlow.value = currentData
        }
    }

    fun startListening() {
        webSocketClient.startListenTicker()
    }

}