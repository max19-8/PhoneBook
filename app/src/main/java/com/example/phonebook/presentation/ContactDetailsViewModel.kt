package com.example.phonebook.presentation

import android.content.Context
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
    private val _contact = MutableLiveData<Contact>()
    val contact:LiveData<Contact> get() = _contact

    private val _isAlarmSet = MutableLiveData<Boolean>()
    val isAlarmSet :LiveData<Boolean> get() = _isAlarmSet

    private fun getContact() {
        val detailedInformationAboutContact = detailsContactUseCase(0)
        _contact.value = detailedInformationAboutContact
    }

    init {
        getContact()
    }

    fun offReminder(id: Int) {
        offReminderUseCase(id)
    }

    fun onReminder(id: Int, contact: Contact) {
        onReminderUseCase(id, contact)
    }

    fun isAlarmSet(context: Context,contactId:Int) = isAlarmSetUseCase(context, contactId)
}