package com.example.phonebook.presentation.contactListScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.phonebook.data.Contact
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase

class ContactListViewModel(
    private val listContactUseCase: ListContactUseCase
) {
    private val mutableContactList = MutableLiveData<List<Contact>>()
    val contactList = mutableContactList as LiveData<List<Contact>>

     fun getContacts(query:String) {
        val listDetailedInformationAboutContact = listContactUseCase(query)
        mutableContactList.value = listDetailedInformationAboutContact
    }

    init {
        getContacts(EMPTY_QUERY)
    }

    companion object {
        const val EMPTY_QUERY = ""
    }
}