package com.example.phonebook.presentation.contactListScreen.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.phonebook.data.Contact

object ContactDiffUtilCallback:DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem == newItem
}