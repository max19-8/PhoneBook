package com.example.phonebook.presentation

import android.content.Context
import com.example.phonebook.data.Contact
import com.example.phonebook.domain.useCase.broadcast.IsAlarmSetUseCase
import com.example.phonebook.domain.useCase.broadcast.OffReminderUseCase
import com.example.phonebook.domain.useCase.broadcast.OnReminderUseCase

class ContactDetailsViewModel(
    private val onReminderUseCase: OnReminderUseCase,
    private val offReminderUseCase: OffReminderUseCase,
    private val isAlarmSetUseCase: IsAlarmSetUseCase,
) {

fun changeNotifyStatus(notifyStatus: Boolean,contact: Contact) {
    if (notifyStatus) {
        onReminderUseCase(contact)
    } else {
        offReminderUseCase(contact)
    }
}
        fun isAlarmSet(context: Context, contact: Contact) = isAlarmSetUseCase(context, contact)

}