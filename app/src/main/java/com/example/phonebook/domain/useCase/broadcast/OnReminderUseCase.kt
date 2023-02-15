package com.example.phonebook.domain.useCase.broadcast

import com.example.phonebook.data.Contact

class OnReminderUseCase(private val repository: BroadcastRepository) {

    operator fun invoke(contact: Contact) = repository.onReminder(contact)
}