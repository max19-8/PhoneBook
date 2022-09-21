package com.example.phonebook.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.phonebook.KEY_ID
import com.example.phonebook.MainActivity
import com.example.phonebook.R
import com.example.phonebook.data.Contact
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object NotificationHelper {

    fun createNotificationChannel(context: Context, importance: Int,
                                  showBadge: Boolean, name: String, description: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(
        context: Context,
        contact: Contact
    ): NotificationCompat.Builder {
        val channelId = "${context.packageName}-buildNotification"
        val currentDate = Date()
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeText: String = timeFormat.format(currentDate)
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setContentTitle(contact.name)
            setAutoCancel(true)
            val largeIcon = R.drawable.contact
            setSubText(timeText)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIcon))
            setContentText(contact.number)
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            setContentIntent(pendingIntent)
        }
    }

    fun createNotificationForBirthday(context: Context, contact: Contact) {
        val notificationBuilder = buildNotification(context, contact)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(contact.id, notificationBuilder.build())
    }
}