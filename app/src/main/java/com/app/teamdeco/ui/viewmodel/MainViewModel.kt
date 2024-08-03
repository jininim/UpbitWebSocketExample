package com.app.teamdeco.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.teamdeco.data.repository.TickerRepository
import com.app.teamdeco.data.model.Ticker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor (
    private val tickerRepository: TickerRepository
) : ViewModel() {

    val tickerDataList: LiveData<List<Ticker>> = tickerRepository.tickerDataFlow.asLiveData()

    init {
//        Log.i("JINNN" , "viewModel init")
        tickerRepository.startListening()
    }
    // 현재가 내림차순으로 정렬
    fun sortByDescTradePrice() {
        tickerRepository.sortByDescTradePrice()
    }
    // 현재가 오름차순으로 정렬
    fun sortByAscTradePrice() {
        tickerRepository.sortByAscTradePrice()
    }
    // 거래대금 내림차순으로 정렬
    fun sortByDescAtp24h() {
        tickerRepository.sortByDescAtp24h()
    }
    // 거래대금 내림차순으로 정렬
    fun sortByAscAtp24h() {
        tickerRepository.sortByAscAtp24h()
    }

}
