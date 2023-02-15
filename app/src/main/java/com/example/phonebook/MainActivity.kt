package com.example.phonebook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.phonebook.data.Contact
import com.example.phonebook.databinding.ActivityMainBinding
import com.example.phonebook.presentation.ContactListFragment
import com.example.phonebook.presentation.ContactListFragmentDirections
import com.example.phonebook.presentation.RequestContactsPermission


class MainActivity : AppCompatActivity(), RequestContactsPermission {

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        if (savedInstanceState == null) {
            getIntentFromNotification(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        getIntentFromNotification(intent)
    }

    private fun initToolbar() {
        val toolBar = binding.toolBar
        setSupportActionBar(toolBar)
        val navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolBar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun getIntentFromNotification(intent: Intent?) {
        val contact = intent?.getParcelableExtra<Contact>("CONTACT")
        val navController = navHostFragment.navController
        val frag = intent?.getStringExtra("FRAGMENT_NAME")
        if (contact !=null){
            frag?.let {
                when (frag) {
                    "SOME_FRAGMENT" -> {
                        Log.d("SOME_FRAGMENT", "FRAGMENT_NAME")
                        navController.navigate(
                            ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(contact)
                        )
                    }
                    else -> throw RuntimeException("Unknown fragment")
                }
            }
        }
    }

    override fun getRequestPermission() {
        val fragment = getCurrentVisibleFragment()
        if (fragment != null) {
            (fragment as ContactListFragment).requestPermission()
        }
    }

    private fun getCurrentVisibleFragment(): Fragment? {
        val fragmentManager = navHostFragment.childFragmentManager
        val fragment = fragmentManager.primaryNavigationFragment
        return if (fragment is ContactListFragment) {
            fragment
        } else null
    }
}