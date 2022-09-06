package com.geonwoo.betterthanyesterday.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.geonwoo.betterthanyesterday.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("yesterdayTemp", "todayTemp")
fun setMainText(
    textView: TextView,
    yesterdayTemp: Float,
    todayTemp: Float
) {
    textView.text = if (yesterdayTemp < todayTemp) {
        "어제보다 따뜻해요"
    } else if (yesterdayTemp > todayTemp) {
        "어제보다 쌀쌀해요"
    } else {
        "어제와 비슷해요"
    }
}

@BindingAdapter("yesterdayTemp", "todayTemp")
fun setImage(
    imageView: ImageView,
    yesterdayTemp: Float,
    todayTemp: Float
) {

    Glide.with(imageView.context)
        .load(
            if (yesterdayTemp < todayTemp) {
                R.drawable.up
            } else if (yesterdayTemp > todayTemp) {
                R.drawable.down
            } else {
                -1
            }
        )
        .into(imageView)
}

@BindingAdapter("temperature")
fun temperature(
    textView: TextView,
    temp: Float
) {
    textView.text = "${temp}º"
}

@BindingAdapter("base_time")
fun baseTime(
    textView: TextView,
    baseTime: String?
) {
    textView.text = "$baseTime 기준"
}
