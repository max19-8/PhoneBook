package com.example.phonebook.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.phonebook.data.Contact
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase

class ContactListViewModel(
    private val listContactUseCase: ListContactUseCase
)  {
    private val mutableContactList  = MutableLiveData<List<Contact>>()
    val contactList = mutableContactList as LiveData<List<Contact>>

    private fun getContacts() {
        val listDetailedInformationAboutContact = listContactUseCase()
        mutableContactList.value = listDetailedInformationAboutContact
    }

    init {
        getContacts()
    }
}