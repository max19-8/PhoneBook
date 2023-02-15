package com.example.phonebook.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentObtainingPermissionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ObtainingPermissionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentObtainingPermissionsBinding? = null
    private val binding get() = _binding!!

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    private var requestContactsPermission: RequestContactsPermission? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestContactsPermission = context as? RequestContactsPermission
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentObtainingPermissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        binding.rationaleButton.setOnClickListener {
            requestContactsPermission?.getRequestPermission()
            dismiss()
        }
        binding.suppressButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "obtaining_permission_fragment"
    }
}