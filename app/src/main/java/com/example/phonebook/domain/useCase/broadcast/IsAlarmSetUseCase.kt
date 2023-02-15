package com.example.phonebook.domain.useCase.broadcast

import android.content.Context
import com.example.phonebook.data.Contact

class IsAlarmSetUseCase(private val repository: BroadcastRepository) {

    operator fun invoke(context: Context, contact: Contact) =
        repository.isAlarmSet(context, contact)
}