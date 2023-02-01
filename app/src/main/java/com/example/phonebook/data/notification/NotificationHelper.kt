package com.example.phonebook.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.phonebook.MainActivity
import com.example.phonebook.R

object NotificationHelper {

    fun createNotificationChannel(context: Context, importance: Int, @StringRes name: Int, @StringRes description: Int){
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                context.getString(name),
                context.getString(description),
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun buildNotification(
        context: Context,
        contactName: String,
        id:Int
    ): NotificationCompat.Builder {

        val channelId =  context.getString(R.string.id_for_channel)
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.icon_birthday)
            setContentTitle(context.getString(R.string.today_is_the_birthday, contactName))
            setContentText(context.getString(R.string.not_forget_to_congratulate))
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_DEFAULT
            val largeIcon = R.drawable.birthday_image
            setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIcon))
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("FRAGMENT_NAME", "SOME_FRAGMENT")
                putExtra("CONTACT_ID", id)
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP  or Intent.FLAG_ACTIVITY_CLEAR_TASK
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
            }

            val pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            setContentIntent(pendingIntent)
        }
    }

    fun createNotificationForBirthday(context: Context,  contactId:Int,contactName: String,) {
        val notificationBuilder = buildNotification(context, contactName,contactId)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(contactId, notificationBuilder.build())
        Log.d("AlarmBirthdayReceiver", contactId.toString() + contactName)
    }
}