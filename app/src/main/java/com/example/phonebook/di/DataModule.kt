package com.example.phonebook.di

import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.domain.useCase.broadcast.BroadcastRepository
import com.example.phonebook.domain.useCase.listContact.ListContactRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @ApplicationScope
    @ContactsRepositoryQualifier
    @Binds
    fun bindsBroadcastRepository(impl:ContactsRepository):BroadcastRepository

    @ApplicationScope
    @Binds
    fun bindsContactsRepository(impl:ContactsRepository):ListContactRepository



    @ApplicationScope
    @NotificationSwitcherQualifier
    @Binds
    fun bindsNotificationSwitcher(impl:NotificationSwitcher):BroadcastRepository

}