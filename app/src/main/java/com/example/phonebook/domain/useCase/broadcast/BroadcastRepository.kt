package com.example.phonebook.domain.useCase.broadcast

import android.content.Context
import com.example.phonebook.data.Contact

interface BroadcastRepository {

    fun offReminder(id: Int)

    fun onReminder(id: Int, contact: Contact)

    fun isAlarmSet(context: Context, contactId: Int): Boolean

}