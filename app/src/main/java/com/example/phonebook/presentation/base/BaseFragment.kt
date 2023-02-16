package com.example.phonebook.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.phonebook.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<viewBinding : ViewBinding> : Fragment() {

    private var _binding: viewBinding? = null
    val binding: viewBinding get() = _binding!!
    protected abstract fun getViewBinding(): viewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return binding.root
    }

    private fun init() {
        _binding = getViewBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }
     fun createSnackBar(text: String) {
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