package com.geonwoo.betterthanyesterday.viewmodels

import androidx.lifecycle.ViewModel
import com.geonwoo.betterthanyesterday.data.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val alarmRepository: AlarmRepository
) : ViewModel() {

    fun getAlarmMills(): Long = alarmRepository.getAlarmMills()

    fun setAlarmMills(mills: Long) {
        alarmRepository.setAlarmMills(mills)
    }

}