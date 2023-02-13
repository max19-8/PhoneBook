package com.example.phonebook.domain.useCase.broadcast

import com.example.phonebook.data.Contact

class OnReminderUseCase(private val repository: BroadcastRepository) {

    operator fun invoke(id: Int, contact: Contact) = repository.onReminder(id, contact)
}