package com.example.phonebook.data.repository

import android.content.Context
import android.util.Log
import com.example.phonebook.data.Contact
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.localDataSource.ContactProvider
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import com.example.phonebook.domain.useCase.contactDetail.DetailsContactRepository
import com.example.phonebook.domain.useCase.listContact.ListContactRepository

class ContactsRepository(private val contactProvider: ContactProvider,private val notificationSwitcher: NotificationSwitcher) :
    BroadcastRepository, ListContactRepository, DetailsContactRepository {

    override fun offReminder(contact: Contact) {
        notificationSwitcher.offReminder(contact)
    }

    override fun onReminder(contact: Contact) {
        notificationSwitcher.onReminder(contact)
    }

    override fun isAlarmSet(context: Context, contact: Contact): Boolean =
        notificationSwitcher.isAlarmSet(context, contact)


    override fun getContactDetails(id: Int): Contact = contactProvider.getDetailContact(id).first()
    override fun getContactList(query: String): List<Contact> {
        Log.d("ContactsRepository", contactProvider.getContactListData(query = query).toString())
       return contactProvider.getContactListData(query = query)
    }
}
