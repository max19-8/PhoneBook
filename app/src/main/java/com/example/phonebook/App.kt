package com.example.phonebook

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.example.phonebook.notification.NotificationHelper

const val KEY_ID = "id32132131"
class App :Application(){

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, true, name = "createSampleDataNotification",
            "this is test notification")

        NotificationHelper.createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, true, name = "buildNotification",
            "this is the birthday notification channel")
    }
}