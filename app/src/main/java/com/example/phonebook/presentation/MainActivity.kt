package com.example.phonebook.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.phonebook.R
import com.example.phonebook.data.Contact
import com.example.phonebook.databinding.ActivityMainBinding
import com.example.phonebook.presentation.contactListScreen.ContactListFragment
import com.example.phonebook.presentation.contactListScreen.ContactListFragmentDirections
import com.example.phonebook.presentation.contactListScreen.RequestContactsPermission


class MainActivity : AppCompatActivity(), RequestContactsPermission {

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         installSplashScreen()
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
        val contact = intent?.getParcelableExtra<Contact>(CONTACT)
        val navController = navHostFragment.navController
        val frag = intent?.getStringExtra(FRAGMENT_NAME)
        if (contact !=null){
            frag?.let {
                when (frag) {
                    SOME_FRAGMENT -> {
                        navController.navigate(
                            ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(contact)
                        )
                    }
                    else -> throw RuntimeException(UNKNOWN_FRAGMENT)
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
    companion object{
        private const val SOME_FRAGMENT = "SOME_FRAGMENT"
        private const val FRAGMENT_NAME = "FRAGMENT_NAME"
        private const val UNKNOWN_FRAGMENT = "Unknown fragment"
        private const val CONTACT = "contact"
    }
}