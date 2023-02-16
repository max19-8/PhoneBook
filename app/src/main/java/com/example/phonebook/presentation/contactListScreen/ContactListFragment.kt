package com.example.phonebook.presentation.contactListScreen

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.phonebook.R
import com.example.phonebook.data.Contact
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.localDataSource.ContactProvider
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase
import com.example.phonebook.presentation.base.BaseFragment
import com.example.phonebook.presentation.ObtainingPermissionFragment
import com.example.phonebook.presentation.contactListScreen.adapter.ContactClickListener
import com.example.phonebook.presentation.contactListScreen.adapter.ContactsAdapter

class ContactListFragment : BaseFragment<FragmentContactListBinding>(), ContactClickListener {
    override fun getViewBinding(): FragmentContactListBinding =
        FragmentContactListBinding.inflate(layoutInflater)


    private val readContactsPermission by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    observeViewModel()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_CONTACTS
                ) -> {
                    showPermissionDialog()
                }
                else -> {
                    createSnackBar(getString(R.string.snack_bar_get_permission_text))
                }
            }
        }
    }


    private val contactListViewModel by lazy {
        ContactListViewModel(
            ListContactUseCase(
                ContactsRepository(
                    ContactProvider(requireContext().contentResolver),
                    NotificationSwitcher(requireActivity().applicationContext)
                )
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        val contactsAdapter = ContactsAdapter(this)
        binding.contactsRecyclerView.adapter = contactsAdapter
        contactListViewModel.contactList.observe(viewLifecycleOwner) { contacts ->
            contactsAdapter.submitList(contacts)
        }
    }

    override fun onContactClick(contact: Contact) {
        navigate(
            ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(contact)
        )
    }

    fun requestPermission() {
        readContactsPermission.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun observeViewModel() {
        contactListViewModel.contactList.observe(viewLifecycleOwner) {

        }
    }

    private fun showPermissionDialog() {
        ObtainingPermissionFragment().show(parentFragmentManager, ObtainingPermissionFragment.TAG)
    }
}

interface RequestContactsPermission {
    fun getRequestPermission()
}