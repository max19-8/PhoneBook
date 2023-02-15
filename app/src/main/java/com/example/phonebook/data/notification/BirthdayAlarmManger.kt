package com.example.phonebook.data.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import java.util.*

object BirthdayAlarmManger {

    fun createAlarmFromBirthday(
        context: Context,
        contactBirthday: Calendar,
        pendingIntent: PendingIntent
    ) {

        val date = putNextBirthday(contactBirthday, Calendar.getInstance()).timeInMillis

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        alarmManager?.setExact(AlarmManager.RTC_WAKEUP, date, pendingIntent)
    }
}