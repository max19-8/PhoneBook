package com.example.phonebook.domain.useCase.broadcast

import android.content.Context

class IsAlarmSetUseCase(private val repository: BroadcastRepository) {

    operator fun invoke(context: Context, contactId: Int) =
        repository.isAlarmSet(context, contactId)
}