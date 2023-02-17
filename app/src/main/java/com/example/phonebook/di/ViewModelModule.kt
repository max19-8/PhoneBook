package com.example.phonebook.di

import androidx.lifecycle.ViewModel
import com.example.phonebook.presentation.contactListScreen.ContactListViewModel
import com.example.phonebook.presentation.detailContactScreen.ContactDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    @Binds
    fun bindsContactListViewModel(impl:ContactListViewModel):ViewModel

    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    @Binds
    fun bindsContactDetailsViewModel(impl:ContactDetailsViewModel):ViewModel
}