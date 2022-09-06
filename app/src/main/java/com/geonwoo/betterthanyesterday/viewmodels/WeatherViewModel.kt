package com.geonwoo.betterthanyesterday.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geonwoo.betterthanyesterday.data.repository.WeatherRepository
import com.geonwoo.betterthanyesterday.data.vo.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var weatherLiveData = MutableLiveData<Map<String, Item?>>()

    fun getWeatherData(baseMap: Map<String, String>, nx: Int, ny: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val itemMap = baseMap.map {
                async { // this will allow us to run multiple tasks in parallel
                    val item = weatherRepository.getWeatherItem(it.key, it.value, nx, ny)
                    Log.d("Jungbong", item.toString())
                    it.key to item // associate id and response for later
                }
            }
            Log.d("Jungbong", baseMap.toString())

            val result = itemMap.awaitAll().toMap()

            weatherLiveData.postValue(result)
        }
}