package com.geonwoo.betterthanyesterday.data.repository

import com.geonwoo.betterthanyesterday.BuildConfig
import com.geonwoo.betterthanyesterday.data.vo.JsonWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("getUltraSrtNcst")
    suspend fun getWeatherData(
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query(value = "serviceKey", encoded = true) serviceKey: String = BuildConfig.SERVICE_KEY,
        @Query("dataType") dataType: String = "JSON"
    ): Response<JsonWeatherResponse>
}