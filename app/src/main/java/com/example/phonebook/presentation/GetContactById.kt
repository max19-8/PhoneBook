package com.example.phonebook.presentation

import com.example.phonebook.data.Contact

interface GetContactById {
    fun getContact(contact:Contact)
}