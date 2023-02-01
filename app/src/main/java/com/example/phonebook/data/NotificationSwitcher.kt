package com.example.phonebook.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import com.example.phonebook.data.notification.AlarmBirthdayReceiver
import com.example.phonebook.data.notification.BirthdayAlarmManger


class NotificationSwitcher(private val context: Context) : BroadcastRepository {


    override fun offReminder(id: Int) {
        val intent = AlarmBirthdayReceiver.newIntent(context)

        val alarmIntent = createPendingIntent(
            id,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(alarmIntent)
        alarmIntent?.cancel()
    }

    override fun onReminder(id: Int, contact: Contact) {
        val intent = AlarmBirthdayReceiver.newIntent(context)
        intent.apply {
            putExtra(FULL_NAME, contact.name)
            putExtra(CONTACT_BIRTHDAY, contact.birthday)
            putExtra(CONTACT_ID, contact.id)
        }
        val existingIntent = isAlarmSet(context, contact.id)
        if (!existingIntent) {
            val alarmIntent = createPendingIntent(
                contact.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            BirthdayAlarmManger.createAlarmFromBirthday(context, contact.birthday, alarmIntent)


        }
    }


    override fun isAlarmSet(context: Context, contactId: Int): Boolean {
        val intent = AlarmBirthdayReceiver.newIntent(context)

        val alarmIntent = createPendingIntent(
            contactId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        )

        return alarmIntent != null
    }

    private fun createPendingIntent(contactId: Int, intent: Intent, flag: Int) =
        PendingIntent.getBroadcast(
            context,
            contactId,
            intent,
            flag
        )


    companion object {
        private const val CONTACT_ID = "id"
        private const val FULL_NAME = "FULL_NAME"
        private const val CONTACT_BIRTHDAY = "contactBirthday"
    }
}