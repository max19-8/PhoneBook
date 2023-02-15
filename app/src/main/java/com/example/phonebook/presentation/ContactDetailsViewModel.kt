package com.example.phonebook.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.phonebook.data.Contact
import com.example.phonebook.domain.useCase.broadcast.IsAlarmSetUseCase
import com.example.phonebook.domain.useCase.broadcast.OffReminderUseCase
import com.example.phonebook.domain.useCase.broadcast.OnReminderUseCase
import com.example.phonebook.domain.useCase.contactDetail.DetailsContactUseCase

class ContactDetailsViewModel(
    private val detailsContactUseCase: DetailsContactUseCase,
    private val onReminderUseCase: OnReminderUseCase,
    private val offReminderUseCase: OffReminderUseCase,
    private val isAlarmSetUseCase: IsAlarmSetUseCase,
) {
//    private val _contact = MutableLiveData<Contact>()
//    val contact: LiveData<Contact> get() = _contact

//    fun getContact(id: Int) {
//        val detailedInformationAboutContact = detailsContactUseCase(id)
//        Log.d("ContactDetailsViewModel", detailedInformationAboutContact.toString())
//        _contact.value = detailedInformationAboutContact
//    }

//    fun changeNotifyStatus(notifyStatus: Boolean,contact: Contact) {
//        this._contact.value = contact
//        if (notifyStatus) {
//            onReminderUseCase(liveContact.value)
//        } else {
//            offReminderUseCase(liveContact.value)
//        }
//    }
fun changeNotifyStatus(notifyStatus: Boolean,contact: Contact) {
    if (notifyStatus) {
        onReminderUseCase(contact)
    } else {
        offReminderUseCase(contact)
    }
}
        fun isAlarmSet(context: Context, contact: Contact) = isAlarmSetUseCase(context, contact)

}