package com.example.phonebook.domain.useCase.broadcast

import com.example.phonebook.data.Contact
import com.example.phonebook.di.ContactsRepositoryQualifier
import javax.inject.Inject

class OnReminderUseCase @Inject constructor(
    @ContactsRepositoryQualifier private val repository: BroadcastRepository) {

    operator fun invoke(contact: Contact) = repository.onReminder(contact)
}