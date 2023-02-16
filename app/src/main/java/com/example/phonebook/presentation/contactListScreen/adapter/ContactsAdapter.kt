package com.example.phonebook.presentation.contactListScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.phonebook.data.Contact
import com.example.phonebook.databinding.ContactItemBinding

class ContactsAdapter(private val contactClick: ContactClickListener) : ListAdapter<Contact,ContactsViewHolder>(ContactDiffUtilCallback) {

    private var _binding: ContactItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
         _binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactsViewHolder(binding,contactClick)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

interface ContactClickListener {
    fun onContactClick(contact:Contact)
}