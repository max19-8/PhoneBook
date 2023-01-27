package com.example.phonebook.domain.useCase.listContact

import com.example.phonebook.data.Contact

interface ListContactRepository {
    fun getContactList():List<Contact>
}