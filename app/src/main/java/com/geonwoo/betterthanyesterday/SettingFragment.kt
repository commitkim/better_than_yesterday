package com.geonwoo.betterthanyesterday

//import com.geonwoo.betterthanyesterday.receiver.AlarmReceiver
//import com.geonwoo.betterthanyesterday.receiver.DeviceBootReceiver
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.geonwoo.betterthanyesterday.databinding.FragmentSettingBinding
import com.geonwoo.betterthanyesterday.viewmodels.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setTimePicker() {
        // 앞서 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재시간
        Calendar.getInstance().apply {
            timeInMillis = viewModel.getAlarmMills()
        }.also {
            binding.timePicker.hour = it[Calendar.HOUR_OF_DAY]
            binding.timePicker.hour = it[Calendar.MINUTE]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.timePicker.setIs24HourView(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        binding.okButton.setOnClickListener {
            // 현재 지정된 시간으로 알람 시간 설정
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar[Calendar.HOUR_OF_DAY] = binding.timePicker.hour
            calendar[Calendar.MINUTE] = binding.timePicker.minute
            calendar[Calendar.SECOND] = 0

            // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1)
            }

            viewModel.setAlarmMills(calendar.timeInMillis)

//            diaryNotification(calendar)
        }
    }

//    fun diaryNotification(calendar: Calendar) {
//        val pm: PackageManager = requireActivity().packageManager
//        val receiver = ComponentName(requireContext(), DeviceBootReceiver::class.java)
//        val alarmIntent = Intent(context, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)
//        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//
//
//        // 사용자가 매일 알람을 허용했다면
//        if (alarmManager != null) {
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
//                AlarmManager.INTERVAL_DAY, pendingIntent
//            )
//
//            // 부팅 후 실행되는 리시버 사용가능하게 설정
//            pm.setComponentEnabledSetting(
//                receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP
//            )
//        }
//    }

}