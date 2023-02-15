package com.example.phonebook.domain.useCase.contactDetail

import com.example.phonebook.data.Contact

interface DetailsContactRepository {
    fun getContactDetails(id: Int): Contact
}