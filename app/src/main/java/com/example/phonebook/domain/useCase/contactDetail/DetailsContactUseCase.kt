package com.example.phonebook.domain.useCase.contactDetail

class DetailsContactUseCase (private val repository: DetailsContactRepository){

    operator fun invoke(id:Int) = repository.getContactDetails(id)
}