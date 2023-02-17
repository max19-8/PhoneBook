package com.example.phonebook

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.example.phonebook.data.notification.NotificationHelper
import com.example.phonebook.di.DaggerApplicationComponent

class App : Application() {


    val component by lazy {
       DaggerApplicationComponent.factory().create(this,contentResolver)
    }
    override fun onCreate() {
        super.onCreate()
        NotificationHelper().createNotificationChannel(
            this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, name = R.string.id_for_channel,
            R.string.name_for_channel
        )
    }
}