package com.example.phonebook.presentation

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.phonebook.R
import com.example.phonebook.data.NotificationSwitcher
import com.example.phonebook.data.repository.ContactsRepository
import com.example.phonebook.databinding.FragmentContactListBinding
import com.example.phonebook.domain.useCase.listContact.ListContactUseCase
import com.google.android.material.snackbar.Snackbar


class ContactListFragment : BaseFragment<FragmentContactListBinding>() {
    override fun getViewBinding(): FragmentContactListBinding =
        FragmentContactListBinding.inflate(layoutInflater)

    private val readContactsPermission by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    //         preparingContactsInfoForDisplay()
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

    private fun showPermissionDialog() {
        ObtainingPermissionFragment().show(parentFragmentManager, ObtainingPermissionFragment.TAG)
    }


    private val contactListViewModel by lazy {
        ContactListViewModel(
            ListContactUseCase(
                ContactsRepository(
                    NotificationSwitcher(requireActivity().applicationContext)
                )
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        binding.contactItem.item.setOnClickListener {
            navigate(
                ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(
                    0
                )
            )
        }
        observeViewModel()
    }

    fun requestPermission() {
        readContactsPermission.launch(Manifest.permission.READ_CONTACTS)
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

    private fun createSnackBar(text: String) {
        val snackBar =
            Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        snackBar.show()
    }
}

interface RequestContactsPermission {
    fun getRequestPermission()
}