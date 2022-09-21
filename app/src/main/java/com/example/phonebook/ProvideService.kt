package com.example.phonebook

import com.example.phonebook.data.ContactService

interface ProvideService {
    fun getService():ContactService?
}