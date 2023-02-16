package com.example.phonebook

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.example.phonebook.data.notification.NotificationHelper


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, name = R.string.id_for_channel,
            R.string.name_for_channel
        )
    }
}