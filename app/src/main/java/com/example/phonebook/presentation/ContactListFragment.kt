package com.example.phonebook.presentation

import android.Manifest
import android.R.attr.button
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.phonebook.R
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.localDataSource.ContactProvider
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase


class ContactListFragment : BaseFragment<FragmentContactListBinding>() {
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
                    NotificationSwitcher(requireActivity().applicationContext))
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        binding.contactItem.item.setOnClickListener {
            navigate(
                ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(
                    contactListViewModel.contactList.value!!.last()
                )
            )
        }
    }

    fun requestPermission() {
        readContactsPermission.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun observeViewModel() {
        contactListViewModel.contactList.observe(viewLifecycleOwner) {
            activity?.runOnUiThread {
                val tvName = binding.contactItem.listTextViewContact
                val tvPhoneNumber = binding.contactItem.listTextViewNumber
                val image = binding.contactItem.listImageContact
                tvName.text = it.last().name
                tvPhoneNumber.text = it.last().number
                    createImage(it.last().name,image)
            }
        }
    }
    fun createImage(name:String,button: Button) {
        val strArray = name.split(" ").toTypedArray()
        val builder = StringBuilder()
//First name
        if (strArray.isNotEmpty()) {
            builder.append(strArray[0], 0, 1)
        }
        //Middle name
        if (strArray.size > 1) {
            builder.append(strArray[1], 0, 1)
        }
        button.text = builder.toString()
    }

    private fun showPermissionDialog() {
        ObtainingPermissionFragment().show(parentFragmentManager, ObtainingPermissionFragment.TAG)
    }
}

interface RequestContactsPermission {
    fun getRequestPermission()
}