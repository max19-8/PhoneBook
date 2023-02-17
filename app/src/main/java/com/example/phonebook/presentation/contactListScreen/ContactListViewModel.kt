package com.example.phonebook.presentation.contactListScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phonebook.data.Contact
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val listContactUseCase: ListContactUseCase
): ViewModel() {
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