package com.app.teamdeco.ui.view

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.teamdeco.R
import com.app.teamdeco.databinding.ActivityMainBinding
import com.app.teamdeco.ui.adapter.TickerAdapter
import com.app.teamdeco.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TickerAdapter

    private lateinit var tradePriceButton : Button
    private lateinit var atp24hButton : Button

    //정렬 상태 변수
    private var isTradePriceSortedByDesc = false
    private var isAtp24hSortedByDesc = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tradePriceButton = binding.btTradePrice
        atp24hButton = binding.btAtp24h

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView 설정
        val recyclerView = binding.recyclerviewTicker
        adapter = TickerAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //정렬 상태에 따른 클릭 이벤트 변경
        tradePriceButton.setOnClickListener {
            if (!isTradePriceSortedByDesc){
                mainViewModel.sortByDescTradePrice()
                isTradePriceSortedByDesc = true
            }else{
                mainViewModel.sortByAscTradePrice()
                isTradePriceSortedByDesc = false
            }

        }
        atp24hButton.setOnClickListener {
            if (!isAtp24hSortedByDesc){
                mainViewModel.sortByDescAtp24h()
                isAtp24hSortedByDesc = true
            }else{
                mainViewModel.sortByAscAtp24h()
                isAtp24hSortedByDesc = false
            }
        }

        mainViewModel.tickerDataList.observe(this) { data ->
            adapter.setData(data)
        }




    }
}