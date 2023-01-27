package com.example.phonebook.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase

class ContactListFragment : BaseFragment<FragmentContactListBinding>(){
    override fun getViewBinding(): FragmentContactListBinding  = FragmentContactListBinding.inflate(layoutInflater)

    private val contactListViewModel by lazy {
        ContactListViewModel(ListContactUseCase(ContactsRepository(
            NotificationSwitcher(requireContext())
        )))

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ContactListFragment","фрагмент он атач")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactItem.item.setOnClickListener {
            navigate(ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(0))
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        contactListViewModel.contactList.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                val tvName = binding.contactItem.listTextViewContact
                val tvPhoneNumber = binding.contactItem.listTextViewNumber
                tvName.text = it[0].name
                tvPhoneNumber.text = it[0].number
            }
        }
    }
}