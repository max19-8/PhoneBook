package com.example.phonebook.domain.useCase.broadcast

import android.content.Context
import com.example.phonebook.data.Contact
import com.example.phonebook.di.ContactsRepositoryQualifier
import javax.inject.Inject

class IsAlarmSetUseCase @Inject constructor(
    @ContactsRepositoryQualifier private val repository: BroadcastRepository) {

    operator fun invoke(context: Context, contact: Contact) =
        repository.isAlarmSet(context, contact)
}