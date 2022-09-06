package com.geonwoo.betterthanyesterday.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import com.geonwoo.betterthanyesterday.data.SharedPref
import java.util.*

class DeviceBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {

            // on device boot complete, reset the alarm
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val sharedPreferences = context.getSharedPreferences(SharedPref.PREFERENCE_NAME, MODE_PRIVATE)
            val millis = sharedPreferences.getLong(
                "nextNotifyTime",
                Calendar.getInstance().timeInMillis
            )
            val currentCalendar: Calendar = Calendar.getInstance()
            val nextNotifyTime: Calendar = GregorianCalendar()

            nextNotifyTime.timeInMillis = sharedPreferences.getLong("nextNotifyTime", millis)

            if (currentCalendar.after(nextNotifyTime)) {
                nextNotifyTime.add(Calendar.DATE, 1)
            }

            manager.setRepeating(
                AlarmManager.RTC_WAKEUP, nextNotifyTime.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )
        }
    }
}



