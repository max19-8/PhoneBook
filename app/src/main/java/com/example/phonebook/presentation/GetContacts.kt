package com.example.phonebook.presentation

import com.example.phonebook.data.Contact

interface GetContacts {
    fun getContacts(list:List<Contact>)
}