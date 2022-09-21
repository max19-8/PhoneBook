package com.example.phonebook.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.phonebook.presentation.ContactListFragment
import com.example.phonebook.presentation.DetailContactFragment
import com.example.phonebook.presentation.GetContactById
import com.example.phonebook.presentation.GetContacts
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class ContactService : Service() {

   private val contactBinder = ContactBinder()

    override fun onBind(intent: Intent) = contactBinder

    fun getContacts(callback: WeakReference<GetContacts>) {
        thread(start = true) {
           callback.get()?.getContacts(listOfContacts)
        }
    }

    fun getDetailContact(id:Int, callback: WeakReference<GetContactById>) {
        thread(start = true) {
           callback.get()?.getContact(listOfContacts[id])
        }
    }
    inner class ContactBinder: Binder() {
        fun getBindServiceInstance():ContactService{
            return this@ContactService
        }
    }
    companion object{
        fun getContactById(id:Int):Contact{
            return listOfContacts[id]
        }
    }
}