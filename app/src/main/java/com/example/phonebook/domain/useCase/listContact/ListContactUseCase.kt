package com.example.phonebook.domain.useCase.listContact

class ListContactUseCase(private val repository:ListContactRepository) {

    operator fun invoke() = repository.getContactList()
}