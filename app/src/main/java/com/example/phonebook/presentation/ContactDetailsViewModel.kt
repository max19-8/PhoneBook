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

    private fun getContact() {
        val detailedInformationAboutContact = detailsContactUseCase(0)
        _contact.value = detailedInformationAboutContact
    }

    init {
        getContact()
    }

    fun changeNotifyStatus(notifyStatus: Boolean,id: Int) {
            if (notifyStatus) {
                contact.value?.let {
                    onReminderUseCase(id, it)
                }
            } else {
                offReminderUseCase(id)
            }
    }

    fun isAlarmSet(context: Context,contactId:Int) = isAlarmSetUseCase(context, contactId)
}