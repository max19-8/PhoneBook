package com.example.phonebook.data.repository

import android.content.Context
import android.util.Log
import com.example.phonebook.data.Contact
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.localDataSource.ContactProvider
import com.example.phonebook.di.ApplicationScope
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import com.example.phonebook.domain.useCase.listContact.ListContactRepository
import javax.inject.Inject

@ApplicationScope
class ContactsRepository @Inject constructor(
    private val contactProvider: ContactProvider,
    private val notificationSwitcher: NotificationSwitcher
) : BroadcastRepository, ListContactRepository {

    override fun offReminder(contact: Contact) {
        notificationSwitcher.offReminder(contact)
    }

    override fun onReminder(contact: Contact) {
        notificationSwitcher.onReminder(contact)
    }

    override fun isAlarmSet(context: Context, contact: Contact): Boolean =
        notificationSwitcher.isAlarmSet(context, contact)

    override fun getContactList(query: String): List<Contact> {
        return contactProvider.getContactListData(query = query)
    }
}
