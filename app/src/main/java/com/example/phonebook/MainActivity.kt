package com.example.phonebook

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.phonebook.data.Contact
import com.example.phonebook.data.ContactService
import com.example.phonebook.databinding.ActivityMainBinding
import com.example.phonebook.notification.AlarmBirthdayReceiver
import java.util.*

class MainActivity : AppCompatActivity(),ProvideService {

    private lateinit var binding: ActivityMainBinding

    var alarmManager: AlarmManager? = null
    private lateinit var pendingIntent: PendingIntent

     var isBound: Boolean = false
    private  var contactService: ContactService? = null

    private val serviceConnection = object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d("CONTACT","onServiceConnected")

            val binder = service as ContactService.ContactBinder

            contactService = binder.getBindServiceInstance()
            Log.d("CONTACT","onServiceConnected маин активити")
            Log.d("CONTACT",contactService.toString())
            //    Log.d("CONTACT",result.first().toString())
            isBound = true

        }
        override fun onServiceDisconnected(name: ComponentName) {
            Log.d("CONTACT","onServiceDisconnected")
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this,ContactService::class.java)
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        Log.d("CONTACT","onCreate маин активити")
        setAlarm(ContactService.getContactById(0))
    }

    private fun initToolbar(){
        val toolBar = binding.toolBar
        setSupportActionBar(toolBar)
        val navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
                toolBar.setupWithNavController(navController,appBarConfiguration)
       setupActionBarWithNavController(navController,appBarConfiguration)
    }

    private fun setAlarm(contact: Contact){
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmBirthdayReceiver::class.java).apply {
           putExtra(KEY_ID,contact.id)
        }
        pendingIntent = PendingIntent.getBroadcast(applicationContext,0,intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val datetimeToAlarm = Calendar.getInstance()
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
//        datetimeToAlarm.set(Calendar.MONTH, contact.mount)
//        datetimeToAlarm.set(Calendar.DAY_OF_MONTH, contact.day)
//        datetimeToAlarm.set(Calendar.HOUR_OF_DAY, 14)
//        datetimeToAlarm.set(Calendar.MINUTE, 41)

        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + 60 * 1000,
            1000 * 60 * 2,
            pendingIntent
        )
//        alarmManager!!.setExactAndAllowWhileIdle( AlarmManager.RTC_WAKEUP,
//            datetimeToAlarm.timeInMillis,  pendingIntent)
     //   sendBroadcast(intent)

//        Log.d("setAlarm",intent.extras.toString())
//        Log.d("setAlarm",contact.id.toString())
//        alarmManager!!.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), pendingIntent)
    }


    override fun onStop() {
       unbindService()
        super.onStop()
    }
    override fun getService(): ContactService? {
    Log.d("CONTACT"," getService + $contactService")
        return contactService
    }

    private fun unbindService(){
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}