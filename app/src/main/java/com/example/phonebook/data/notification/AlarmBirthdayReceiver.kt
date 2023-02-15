package com.example.phonebook.data.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.phonebook.data.Contact
import java.util.*

class AlarmBirthdayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val contact = intent.getParcelableExtra<Contact>(CONTACT)
        if (contact !=null){
            val contactId = contact.id
            val date = contact.birthday as Calendar

            NotificationHelper.createNotificationForBirthday(context,contact)

            val alarmIntent =
                PendingIntent.getBroadcast(
                    context,
                    contactId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            BirthdayAlarmManger.createAlarmFromBirthday(context, date, alarmIntent)
        }
    }

    companion object {
        private const val CONTACT = "contact"
        fun newIntent(context: Context?) = Intent(context, AlarmBirthdayReceiver::class.java)
    }
}