package com.example.phonebook.presentation.detailContactScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.phonebook.data.Contact
import com.example.phonebook.domain.useCase.broadcast.IsAlarmSetUseCase
import com.example.phonebook.domain.useCase.broadcast.OffReminderUseCase
import com.example.phonebook.domain.useCase.broadcast.OnReminderUseCase
import javax.inject.Inject

class ContactDetailsViewModel @Inject constructor(
    private val onReminderUseCase: OnReminderUseCase,
    private val offReminderUseCase: OffReminderUseCase,
    private val isAlarmSetUseCase: IsAlarmSetUseCase,
):ViewModel() {

fun changeNotifyStatus(notifyStatus: Boolean,contact: Contact) {
    if (notifyStatus) {
        onReminderUseCase(contact)
    } else {
        offReminderUseCase(contact)
    }
}
        fun isAlarmSet(context: Context, contact: Contact) = isAlarmSetUseCase(context, contact)

}