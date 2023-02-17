package com.example.phonebook.domain.useCase.listContact

import javax.inject.Inject

class ListContactUseCase @Inject constructor(private val repository: ListContactRepository) {

    operator fun invoke(query:String) = repository.getContactList(query)
    }