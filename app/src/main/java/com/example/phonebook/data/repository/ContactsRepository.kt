package com.example.phonebook.data.repository

import android.content.Context
import com.example.phonebook.data.Contact
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.listOfContacts
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import com.example.phonebook.domain.useCase.contactDetail.DetailsContactRepository
import com.example.phonebook.domain.useCase.listContact.ListContactRepository

class ContactsRepository(private val notificationSwitcher: NotificationSwitcher) : BroadcastRepository, ListContactRepository, DetailsContactRepository {

    override fun offReminder(id: Int) {
      notificationSwitcher.offReminder(id)
    }

    override fun onReminder(id: Int, contact: Contact) {
        notificationSwitcher.onReminder(id, contact)
    }

    override fun isAlarmSet(context: Context,contactId:Int): Boolean =
        notificationSwitcher.isAlarmSet(context, contactId)


    override fun getContactDetails(id:Int): Contact  = listOfContacts[id]
    override fun getContactList(): List<Contact> = listOfContacts
}