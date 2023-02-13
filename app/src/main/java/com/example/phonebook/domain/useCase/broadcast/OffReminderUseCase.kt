package com.example.phonebook.domain.useCase.broadcast

class OffReminderUseCase(private val repository: BroadcastRepository) {

    operator fun invoke(id: Int) = repository.offReminder(id)
}