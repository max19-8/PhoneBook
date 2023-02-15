package com.example.phonebook.domain.useCase.broadcast

import com.example.phonebook.data.Contact

class OffReminderUseCase(private val repository: BroadcastRepository) {

    operator fun invoke(contact: Contact) = repository.offReminder(contact)
}