package com.app.teamdeco.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.teamdeco.data.TickerRepository
import com.app.teamdeco.data.model.Ticker
import com.app.teamdeco.ui.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor (
    private val tickerRepository: TickerRepository,
    mainActivity: MainActivity
) : ViewModel() {
    init {
        tickerRepository.startListening()
    }

    val tickerDataList: LiveData<List<Ticker>> = tickerRepository.tickerDataFlow.asLiveData()


}

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//    @Provides
//    fun provideUserRepository(): UserRepository {
//        return UserRepository()
//    }
//}