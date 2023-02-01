package com.example.phonebook.data.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmBirthdayReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val contactId = intent.getIntExtra(CONTACT_ID, 0)
        val contactName = intent.getStringExtra(FULL_NAME) ?: ""
        val date = intent.getSerializableExtra(CONTACT_BIRTHDAY) as Calendar

        NotificationHelper.createNotificationForBirthday(context, contactId, contactName)

        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                contactId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        BirthdayAlarmManger.createAlarmFromBirthday(context, date, alarmIntent)

    }

    companion object {
        private const val CONTACT_ID = "id"
        private const val FULL_NAME = "FULL_NAME"
        private const val CONTACT_BIRTHDAY = "contactBirthday"

        fun newIntent(context: Context?) = Intent(context, AlarmBirthdayReceiver::class.java)
    }
}

