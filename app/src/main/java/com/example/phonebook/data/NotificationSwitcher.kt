package com.example.phonebook.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.phonebook.data.notification.AlarmBirthdayReceiver
import com.example.phonebook.data.notification.BirthdayAlarmManger
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import javax.inject.Inject

class NotificationSwitcher  @Inject constructor (private val context: Context) : BroadcastRepository {

    override fun offReminder(contact: Contact) {
        val intent = AlarmBirthdayReceiver.newIntent(context)

        val alarmIntent = createPendingIntent(
            contact,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(alarmIntent)
        alarmIntent?.cancel()
    }

    override fun onReminder(contact: Contact) {
        val intent = AlarmBirthdayReceiver.newIntent(context)
        intent.apply {
            putExtra(CONTACT, contact)
        }
        val existingIntent = isAlarmSet(context, contact)
        if (!existingIntent) {
            val alarmIntent = createPendingIntent(
                contact,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            contact.birthday?.let {
                BirthdayAlarmManger().createAlarmFromBirthday(context,
                    it, alarmIntent)
            }
        }
    }
    override fun isAlarmSet(context: Context, contact: Contact): Boolean {
        val intent = AlarmBirthdayReceiver.newIntent(context)
        val alarmIntent = createPendingIntent(
            contact,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        )
        return alarmIntent != null
    }

    private fun createPendingIntent(contact: Contact, intent: Intent, flag: Int) =
        PendingIntent.getBroadcast(
            context,
            contact.id,
            intent,
            flag
        )
    companion object {
        private const val CONTACT = "contact"
    }
}