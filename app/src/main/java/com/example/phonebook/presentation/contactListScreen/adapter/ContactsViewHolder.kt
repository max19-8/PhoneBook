package com.example.phonebook.presentation.contactListScreen.adapter

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.phonebook.data.Contact
import com.example.phonebook.databinding.ContactItemBinding

class ContactsViewHolder(private val binding: ContactItemBinding,private val contactClick: ContactClickListener):ViewHolder(binding.root) {
    fun bind(contact:Contact){
         binding.listTextViewContact.text = contact.name
        binding.listTextViewNumber.text =contact.number
       binding.listImageContact
        createImage(contact.name,binding.listImageContact)
        binding.root.setOnClickListener {
            contactClick.onContactClick(contact)
        }
    }

    private fun createImage(name:String,button: Button) {
        val strArray = name.split(" ").toTypedArray()
        val builder = StringBuilder()
        if (strArray.isNotEmpty()) {
            builder.append(strArray[0], 0, 1)
        }
        if (strArray.size > 1) {
            builder.append(strArray[1], 0, 1)
        }
        button.text = builder.toString()
    }
}