package com.geonwoo.betterthanyesterday.data.repository

import com.geonwoo.betterthanyesterday.data.vo.Item
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getWeatherItem(
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Item? = try {
        val apiResult = weatherService.getWeatherData(
            baseDate, baseTime, nx, ny
        ).body()?.response

        val item = apiResult?.body?.items?.item?.find {
            it.category == "T1H"
        }

        item
    } catch (e: Exception) {
        null
    }
}