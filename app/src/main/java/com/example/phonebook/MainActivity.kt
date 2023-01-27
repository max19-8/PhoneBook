package com.example.phonebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.phonebook.databinding.ActivityMainBinding

import com.example.phonebook.presentation.ContactListFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        Log.d("MainActivityfsfsdfsd", "onCreate маин активити")
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
        val contactId = intent?.getIntExtra("CONTACT_ID", 22)
        val navController = navHostFragment.navController
        val frag = intent?.getStringExtra("FRAGMENT_NAME")
        frag?.let {
            when (frag) {
                "SOME_FRAGMENT" -> {
                    Log.d("SOME_FRAGMENT", "FRAGMENT_NAME")
                    navController.navigate(
                        ContactListFragmentDirections.actionContactListFragmentToDetailContactFragment(
                            contactId ?: 0
                        )
                    )
                }
                else -> throw RuntimeException()
            }
        }
    }
}