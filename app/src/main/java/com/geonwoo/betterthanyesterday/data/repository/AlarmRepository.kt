package com.geonwoo.betterthanyesterday.data.repository

import com.geonwoo.betterthanyesterday.data.SharedPref
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(
    private val sharedPref: SharedPref
){
    fun getAlarmMills(): Long =
        sharedPref.getSharedPreferences("nextNotifyTime", Calendar.getInstance().timeInMillis)


    fun setAlarmMills(mills: Long) {
        sharedPref.setSharedPreferences("nextNotifyTime", mills)
    }
}