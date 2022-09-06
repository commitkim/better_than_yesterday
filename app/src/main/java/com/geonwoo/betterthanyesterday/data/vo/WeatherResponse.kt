package com.geonwoo.betterthanyesterday.data.vo

data class JsonWeatherResponse(
    val response: WeatherResponse? = null
)

data class WeatherResponse(
    val header: Header? = null,
    val body: Body? = null,
    val pageNo: Int? = null,
    val numOfRows: Int? = null,
    val totalCount: Int? = null,
)

data class Header(
    val resultCode: String? = null,
    val resultMsg: String? = null,
)

data class Body(
    val dataType: String? = null,
    val items: Items? = null
)

data class Items(
    val item: List<Item>? = null
)

data class Item(
    val baseDate: String? = null,
    val baseTime: String? = null,
    val category: String? = null,
    val nx: Int? = null,
    val ny: Int? = null,
    val obsrValue: String? = null
)