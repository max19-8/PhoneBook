package com.example.phonebook.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import com.example.phonebook.notification.AlarmBirthdayReceiver
import com.example.phonebook.notification.putNextBirthday
import java.util.*

class NotificationSwitcher(private val context: Context) : BroadcastRepository {

    private val intent = AlarmBirthdayReceiver.newIntent(context)

    override fun offReminder(id: Int) {
        val alarmIntent =  id.let {
            PendingIntent.getBroadcast(
                context,
                it,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(alarmIntent)
        alarmIntent?.cancel()
    }

    override fun onReminder(id: Int, contact: Contact) {
        val intent = AlarmBirthdayReceiver.newIntent(context)
        intent.putExtra(FULL_NAME, contact.name)
        intent.putExtra(CONTACT_BIRTHDAY, contact.birthday)
        intent.putExtra(CONTACT_ID,  contact.id)
        val alarmIntent =  contact.id.let {
            PendingIntent.getBroadcast(
                context,
                it,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            putNextBirthday(contact.birthday,
                Calendar.getInstance()).timeInMillis,
            alarmIntent
        )
    }

    override fun isAlarmSet(context: Context,contactId:Int): Boolean {
            val intent = AlarmBirthdayReceiver.newIntent(context)
            val alarmIntent = contactId.let {
                PendingIntent.getBroadcast(
                    context,
                    it,
                    intent,
                    PendingIntent.FLAG_NO_CREATE
                )
            }
            return alarmIntent != null
        }


    companion object {
        private const val CONTACT_ID = "id"
        private const val FULL_NAME = "FULL_NAME"
        private const val CONTACT_BIRTHDAY = "contactBirthday"
    }
}