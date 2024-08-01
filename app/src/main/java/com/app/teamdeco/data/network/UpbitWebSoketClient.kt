package com.app.teamdeco.data.network

import android.util.Log
import com.app.teamdeco.data.model.Coin
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class UpbitWebSocketClient() {
    data class Ticket(val ticket: String)
    data class Type(val type: String, val codes: List<String>)


    private val gson = Gson()
    private val okHttpClient = OkHttpClient()

    private var webSocketList: MutableList<WebSocket> = mutableListOf()


    fun startListenTicker() {
        val request = Request.Builder()
            .url("wss://api.upbit.com/websocket/v1")
            .build()

        val webSocket = okHttpClient.newWebSocket(request, orderBookListener)
        webSocketList.add(webSocket)
    }

    private val orderBookListener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            //연결 성공
            Log.i("JINNN", "WebSoket 연결 성공")
            webSocket.send(createTicket()) //요청
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)

            val bytesToString = bytes.toByteArray().decodeToString()
//            Log.i("JINNN", "bytesToString:  $bytesToString")
            val coin = gson.fromJson(bytesToString, Coin::class.java)
            Log.i("JINNN", "coin:  $coin")
            // TODO coin handle
        }
    }

    fun createTicket(): String {
        val ticket = Ticket("ticker")
        val codeList = arrayListOf(
            "KRW-SAND",
            "KRW-BTC",
            "KRW-XRP",
            "KRW-SOL",
            "KRW-ETH",
            "KRW-SHIB",
            "KRW-SEI",
            "KRW-NEAR",
            "KRW-ID",
            "KRW-AVAX",
            "KRW-DOGE",
            "KRW-SUI",
            "KRW-ETC",
            "KRW-BTG",
            "KRW-CTC",
            "KRW-ASTR",
            "KRW-MINA",
            "KRW-SC",
            "KRW-ZRX",
            "KRW-WAVES"
        )
        /* 데이터 타입
            - ticker: 현재가
            - trade: 체결
            - orderbook: 호가
        */
        val type = Type("ticker", codeList)

        return gson.toJson(arrayListOf(ticket, type))
    }
}