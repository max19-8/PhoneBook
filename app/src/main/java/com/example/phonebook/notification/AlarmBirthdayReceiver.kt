package com.example.phonebook.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.phonebook.KEY_ID
import com.example.phonebook.ProvideService
import com.example.phonebook.data.Contact
import com.example.phonebook.data.ContactService
import com.example.phonebook.presentation.GetContactById

class AlarmBirthdayReceiver : BroadcastReceiver() {

    private val TAG = AlarmBirthdayReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(
            TAG,
            "сам интент= [$context], intent = [$intent], ${intent?.extras}"
        )
        if (context != null && intent != null) {
            if (intent.extras != null) {
                val extras = intent.extras!!.get(KEY_ID)
                val contact = ContactService.getContactById(intent.extras!!.getInt(KEY_ID))
                Log.d(TAG, "интент значение  =${intent.extras!!.getInt(KEY_ID)}")
                Log.d(TAG, "extras  =${extras}")
                NotificationHelper.createNotificationForBirthday(context, contact)
                Log.d(TAG, "экземпляр контакта=$contact")
            }
        }
    }
}

