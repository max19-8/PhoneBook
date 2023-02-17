package com.example.phonebook.domain.useCase.broadcast

import android.content.Context
import com.example.phonebook.data.Contact
interface BroadcastRepository {
    fun offReminder(contact: Contact)
    fun onReminder(contact: Contact)
    fun isAlarmSet(context: Context, contact: Contact): Boolean
}