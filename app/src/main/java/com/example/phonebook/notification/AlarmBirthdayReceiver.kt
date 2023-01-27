package com.example.phonebook.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmBirthdayReceiver : BroadcastReceiver() {

    private val TAG = AlarmBirthdayReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            if (intent.extras != null) {
                val contactId = intent.getIntExtra(CONTACT_ID,0)
                val contactName = intent.getStringExtra(FULL_NAME) ?: ""

               NotificationHelper.createNotificationForBirthday(context, contactId,contactName)

                val alarmIntent =
                    PendingIntent.getBroadcast(context, contactId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

                alarmManager?.set(
                    AlarmManager.RTC_WAKEUP,
                    putNextBirthday(intent.getSerializableExtra(CONTACT_BIRTHDAY) as Calendar, Calendar.getInstance()).timeInMillis,
                    alarmIntent
                )
            }
        }
    }
    companion object{
        private const val CONTACT_ID = "id"
        private const val FULL_NAME = "FULL_NAME"
        private const val CONTACT_BIRTHDAY = "contactBirthday"

        fun newIntent(context: Context?) = Intent(context, AlarmBirthdayReceiver::class.java)
    }
}

