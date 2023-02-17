package com.example.phonebook.di

import android.content.ContentResolver
import android.content.Context
import com.example.phonebook.presentation.contactListScreen.ContactListFragment
import com.example.phonebook.presentation.detailContactScreen.DetailContactFragment
import dagger.BindsInstance
import dagger.Component


@Component(modules = [DataModule::class,ViewModelModule::class])
@ApplicationScope
interface ApplicationComponent {

    fun inject(fragment: ContactListFragment)
    fun inject(fragment: DetailContactFragment)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
         @BindsInstance    context: Context,
         @BindsInstance   contentResolver: ContentResolver
        ):ApplicationComponent
    }
}